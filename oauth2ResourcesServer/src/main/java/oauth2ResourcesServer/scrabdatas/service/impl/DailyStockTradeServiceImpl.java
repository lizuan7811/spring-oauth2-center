package oauth2ResourcesServer.scrabdatas.service.impl;

import lombok.extern.log4j.Log4j2;
import oauth2ResourcesServer.scrabdatas.service.DailyStockTradeService;
import oauth2ResourcesServer.scrabdatas.util.ConnectionFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.util.Strings;
import org.jvnet.hk2.annotations.Service;
import org.springframework.aop.target.PoolingConfig;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class DailyStockTradeServiceImpl implements DailyStockTradeService {

    List<String> params = Arrays.asList("stockCode", "date", "transactVolume", "sharesTradedNum", "totalPrice", "startPrice", "highestPrice", "lowestPrice", "endPrice", "upAndDown");

    public void testUpdateDailyTradeData() {
        readLineFmWeb("20240521");
//        String txDate = getTxDate();
//
//        String testString = "=\"0050\",\"元大台灣50\",\"7,278,652\",\"13,023\",\"1,212,643,672\",\"167.25\",\"167.40\",\"165.30\",\"167.20\",\"-\",\"0.05\",\"167.10\",\"4\",\"167.20\",\"40\",\"0.00\",";
//
//        if (testString.startsWith("=")) {
//            testString = testString.substring(1, testString.length());
//        }
//        System.out.println(testString);
//        String[] tmpStrArray = testString.split("\",\"");
//        System.out.println(tmpStrArray.length);
//        String tmpVal = "";
//        Map<String, String> beanMap = new HashMap<>();
//        for (int i = 0; i <= 10; i++) {
//            if (i == 9 || i == 10) {
//                tmpVal += tmpStrArray[i];
//                if (i == 10) {
//                    beanMap.put(params.get(i - 1), tmpVal);
//                }
//            } else if (i == 1) {
//                beanMap.put(params.get(i), txDate);
//            } else {
//                beanMap.put(params.get(i), tmpStrArray[i]);
//            }
//        }
//        StockHistEntity stockHistEntity = new StockHistEntity();
//        try {
//            BeanUtils.populate(stockHistEntity, beanMap);
//        } catch (IllegalAccessException e) {
//            log.debug(">>> ", e);
//        } catch (InvocationTargetException e) {
//            log.debug(">>> ", e);
//        }
    }

    private String getTxDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    @Override
    public void updateDailyTradeData() {


    }

    private void readLineFmWeb(String qDate) {

        String url = String.format("https://www.twse.com.tw/exchangeReport/MI_INDEX?response=csv&date=%s&type=ALL", qDate);
//        String url = "https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json&date=20240520&stockNo=2330";
        try {

//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            try (InputStream trustStoreIS = new FileInputStream("C://JAVA/jdk-17.0.2/lib/security/cacerts")) {
//                trustStore.load(trustStoreIS, "changeit".toCharArray());
//            }
//
//            // 初始化 TrustManagerFactory
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(trustStore);
//
//            // 创建 SSLContext
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustManagerFactory.getTrustManagers(), new java.security.SecureRandom());

            // 设置默认的 SSLContext
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//
//
//            HttpsURLConnection connection = ConnectionFactory.getConnectionInst(new URL(url));
//            BufferedReader reader = ConnectionFactory.getBufferReader(connection);
//            String rdLine = Strings.EMPTY;
//            while (StringUtils.isNotBlank(rdLine = reader.readLine())) {
//                System.out.println(rdLine);
//            }
//            ConnectionFactory.disConnection();


            // 使用 TrustAllStrategy 构建 SSLContext
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(new TrustAllStrategy())
                    .build();

            // 创建 SSLConnectionSocketFactory 并配置为忽略证书验证
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                    sslContext, (hostname, session) -> true);

            // 创建连接管理器并设置自定义的 SSL 工厂
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

            connManager.setDefaultSocketConfig(SocketConfig.custom()
                    .build());

            connManager.setDefaultConnectionConfig(ConnectionConfig.custom()
                    .build());

            connManager.setValidateAfterInactivity(TimeValue.ofSeconds(30));

            // 创建 HttpClient 并配置自定义的连接管理器
//            CloseableHttpClient httpClient = HttpClients.custom()
//                    .setConnectionManager(connManager)
//                    .build();





//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
////////
//            HttpClientConnectionManager connectionManager=PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).build();
////            SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
//                    .setSslContext(sslContext)
//                    .build();
////            HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
////                    .setSSLSocketFactory(sslSocketFactory)
////                    .build();
//
//
            int i = 0;
            try (CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
                    .build()) {
                HttpGet request = new HttpGet(url);
                try (CloseableHttpResponse response = httpClient.execute(request)) {
                    if (response.getCode() == 200) {
                        try (InputStream inputStream = response.getEntity().getContent();
                             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"BIG5"))) {

                            String line;
                            while ((line = reader.readLine()) != null) {
                                // 處理每一行
                                System.out.println(line);
                                i++;
                                if (i == 1000) {
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Request failed: " + response.getCode());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }


////        System.setProperty("javax.net.ssl.keyStore", "certs/www-twse-com-tw.jks");
////        System.setProperty("javax.net.ssl.keyStorePassword", "www-twse-com-tw");
////        String url = "http://www.twse.com.tw/exchangeReport/STOCK_DAY?response=csv&date=20210510&stockNo=2330";
//        try {
////            HttpClientBuilder clientBuilder = HttpClients.custom();
//////            final SSLContext sslContext = createSslContext();
//            SSLContext sslContext = getSslContext("C://Java/jdk-17/lib/security/cacerts");
////
////            final Registry<ConnectionSocketFactory> socketFactoryRegistry =
////                    RegistryBuilder.<ConnectionSocketFactory> create()
////                            .register("https", sslsf)
////                            .register("http", new PlainConnectionSocketFactory())
////                            .build();
////            final BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
////            clientBuilder.setConnectionManager(connectionManager);\
//
//            SSLContext sslContext =createCustomSSLContext("certs/www-twse-com-tw.pem");
////                    createSystemDefault();
//            Registry<ConnectionSocketFactory> socketFactoryRegistry= RegistryBuilder.<ConnectionSocketFactory>create()
//                            .register("http", PlainConnectionSocketFactory.INSTANCE)
//                            .register("https", new SSLConnectionSocketFactory(sslContext))
//                            .build();
//
//            BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
//

    }

    private SSLContext getSslContext(String sslFilePath){
        SSLContext sslContext =null;
        try{
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (FileInputStream trustStoreFis = new FileInputStream(sslFilePath)) {
                trustStore.load(trustStoreFis, "changeit".toCharArray());
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

        }catch (Exception e){
            e.printStackTrace();
        }

        return sslContext;
    }


//    private SSLContext createCustomSSLContext(String pemFilePath) {
//        SSLContext sslContext = null;
//        try (InputStream is = new FileInputStream(pemFilePath)) {
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            Certificate caCert = cf.generateCertificate(is);
//
//            // Create a KeyStore and add the certificate
//            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//            ks.load(null, null);
//            ks.setCertificateEntry("caCert", caCert);
//
//            // Create a TrustManagerFactory with the KeyStore
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            tmf.init(ks);
//
//            // Build SSLContext with the TrustManagerFactory
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, tmf.getTrustManagers(), null);
//        } catch (IOException | GeneralSecurityException e) {
//            e.printStackTrace();
//        }
//        return sslContext;
//    }
}
