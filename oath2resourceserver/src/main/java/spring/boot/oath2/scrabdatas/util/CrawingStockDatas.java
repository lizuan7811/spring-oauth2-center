package spring.boot.oath2.scrabdatas.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import spring.boot.oath2.scrabdatas.entity.StockEntity;
import spring.boot.oath2.scrabdatas.model.StockModel;
import spring.boot.oath2.scrabdatas.property.ScrawProperty;

/**
 * 爬YAHOO資料工具
 */
@Component
public class CrawingStockDatas {

	@Autowired
	private ScrawProperty scrawProperty;
	
	private static URL STOCKCODE_FQDN;
	private static URL TARGET_FQDN;

	private final String URL_PATTERN = "(http|https)://[^\\s/$.?#].[^\\s]*$";
	private final String FMT_URL_PATTERN = "((https?://)?/?[^\"']+)[\\\"']?.*?";
//	private final String HTML_URL_PATTERN = "a.*?href=[\\\"']?((https?://)?/?[^\"']+)[\\\"']?.*?>(.+)</a>";
	private final String STOCK_POINT_PATTERN = "https?://.*/(quote)?/?.*";
	private final String PREFIX_FQDN = "https://tw.stock.yahoo.com/quote/";
	private final String STOCK_PURE_CODE_FILE = "C:\\Users\\Lizuan\\Desktop\\DevTest_dir\\stockpurecode.txt";
	private final String STOCK_CODE_FILE = "C:\\Users\\Lizuan\\Desktop\\DevTest_dir\\stockcode.txt";
	private final String URL_FILE_NAME = "C:\\Users\\Lizuan\\Desktop\\DevTest_dir\\utls.txt";
	private final String CONTENT_FILE_NAME = "C:\\Users\\Lizuan\\Desktop\\DevTest_dir\\contents.txt";
	private final String START_DIV_CLASS = "Fx(n) W(316px) Bxz(bb) Pstart(16px) Pt(12px)";

	private String localDate = "";

	private final List<StockModel> stockModelList = new ArrayList<StockModel>();
	private final List<StockEntity> stockEntityList = new ArrayList<StockEntity>();
	static {
		try {
			STOCKCODE_FQDN = new URL("https://stock.wespai.com/p/3752");
			TARGET_FQDN = new URL("https://tw.stock.yahoo.com/quote/2330");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 爬蟲類別的建構子
	 */
	public CrawingStockDatas() {
		this.localDate = NormalUtils.getLocalDate();
	}

	public CrawingStockDatas(boolean fromInternet) {
		this.localDate = NormalUtils.getLocalDate();
		initStockTable(STOCK_CODE_FILE, fromInternet);
	}

	public List<StockModel> getStockModelList() {
		return this.stockModelList;
	}

	public List<StockEntity> getStockEntityList() {
		modelToEntity();
		return this.stockEntityList;
	}

	/**
	 * 轉換model為Entity
	 */
	private void modelToEntity() {
		this.stockModelList.stream().forEach(model -> {
			StockEntity entity = new StockEntity();
			BeanUtils.copyProperties(model, entity);
			stockEntityList.add(entity);
		});
	}

	/**
	 * 初始化stock code table
	 */
	private void initStockTable(String fileName, boolean fromInternet) {
		boolean isExist = NormalUtils.ifFileNotExitThenCreate(fileName);
//		建立儲存stock code的file
		if (isExist) {
//			檔案存在且時間不超過30天。
			boolean isValid = ifFileTimeValid(fileName);
			updateFileIfNotValid(fileName, isValid);
		} else {
//		檔案不存在
			initIfFileNotExist(fileName);
		}
//		爬取STOCKCODE_FQDN 的stock code
		scrawStockCodeAndSaveToFile(fileName, fromInternet, this.stockModelList);
	}

	private void updateFileIfNotValid(String fileName, boolean isValid) {
		if (!isValid) {
			Paths.get(fileName).toFile().delete();
			initIfFileNotExist(fileName);
		}
	}

	private void scrawStockCodeAndSaveToFile(String fileName, boolean fromInternet, List<StockModel> stockModelList) {
//		讀StockFile檔案存成List。
		try {
			if (fromInternet) {
				List<String> stockFqdnList = Files.readAllLines(Paths.get(fileName));
				stockFqdnList.stream().forEach(url -> {
					stockModelList.add(scrawStockByFqdn(url, fromInternet));
				});
			} else {
				stockModelList.addAll(scrawStockByFile(fileName, fromInternet));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 讀取Stock的網址並儲存取得的資料
	 */
	private StockModel scrawStockByFqdn(String urlString, boolean fromInternet) {
		StockModel stockModel = null;
		NormalUtils.ifFileNotExitThenCreate(CONTENT_FILE_NAME);
		try (BufferedWriter bw = new BufferedWriter(
				Files.newBufferedWriter(Paths.get(CONTENT_FILE_NAME), StandardOpenOption.APPEND))) {

			Thread.sleep(ConnectionFactory.randMill(scrawProperty.getBaseRandTime(),scrawProperty.getRandTime()));

			URL url = new URL(urlString);

			HttpURLConnection connection = ConnectionFactory.getConnectionInst(url);

			StringBuilder sb = new StringBuilder();

			if (connection.getResponseCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				stockModel = parseData(sb.toString(), fromInternet);
				stockModel.setStockCode(urlString.substring(urlString.lastIndexOf('/') + 1));
				System.out.println(stockModel.toString());
				bw.write(stockModel.toString());
				bw.write(System.lineSeparator());
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.disConnection();
			System.out.println("Finally");
		}
		return stockModel;
	}

	private List<StockModel> scrawStockByFile(String fileName, boolean fromInternet) {
		List<StockModel> modelList = new ArrayList<StockModel>();
		try (BufferedReader br = new BufferedReader(Files.newBufferedReader(Paths.get(CONTENT_FILE_NAME)))) {
			modelList = br.lines().map(line -> {
				return parseData(line, fromInternet);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelList;
	}

//	public void jsoupPrint() {
//		try {
//			org.jsoup.nodes.Document doc = Jsoup.connect(TARGET_FQDN.toString()).data("query", "Java")
//					.userAgent("Mozilla").cookie("auth", "token").timeout(3000).get();
//
//			Elements hrefElements = doc.select("a[href]");
//			for (Element href : hrefElements) {
//				System.out.println(href);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 解析html內容
	 */
//	public void parseDate() {
//		try (BufferedReader br = Files.newBufferedReader(Paths.get(CONTENT_FILE_NAME))) {
//			String rdLine = StringUtils.EMPTY;
//			while ((rdLine = br.readLine()) != null) {
//				org.jsoup.nodes.Document doc = Jsoup.parse(rdLine);
//				if (org.apache.commons.lang.StringUtils.isBlank(doc.getElementsByClass(START_DIV_CLASS).text()))
//					continue;
//				Elements elem = doc.getElementsByClass(START_DIV_CLASS);
//				Elements spanElem = elem.select("span");
//				String spanString = spanElem.text();
//				int firIndex = spanString.indexOf("外盤");
//				if (firIndex != -1) {
//					String firString = spanString.substring(0, firIndex);
//					List<String> fmtFirList = Arrays.asList(firString.split(" ")).stream()
//							.filter(fir -> fir.matches("^\\(?(\\d)+.*")).collect(Collectors.toList());
////					fmtFirList.forEach(System.out::println);
//					System.out.println(transToObject(StockModel.class, fmtFirList));
//				}
//				System.out.println("==============================");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 解析html內容
	 */
	public StockModel parseData(String rdLine, boolean fromInternet) {
		StockModel stockModel = new StockModel();
		if (StringUtils.isNotBlank(rdLine)) {
			List<String> fmtFirList = new ArrayList<String>();
			if (fromInternet) {
				Document doc = Jsoup.parse(rdLine);
				Elements elem = doc.getElementsByClass(START_DIV_CLASS);
				Elements spanElem = elem.select("span");
				String spanString = spanElem.text();
				int firIndex = spanString.indexOf("外盤");
				System.out.println(spanString);
				if (firIndex != -1) {
					String firString = spanString.substring(0, firIndex);

					String[] firArray = firString.split(" ");

					fmtFirList = Arrays.asList(firArray).stream()
							.filter(fir -> fir.matches("^\\(?(\\d)+.*") || fir.matches("^\\(?-\\(?-?.*"))
							.collect(Collectors.toList());

					stockModel = (StockModel) NormalUtils.transToObject(StockModel.class, fmtFirList);
				}
			} else {
				fmtFirList = Arrays.asList(rdLine.split(";")).stream()
						.filter(fir -> fir.matches("^\\(?(\\d)+.*") || fir.matches("^\\(?-\\(?-?.*"))
						.collect(Collectors.toList());
				stockModel = (StockModel) NormalUtils.fileToObject(StockModel.class, fmtFirList);
			}
		}
		System.out.println(stockModel.toString());
		return stockModel;
	}

//	/**
//	 * 將網路爬取的資料轉為Object
//	 */
//	private <T> Object transToObject(Class<T> clasT, List<String> sourceList) {
//		try {
//			T objT = (T) clasT.getDeclaredConstructor(null).newInstance(null);
//			Field[] fields = clasT.getDeclaredFields();
//			int fieldIndex = 0;
//			for (int i = 0; i <= sourceList.size(); i++) {
//				ReflectionUtils.makeAccessible(fields[i]);
//				if (fields[i].getName().equals("stockCode")) {
//					continue;
//				}
//				fields[i].set(objT, sourceList.get(i - 1));
//				fieldIndex = i;
//			}
//			if(objT instanceof StockModel) {
//				ReflectionUtils.makeAccessible(fields[fieldIndex + 1]);
//				fields[fieldIndex + 1].set(objT, localDate);
//			}
//			return objT;
//		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
//				| NoSuchMethodException | SecurityException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 從文件的資料轉為Obejct
	 */
//	private <T> Object fileToObject(Class<T> clasT, List<String> sourceList) {
//		try {
//			T objT = (T) clasT.getDeclaredConstructor(null).newInstance(null);
//			Field[] fields = clasT.getDeclaredFields();
//			for (int i = 0; i < sourceList.size(); i++) {
//				ReflectionUtils.makeAccessible(fields[i]);
//				fields[i].set(objT, sourceList.get(i));
//			}
//			return objT;
//		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
//				| NoSuchMethodException | SecurityException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 檔案若不存在就建立
	 */
	private void initIfFileNotExist(String fileName) {
		try (BufferedWriter bw = new BufferedWriter(
				Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND))) {
			Thread.sleep(1000);

			HttpURLConnection connection = ConnectionFactory.getConnectionInst(STOCKCODE_FQDN);

			if (connection.getResponseCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = "";

				Pattern p = Pattern.compile(FMT_URL_PATTERN);
				Set<String> codeSet = new TreeSet<String>();
				while ((line = reader.readLine()) != null) {
					if (line.matches(".*youtu.*")) {
						continue;
					}
					org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(line);
					Elements tableEle = doc.getElementsByTag("body");
					tableEle.stream().filter(tagValue -> tagValue.text().matches("^(\\d){4,6}$")).forEach(tagValue -> {
						codeSet.add(tagValue.text());
					});
				}
				codeSet.forEach(code -> {
					try {
						bw.write(PREFIX_FQDN + code);
						bw.write(System.lineSeparator());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.disConnection();
			System.out.println("InitStockCodeTable!");
		}
	}

	/**
	 * 解析html檔案內容
	 */
	public void parseHtml(String filename, String tagName, String regex) {
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
			String rdLine = StringUtils.EMPTY;
			while ((rdLine = br.readLine()) != null) {
				org.jsoup.nodes.Document doc = Jsoup.parse(rdLine);
//				if (org.apache.commons.lang.StringUtils.isBlank(doc.getElementsByClass(SPAN_CLASS_UP).text()))
//					continue;
				System.out.println(doc.getElementsByClass(tagName).text());
//				System.out.println(doc.getElementsByClass(SPAN_CLASS_PRICE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 爬取網頁取得source code的IP
	 */
	public void getURL() {
		Map<String, Boolean> oldMap = new LinkedHashMap<String, Boolean>();
		String oldLinkHost = "";
		Pattern p = Pattern.compile(URL_PATTERN);
		Matcher m = p.matcher(TARGET_FQDN.toString());
		if (m.find()) {
			oldLinkHost = m.group();
			System.out.println("SouceURL:\t" + oldLinkHost);

			oldMap.put(TARGET_FQDN.toString(), false);
			long start1 = System.currentTimeMillis();
			crawlLinks(oldLinkHost, oldMap, 3);
			long start2 = System.currentTimeMillis();
			System.out.println(start2 - start1);

			for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
				System.out.println("Linked: " + mapping.getKey());
			}
		}
	}

	/**
	 * 判斷檔案時間是否在範圍內
	 */
	private boolean ifFileTimeValid(String fileName) {
		boolean isValid = false;
		try {
			long fileTime = FileUtils.lastModified(Paths.get(fileName).toFile());
			LocalDateTime fileDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(fileTime), ZoneId.systemDefault());
			LocalDateTime nowDt = LocalDateTime.now();
			int days = (int) Duration.between(fileDt, nowDt).toDays();
			System.out.println(days);
//			30天更新一次，可以改用calendar指定每月一號更新
			isValid = days >= 0 && days <= 30;
			System.out.println(isValid);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return isValid;
	}

	/**
	 * 檢查連線是否被redirect，若被move則取得move後的連線
	 */
	private HttpURLConnection checkIsMoved(HttpURLConnection connection) throws IOException {
		int statusCode = connection.getResponseCode();
		if (statusCode == HttpURLConnection.HTTP_MOVED_PERM || statusCode == HttpURLConnection.HTTP_MOVED_TEMP) {
			String location = connection.getHeaderField("Location"); // 获取重定向后的新 URL 地址
			URL url = new URL(location);
			connection = (HttpURLConnection) url.openConnection();
		}
		return connection;
	}

	/**
	 * 開啟connection並讀取網頁內容
	 */
	public void crawlLinks(String oldLinkHost, Map<String, Boolean> oldMap, int count) {
		System.out.println(">>>count: " + count);
		if (count <= 0) {
			return;
		}

		System.out.println("==crawlLinks==");

		Map<String, Boolean> newMap = new LinkedHashMap<String, Boolean>();

		String oldLink = "";

		for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {

			oldLink = mapping.getKey();
			if (mapping.getValue() || org.apache.commons.lang.StringUtils.isBlank(oldLink)) {
				continue;
			}

			NormalUtils.ifFileNotExitThenCreate(URL_FILE_NAME);

			NormalUtils.ifFileNotExitThenCreate(CONTENT_FILE_NAME);

			try (BufferedWriter bw = new BufferedWriter(
					Files.newBufferedWriter(Paths.get(URL_FILE_NAME), StandardOpenOption.APPEND));
					BufferedWriter bwContent = Files.newBufferedWriter(Paths.get(CONTENT_FILE_NAME),
							StandardOpenOption.APPEND)) {

				Thread.sleep(1000);

				URL url = new URL(oldLink);

				HttpURLConnection connection = ConnectionFactory.getConnectionInst(url);

				if (connection.getResponseCode() == 200) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String line = "";

					Pattern p = Pattern.compile(FMT_URL_PATTERN);

					Function<String, String> func = getHttpFqdnFunc(oldLinkHost, p);

					while ((line = reader.readLine()) != null) {
						if (line.matches(".*youtu.*")) {
							continue;
						}

						List<String> lineArrayList = Arrays.asList(line.replaceAll("href=\"", "\n").split("\n"));

						lineArrayList.forEach(inLine -> {
							try {
								String newLine = func.apply(inLine);
								System.out.println(newLine);
								bw.write(newLine);
								bw.write(System.lineSeparator());
								bwContent.write(inLine);
								bwContent.write(System.lineSeparator());
								saveToMap(oldMap, newMap, oldLinkHost, newLine);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ConnectionFactory.disConnection();
				System.out.println("Finally");
			}
			oldMap.replace(oldLink, false, true);
		}
		if (!newMap.isEmpty()) {
			System.out.println("!newMap.isEmpty()");
			crawlLinks(oldLinkHost, newMap, --count);
			oldMap.putAll(newMap);
		}
		return;
	}

	/**
	 * 將網址存入map
	 */
	private void saveToMap(Map<String, Boolean> oldMap, Map<String, Boolean> newMap, String oldLinkHost,
			String newLink) {
		if (!oldMap.containsKey(newLink) && !newMap.containsKey(newLink) && !newLink.startsWith(oldLinkHost)) {
			newMap.put(newLink, false);
		}
	}

	/**
	 * 將取得的source code過濾出需要的http網址格式
	 */
	public Function<String, String> getHttpFqdnFunc(String oldLinkHost, Pattern p) {
		return new Function<String, String>() {
			@Override
			public String apply(String t) {
				Matcher innerMatcher = (Matcher) p.matcher(t);
				System.out.println(t);
				System.out.println(innerMatcher.matches() && t.matches(STOCK_POINT_PATTERN));
				if (innerMatcher.matches() && t.matches(STOCK_POINT_PATTERN)) {
					String newLink = innerMatcher.group(1).trim();
					if (!newLink.startsWith("http")) {
						if (newLink.startsWith("/")) {
							newLink = oldLinkHost + newLink;
						} else {
							newLink = oldLinkHost + "/" + newLink;
						}
					}
					if (newLink.endsWith("/")) {
						newLink = newLink.substring(0, newLink.length() - 1);
					}
					return newLink;
				}
				return "";
			}
		};
	}

	public void initStockCodeFile() {

		String fileName = STOCK_PURE_CODE_FILE;

		NormalUtils.ifFileNotExitThenCreate(fileName);

		try (BufferedWriter bw = new BufferedWriter(
				Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND))) {
			Thread.sleep(1000);

			HttpURLConnection connection = ConnectionFactory.getConnectionInst(STOCKCODE_FQDN);

			if (connection.getResponseCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = "";

				Pattern p = Pattern.compile(FMT_URL_PATTERN);
				Set<String> codeSet = new TreeSet<String>();
				while ((line = reader.readLine()) != null) {
					if (line.matches(".*youtu.*")) {
						continue;
					}
					org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(line);
					Elements tableEle = doc.getElementsByTag("body");
//					tableEle.stream().filter(tagValue -> tagValue.text().matches("^(\\d){4,6}$")).forEach(System.out::println);
					tableEle.stream().filter(tagValue -> tagValue.text().matches("^(\\d){4,6}$")).forEach(tagValue -> {
						codeSet.add(tagValue.text());
					});
				}
				codeSet.forEach(code -> {
					try {
						bw.write(code);
						bw.write(System.lineSeparator());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.disConnection();
			System.out.println("InitStockCodeTable!");
		}
	}

}
