package oauth2AuthorizeServer.util;

import lombok.extern.slf4j.Slf4j;
import oauth2AuthorizeServer.properties.CommonProperties;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class HttpSendUtils {

    private final CommonProperties commonProperties;

    private final String resourceUrl;

    public HttpSendUtils(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
        resourceUrl = commonProperties.getResourceUrl();
    }

    public String sendGetRequest(String uri, Map<String,String> customHeader,  Map<String,String> paramsMap) {
        String url = resourceUrl + uri;
        String result = Strings.EMPTY;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(url);

            customHeader.entrySet().stream().forEach(entry-> httpGet.addHeader(entry.getKey(),entry.getValue()));

            ClassicHttpResponse resultResponse = httpClient.execute(httpGet, response -> response);

            if (HttpStatus.SC_OK==resultResponse.getCode()) {
                result = EntityUtils.toString(resultResponse.getEntity());
            }
            log.debug(">>> ", result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<String,String> convertHeaderToMap(HttpServletRequest request){
        Map<String,String> headerMap=new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        // 遍历每个头部名称，并获取对应的头部值
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            // 打印头部名称和值
            while (headerValues.hasMoreElements()) {
                String headerValue = headerValues.nextElement();
                headerMap.put(headerName,headerValue);
                log.debug(">>> {} : {}",headerName,headerValue);
            }
        }
        return headerMap;
    }

}
