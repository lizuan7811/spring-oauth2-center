package oauth2ResourcesServer.commons.properties;

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

}
