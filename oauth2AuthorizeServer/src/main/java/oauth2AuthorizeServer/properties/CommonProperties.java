package oath2resourceserver.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class CommonProperties {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisAuth;

    @Value("${spring.redis.maxtotal}")
    private int maxTotal;

    @Value("${spring.redis.maxidel}")
    private int maxIdel;

    @Value("${spring.oauth2.authorize-server-url}")
    private String authorizeServerUrl;

    @Value("${spring.oauth2.redirect-uri}")
    private String redirectUri;

    @Value("${spring.oauth2.accesstoken-url}")
    private String accessTokenUrl;

    @Value("${spring.oauth2.resource-url}")
    private String resourceUrl;

}
