package oath2authorizeserver.scrabdatas.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import oath2authorizeserver.scrabdatas.model.HtmlParseResult;
import oath2authorizeserver.scrabdatas.model.StockModel;

public class NormalUtils {
	public static boolean ifFileNotExitThenCreate(String fileName) {
		boolean isExist = Paths.get(fileName).toFile().exists();
		if (!isExist) {
			try {
				Paths.get(fileName).toFile().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isExist;
	}

	public static <T> Object transToObject(Class<T> clasT, List<String> sourceList) {
		try {
			T objT = (T) clasT.getDeclaredConstructor(null).newInstance(null);
			Field[] fields = clasT.getDeclaredFields();
			int fieldIndex = 0;
			for (int i = 0; i <= sourceList.size(); i++) {
				ReflectionUtils.makeAccessible(fields[i]);
				if (fields[i].getName().equals("stockCode")) {
					continue;
				} else if (fields[i].getName().equals("date")) {
					fields[i].set(objT, sourceList.get(i - 1).replaceAll("/", "-"));
					continue;
				}

				fields[i].set(objT, sourceList.get(i - 1));
				fieldIndex = i;
			}
			if (objT instanceof StockModel) {
				ReflectionUtils.makeAccessible(fields[fieldIndex + 1]);
				fields[fieldIndex + 1].set(objT, getLocalDate());
			}
			return objT;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> Object fileToObject(Class<T> clasT, List<String> sourceList) {
		try {
			T objT = (T) clasT.getDeclaredConstructor(null).newInstance(null);
			Field[] fields = clasT.getDeclaredFields();
			for (int i = 0; i < sourceList.size(); i++) {
				ReflectionUtils.makeAccessible(fields[i]);
				fields[i].set(objT, sourceList.get(i));
			}
			return objT;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getLocalDate() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public static <T> Object parseJson(String jsonString, Class<T> classT) {
		ObjectMapper objMap = new ObjectMapper();
		try {
			if (StringUtils.isNotBlank(jsonString)) {
				return (T) objMap.readValue(jsonString, classT);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document parseHtmlToDoc(String rdLine) {
		Document doc = Jsoup.parse(rdLine);
		return doc;
	}
}
