package oath2authorizeserver.trainseat;


import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

public class TrainSeatTool {

    //    查台鐵網頁資料
    private String taiTrainUrl = "https://www.railway.gov.tw/tra-tip-web/tip/tip001/tip119/queryTime";
//    查詢時間及班次

//    越過圖片驗證

//    取得回傳資料判斷剩餘座位

    private void queryTrainTipWeb() {
        SSLConnectionSocketFactory sslsf=new SSLConnectionSocketFactory(SSLContexts.createDefault(),new String[] {"TLSv1.2","TLSv1.3"},null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient closetableHttpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//        HttpClients.custom();
    }

}
