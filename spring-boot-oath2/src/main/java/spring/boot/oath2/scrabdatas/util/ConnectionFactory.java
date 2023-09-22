package spring.boot.oath2.scrabdatas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;

import spring.boot.oath2.scrabdatas.property.ScrawProperty;

public class ConnectionFactory {
	private static HttpsURLConnection httpsURLConnection;
	
	private static BufferedReader bufReader = null;

	public static HttpsURLConnection getConnectionInst(URL url) throws IOException {
		httpsURLConnection = (HttpsURLConnection) url.openConnection();
		httpsURLConnection.setRequestMethod("GET");
		httpsURLConnection.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
		httpsURLConnection.setInstanceFollowRedirects(false); // 禁止自动重定向
		httpsURLConnection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded;charset="+"UTF-8");
//		httpsURLConnection.setRequestProperty("Connection", "keep-alive");
		System.out.println(url);
		int statusCode = httpsURLConnection.getResponseCode();
		System.out.println(statusCode);
		if (statusCode == HttpURLConnection.HTTP_MOVED_PERM || statusCode == HttpURLConnection.HTTP_MOVED_TEMP) {
			String location = httpsURLConnection.getHeaderField("Location"); // 获取重定向后的新 URL 地址
			System.out.println(">>> location" + location);
			URL mvdUrl = new URL(location);
			httpsURLConnection = (HttpsURLConnection) mvdUrl.openConnection();
		}
		return httpsURLConnection;
	}

	public static void disConnection() {
		try {
			if (Objects.nonNull(bufReader))
				bufReader.close();
			if (Objects.nonNull(httpsURLConnection))
				httpsURLConnection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedReader getBufferReader(HttpURLConnection connection) {
		try {
			bufReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufReader;
	}

	public static long randMill(int baseRandTime,int randTime) {
		return new SecureRandom().nextInt(randTime)+baseRandTime;
	}

}
