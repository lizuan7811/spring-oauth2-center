package oauth2ResourcesServer.test;

import oauth2ResourcesServer.scrabdatas.service.DailyStockTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestCraw implements CommandLineRunner {
    @Autowired
    private DailyStockTradeService dailyStockTradeService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Start to update daily data!");
        dailyStockTradeService.updateDailyTradeData("20240501","20240522");
    }
}
