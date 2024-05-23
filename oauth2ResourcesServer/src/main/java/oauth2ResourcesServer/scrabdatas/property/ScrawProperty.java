package oauth2ResourcesServer.scrabdatas.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
@Data
@ConfigurationProperties(prefix="scraw")
public class ScrawProperty {
	private String stockHistFqdn;
	private String dailyTradedataFqdn;
	private String stockPureCodeFile;
	private String stockHistdataFile;
	private String httpHost;
	private String httpPort;
	private String httpsHost;
	private String httpsPort;
	private Integer startYear;
	private Integer baseRandTime;
	private Integer randTime;
	private String cacertPath;
}
