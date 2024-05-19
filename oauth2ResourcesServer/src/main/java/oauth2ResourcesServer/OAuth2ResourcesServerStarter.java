package oauth2ResourcesServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootApplication(scanBasePackages = {"oauth2ResourcesServer.commons.properties", "oauth2ResourcesServer.websecurity", "oauth2ResourcesServer.redis", "oauth2ResourcesServer.scrabdatas", "oauth2ResourcesServer.oath2"})
@ConfigurationPropertiesScan
public class OAuth2ResourcesServerStarter {
	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourcesServerStarter.class, args);
	}
//	public static void main(String[] args) {
//		Calendar currentCalendar=Calendar.getInstance();
//		Date curDate=currentCalendar.getTime();
//		currentCalendar.add(Calendar.DAY_OF_MONTH,-20);
//		Date periodDate=currentCalendar.getTime();
//		System.out.println(curDate);
//		System.out.println(periodDate);
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//
//
//
//		String startDate= sdf.format(periodDate);
//		String endDate= sdf.format(curDate);
//		System.out.println(startDate);
//		System.out.println(endDate);
//	}
}
