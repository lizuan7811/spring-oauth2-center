package oauth2AuthorizeServer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2023/12/16
 * @time: 下午 02:51
 **/
@Data
public class JwtTokenModel implements Serializable {
    @JsonProperty("access_token")
    private String accessToeken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("jti")
    private String jti;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresIn;
}
