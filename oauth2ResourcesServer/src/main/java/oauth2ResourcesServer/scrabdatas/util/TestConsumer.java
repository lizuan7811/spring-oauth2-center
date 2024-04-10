package oauth2ResourcesServer.scrabdatas.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

import org.apache.logging.log4j.util.Strings;


public class TestConsumer {
	public static void main(String[] args) {
		
		
		testConsumer("rdLine", "FileName",(Consumer<String>)(e->System.out.println(e)));
		
	}

	public static <T>void testConsumer(T rdLine, String fileName, Consumer<T>consumer) {
//		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND)){
//			System.out.println(rdLine);
		consumer.accept(rdLine);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
