package oauth2ResourcesServer;

import oauth2ResourcesServer.scrabdatas.service.impl.DailyStockTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootApplication(scanBasePackages = {"oauth2ResourcesServer.commons.properties", "oauth2ResourcesServer.websecurity", "oauth2ResourcesServer.redis", "oauth2ResourcesServer.scrabdatas", "oauth2ResourcesServer.oath2","oauth2ResourcesServer.test"})
@ConfigurationPropertiesScan
public class OAuth2ResourcesServerStarter {
	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourcesServerStarter.class, args);



	}
}
