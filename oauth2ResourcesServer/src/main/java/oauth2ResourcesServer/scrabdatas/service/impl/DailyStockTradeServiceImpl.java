package oauth2ResourcesServer.scrabdatas.service.impl;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import oauth2ResourcesServer.scrabdatas.entity.StockHistEntity;
import oauth2ResourcesServer.scrabdatas.persistent.StockHistRepo;
import oauth2ResourcesServer.scrabdatas.property.ScrawProperty;
import oauth2ResourcesServer.scrabdatas.service.DailyStockTradeService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
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

@Log4j2
@Service
public class DailyStockTradeServiceImpl implements DailyStockTradeService {

    private final String HEADERLINE = "\"證券代號\",\"證券名稱\",\"成交股數\",\"成交筆數\",\"成交金額\",\"開盤價\",\"最高價\",\"最低價\",\"收盤價\",\"漲跌(+/-)\",\"漲跌價差\",\"最後揭示買價\",\"最後揭示買量\",\"最後揭示賣價\",\"最後揭示賣量\",\"本益比\",";

    private final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36";

    private final String DAILYTRADEDATA_ADDRESS;
    private final String CACERT_PATH;
    private final ScrawProperty scrawProperty;
    private final StockHistRepo stockHistRepo;

    @Autowired
    public DailyStockTradeServiceImpl(ScrawProperty scrawProperty, StockHistRepo stockHistRepo) {
        this.scrawProperty = scrawProperty;
        this.stockHistRepo = stockHistRepo;
        DAILYTRADEDATA_ADDRESS = scrawProperty.getDailyTradedataFqdn();
        CACERT_PATH = scrawProperty.getCacertPath();
    }

    List<String> params = Arrays.asList("stockCode", "date", "transactVolume", "sharesTradedNum", "totalPrice", "startPrice", "highestPrice", "lowestPrice", "endPrice", "upAndDown");

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

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    @Override
    public void updateDailyTradeData(String startDt, String endDt) {
        if (StringUtils.isBlank(endDt)) {
            System.out.println(">>> endDt is empty!");
            endDt = getCurrentDate();
        }
        List<String> queryDates = getQueryDate(startDt, endDt);
        queryDates.stream().forEach(queryDt -> {
            System.out.println(">>> >>> Query date is: "+queryDt);
            log.debug(">>> Query date is: {}", queryDt);

            queryDailyTradeDatas(queryDt);
        });

    }

    private void queryDailyTradeDatas(String qDate) {
        List<StockHistEntity> collectList = new ArrayList<>();
        boolean startParse = false;
        String noDeshDt = qDate.replace("-", "");
        String url = String.format(DAILYTRADEDATA_ADDRESS, noDeshDt);
        System.out.println(">>> Query url is: "+url);

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
                    try (InputStream inputStream = response.getEntity().getContent();
                         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "BIG5"))) {
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
//                                stockHistRepo.save(processLine(line, getMinguoDate(qDate)));
                                collectList.add(processLine(line, getMinguoDate(qDate)));
                                if (collectList.size() % 100 == 0) {
                                    saveEntities(collectList);
                                    collectList.clear();
                                    System.out.println(">>> Save 3000 datas to db!");

                                    log.debug(">>> Save 3000 datas to db!");
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Request failed: " + response.getCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!collectList.isEmpty()) {
                saveEntities(collectList);
            }
            log.debug(">>> Finish Update!");
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
        String[] tmpStrArray = line.split("\",\"");
        String tmpVal = "";
        Map<String, String> beanMap = new HashMap<>();
        for (int i = 0; i <= 10; i++) {
            if (i == 9 || i == 10) {
                tmpVal += tmpStrArray[i];
                if (i == 10) {
                    beanMap.put(params.get(i - 1), tmpVal.trim().replace("\"",""));
                }
            } else if (i == 1) {
                beanMap.put(params.get(i), qDate.trim().replace("\"",""));
            } else {
                beanMap.put(params.get(i), tmpStrArray[i].trim().replace("\"",""));
            }
        }
        StockHistEntity stockHistEntity = new StockHistEntity();
        try {
            BeanUtils.populate(stockHistEntity, beanMap);
            System.out.println(stockHistEntity.toString());
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

            // Create a KeyStore and add the certificate
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, null);
            ks.setCertificateEntry("caCert", caCert);

            // Create a TrustManagerFactory with the KeyStore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);

            // Build SSLContext with the TrustManagerFactory
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    @Transactional
    public void saveEntities(List<StockHistEntity> entities) {
        try{
            stockHistRepo.saveAll(entities);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
