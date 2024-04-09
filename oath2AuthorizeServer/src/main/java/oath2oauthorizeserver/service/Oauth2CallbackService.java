package oath2oauthorizeserver.service;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2023/11/26
 * @time: 下午 10:30
 **/
public interface Oauth2CallbackService {

    public String codeToAccessToken(String code);

}
