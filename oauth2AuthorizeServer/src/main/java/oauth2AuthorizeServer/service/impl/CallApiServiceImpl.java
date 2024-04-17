package oauth2AuthorizeServer.service.impl;

import oauth2AuthorizeServer.service.CallApiService;
import oauth2AuthorizeServer.util.HttpSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class CallApiServiceImpl implements CallApiService {

    private final HttpSendUtils httpSendUtils;

    @Autowired
    public CallApiServiceImpl(HttpSendUtils httpSendUtils){
        this.httpSendUtils=httpSendUtils;
    }

    @Override
    public String selectHists(HttpServletRequest request) {
        String uri="/findhist/getStock";
        Map<String,String> headerMap=httpSendUtils.convertHeaderToMap(request);
        return httpSendUtils.sendGetRequest(uri,headerMap,new HashMap<>());
    }


}
