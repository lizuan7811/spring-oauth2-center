package oauth2ResourcesServer.test;

import oauth2ResourcesServer.scrabdatas.service.HistStockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2024/5/26
 * @time: 下午 06:01
 **/
@Component
public class OncePerStart implements CommandLineRunner {
    private final HistStockDataService histStockDataService;

    @Autowired
    public OncePerStart(HistStockDataService histStockDataService) {
        this.histStockDataService = histStockDataService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> stocks=new ArrayList<>();

        histStockDataService.queryMAs(stocks,60);
    }
}
