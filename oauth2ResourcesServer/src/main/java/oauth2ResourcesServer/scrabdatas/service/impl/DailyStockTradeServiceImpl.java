package oauth2ResourcesServer.scrabdatas.service.impl;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import oauth2ResourcesServer.scrabdatas.entity.StockHistEntity;
import oauth2ResourcesServer.scrabdatas.entity.StockHistNoRealtiveEntity;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityNoRelativePk;
import oauth2ResourcesServer.scrabdatas.persistent.EntityManagerUtil;
import oauth2ResourcesServer.scrabdatas.persistent.StockHistNoRelativeRepo;
import oauth2ResourcesServer.scrabdatas.persistent.StockHistRepo;
import oauth2ResourcesServer.scrabdatas.property.ScrawProperty;
import oauth2ResourcesServer.scrabdatas.service.DailyStockTradeService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.transaction.Transactional;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DailyStockTradeServiceImpl implements DailyStockTradeService {

    private final String HEADERLINE = "\"證券代號\",\"證券名稱\",\"成交股數\",\"成交筆數\",\"成交金額\",\"開盤價\",\"最高價\",\"最低價\",\"收盤價\",\"漲跌(+/-)\",\"漲跌價差\",\"最後揭示買價\",\"最後揭示買量\",\"最後揭示賣價\",\"最後揭示賣量\",\"本益比\",";

    private final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36";

    private final String DAILYTRADEDATA_ADDRESS;

    private final String OUTPUTPATH;
    private final String CACERT_PATH;
    private final ScrawProperty scrawProperty;
    private final EntityManagerUtil entityManagerUtil;
    private final StockHistRepo stockHistRepo;
    private final Map<String,Consumer<List<StockHistEntity>>>consumerMap=new HashMap<>();
    private final String SQL = "INSERT INTO STOCK_HIST_DATA(STOCKCODE, DATET, TRANSACTVOLUME, SHARESTRADENUM, TOTALPRICE, STARTPRICE, HIGHESTPRICE, LOWESTPRICE, ENDPRICE, UPANDDOWN) VALUES (:stockCode, :datet, :transactVolume, :sharesTradedNum, :totalPrice, :startPrice, :highestPrice, :lowestPrice, :endPrice, :upAndDown)";

    @Autowired
    public DailyStockTradeServiceImpl(ScrawProperty scrawProperty, EntityManagerUtil entityManagerUtil,StockHistRepo stockHistRepo) {
        this.scrawProperty = scrawProperty;
        this.entityManagerUtil = entityManagerUtil;
        this.stockHistRepo=stockHistRepo;
        DAILYTRADEDATA_ADDRESS = scrawProperty.getDailyTradedataFqdn();
        OUTPUTPATH = scrawProperty.getDailytradeDataOutputPath();
        CACERT_PATH = scrawProperty.getCacertPath();
        consumerMap.put("ENTITYMANAGER",saveEntitiesWithEntityManager());
        consumerMap.put("JPA",saveEntitiesWithJpa());
        consumerMap.put("JDBC",saveEntitiesWithJdbc());
    }

    List<String> params = Arrays.asList("stockCode", "datet", "transactVolume", "sharesTradedNum", "totalPrice", "startPrice", "highestPrice", "lowestPrice", "endPrice", "upAndDown");

    private List<String> getQueryDate(String startDt, String endDt) {

        List<String> queryDts = new ArrayList<>();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate = LocalDate.parse(startDt, inputFormatter);
        LocalDate endDate = LocalDate.parse(endDt, inputFormatter);

        LocalDate tmpStartDt = startDate;

        while (!tmpStartDt.isAfter(endDate)) {
            queryDts.add(tmpStartDt.format(outputFormatter));
            tmpStartDt = tmpStartDt.plusDays(1);
        }
        return queryDts;
    }

    private String getCurrentNoDeshDate() {
        return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    }

    @Override
    public void updateDailyTradeData(String startDt, String endDt,String saveMode) {
        String tmpEndDt=endDt;
        if (StringUtils.isBlank(tmpEndDt)) {
            System.out.println(">>> endDt is empty!");
            tmpEndDt = getCurrentNoDeshDate();
        }
        List<String> queryDates = getQueryDate(startDt, tmpEndDt);
        queryDates.stream().forEach(queryDt -> {
            log.debug(">>> Query date is: {}", queryDt);
            queryDailyTradeDatas(queryDt,saveMode);
        });

    }

    private void queryDailyTradeDatas(String qDate,String saveMode) {
        String noDeshDt = qDate.replace("-", "");
        String url = String.format(DAILYTRADEDATA_ADDRESS, noDeshDt);
        log.debug(">>> Query url is: " + url);

        SSLContext sslContext = createCustomSSLContext(CACERT_PATH);

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).build();

        int i = 0;
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setUserAgent(USERAGENT)
                .build()) {
            HttpGet request = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                if (response.getCode() == 200) {
                    String downloadFilePath=String.format(OUTPUTPATH,qDate);
                    if(Paths.get(downloadFilePath).toFile().exists()){
                        return;
                    }
                    try (InputStream inputStream = response.getEntity().getContent();
                         InputStreamReader inputStreamReader= new InputStreamReader(inputStream, Charset.forName("BIG5"));
                         OutputStreamWriter writer = new OutputStreamWriter(
                                 new FileOutputStream(Paths.get(downloadFilePath).toFile()),StandardCharsets.UTF_8)) {
                        if (response.getCode() == 200) {
                            if(inputStreamReader.read()==-1){
                                return;
                            }
                            IOUtils.copy(inputStreamReader, writer);
                            log.debug(">>> File downloaded to: {}!", OUTPUTPATH);
                        } else {
                            log.debug(">>> Failed to downloaded,  response code: {} !", response.getCode());
                        }
                    }
                    processDownloadedFile(downloadFilePath,qDate,saveMode);
                } else {
                    log.debug("Request failed: " + response.getCode());
                }
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.debug(">>> Finish Update!");
        }
    }

    private void processDownloadedFile(String filePath,String qDate,String saveMode) {
        boolean startParse = false;
        List<StockHistEntity> collectList = new ArrayList<>();

        try (BufferedReader reader =
                     Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String line = Strings.EMPTY;
            while ((line = reader.readLine()) != null) {
                if (line.equals(HEADERLINE)) {
                    startParse = true;
                    continue;
                }
                if (!startParse) {
                    continue;
                }
                if (StringUtils.isNotBlank(line)) {
                    collectList.add(processLine(line, getMinguoDate(qDate)));
                    if (collectList.size() % 3000 == 0) {
                        consumerMap.get(saveMode).accept(collectList);
                        collectList.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!collectList.isEmpty()) {
                consumerMap.get(saveMode).accept(collectList);
            }
        }
    }

    private String getMinguoDate(String qDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fmtDate = LocalDate.parse(qDate, inputFormatter);
        DateTimeFormatter minguoFormatter = new DateTimeFormatterBuilder()
                .appendValueReduced(ChronoField.YEAR, 3, 3, 1911)
                .appendLiteral("-")
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral("-")
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .toFormatter()
                .withChronology(java.time.chrono.MinguoChronology.INSTANCE);
        return fmtDate.format(minguoFormatter);
    }

    private StockHistEntity processLine(String line, String qDate) {
        if (line.startsWith("=")) {
            line = line.substring(1, line.length());
        }
        String[] tmpStrArray = line.replace(",\r\n","").split("\",\"");
        String tmpVal = "";
        Map<String, String> beanMap = new HashMap<>();

        for (int i = 0; i <= 10; i++) {
            if (i == 9 || i == 10) {
                tmpVal += tmpStrArray[i];
                if (i == 10) {
                    beanMap.put(params.get(i - 1), tmpVal.trim().replace("\"", ""));
                }
            } else if (i == 1) {
                beanMap.put(params.get(i), qDate.trim().replace("\"", ""));
            } else {
                beanMap.put(params.get(i), tmpStrArray[i].trim().replace("\"", ""));
            }
        }
        StockHistEntity stockHistEntity = new StockHistEntity();
        try {
            BeanUtils.populate(stockHistEntity, beanMap);
            log.debug(">>> {}!",stockHistEntity);
        } catch (IllegalAccessException e) {
            log.debug(">>> ", e);
        } catch (InvocationTargetException e) {
            log.debug(">>> ", e);
        }
        return stockHistEntity;
    }


    private SSLContext createCustomSSLContext(String certFilePath) {
        SSLContext sslContext = null;
        try (InputStream is = FileUtils.openInputStream(Paths.get(certFilePath).toFile())) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate caCert = cf.generateCertificate(is);

            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, null);
            ks.setCertificateEntry("caCert", caCert);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslContext;
    }
    @Transactional
    public Consumer<List<StockHistEntity>> saveEntitiesWithEntityManager(){
        return stockHistEntities -> entityManagerUtil.bulkSave(stockHistEntities);
    }
    @Transactional
    public Consumer<List<StockHistEntity>> saveEntitiesWithJpa(){
        return stockHistEntities -> {
            stockHistRepo.saveAllAndFlush(stockHistEntities);
        };
    }
    @Transactional
    public Consumer<List<StockHistEntity>> saveEntitiesWithJdbc(){
        return stockHistMapEntities ->{
          List<Map<String,String>> tmpStockHistMapEntities=stockHistMapEntities.stream().map(stock->{
                Map<String,String> tmpMap;
                try {
                    tmpMap=BeanUtils.describe(stock);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                return tmpMap;
            }).collect(Collectors.toList());

            entityManagerUtil.bulkInsertWithJdbc(tmpStockHistMapEntities, SQL);
        };
    }
}
