package spring.boot.oath2.scrabdatas.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

public class I18nUtil {

	public static String getI18nMessage(String language,String key) {
		Locale locale=Locale.CHINESE;
		if(!StringUtils.isEmpty(language)) {
			locale=Locale.getDefault();
		}else {
			locale=new Locale(language);
		}
		ResourceBundle rb=ResourceBundle.getBundle("message",locale);
		return rb.getString(key);
	}
	
	public static String getI18nMessage(String language,String key,Object[] params) {
		Locale locale=new Locale("zh-TW");
		if(StringUtils.isEmpty(language)) {
			locale=Locale.getDefault();
		}else {
			locale=new Locale(language);
		}
		ResourceBundle rb=ResourceBundle.getBundle("message",locale);
		String value=rb.getString(key);
		return MessageFormat.format(value, params);
	}
	
}
