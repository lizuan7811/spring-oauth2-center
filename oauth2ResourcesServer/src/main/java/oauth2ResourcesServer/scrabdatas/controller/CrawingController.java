package oauth2ResourcesServer.scrabdatas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import oauth2ResourcesServer.scrabdatas.property.ScrawProperty;
import oauth2ResourcesServer.scrabdatas.service.DailyStockTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import oauth2ResourcesServer.scrabdatas.service.CrawingStockService;
import oauth2ResourcesServer.scrabdatas.service.StockHistDataService;

/**
 * 執行 Crawing Stocke Data的controller
 */
@RestController
@RequestMapping("/crawing")
@Slf4j
public class CrawingController {

    private final CrawingStockService crawingStockService;
    private final StockHistDataService stockHistDataService;
    private final DailyStockTradeService dailyStockTradeService;
    private final ScrawProperty scrawProperty;

    @Autowired
    public CrawingController(CrawingStockService crawingStockService, StockHistDataService stockHistDataService, DailyStockTradeService dailyStockTradeService, ScrawProperty scrawProperty) {
        this.crawingStockService = crawingStockService;
        this.stockHistDataService = stockHistDataService;
        this.dailyStockTradeService = dailyStockTradeService;
        this.scrawProperty = scrawProperty;
    }

    @GetMapping(value = "/quoteStock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getStockContent(HttpServletRequest httpRequest) {
        List<String> respList = crawingStockService.quoteStockContents();
        return respList;
    }

    @GetMapping(value = "/quoteStock/{stockCode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStockContent(HttpServletRequest httpRequest, @PathParam("stockCode") String stockCode) {
        String respString = crawingStockService.quoteStockContentByCode(stockCode);
        return respString;
    }

    /**
     * 更新股票內容
     */
    @Deprecated
    @GetMapping(value = "/quoteStock/update")
    public String updateStockContent() {
        log.debug(">>> Update Stock Content!");
        crawingStockService.updateContents();
        return "update";
    }

    /**
     * 開始爬取歷史資料
     */
    @Deprecated
    @GetMapping(value = "/scrawinghist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String startToScrawStockHistData(boolean saveToDb, boolean isHist) {
        log.debug(">>> Start to scrawing hist data!");
        resetProxy();
        new Thread(new Runnable() {
            @Override
            public void run() {
                stockHistDataService.startToScrawHistData(saveToDb, isHist);
            }
        }).start();

        return "start to scrawing hist data";
    }

    @GetMapping(value = "/updateDailyTD", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateDailyTradeData(String startDt, String endDt, String saveMode) {
        log.debug(">>> Start to update Daily Trade Data!");
        new Thread(() -> dailyStockTradeService.updateDailyTradeData(startDt, endDt, saveMode)).start();
        return "Start to update Daily Trade Data!";
    }
    @Deprecated
    @PostMapping(value = "/srawtimelydata", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTimelyData(List<String> codeList) {
        log.debug(">>> start to scrawing timely data!");
        return "";
    }

    /**
     * 重設代理
     */
    @Deprecated
    private void resetProxy() {
        log.debug(">>> Reset Proxy, http.proxyHost: {}, http.proxyPort: {}, https.proxyHost: {}, https.proxyPort: {}", scrawProperty.getHttpHost(), scrawProperty.getHttpPort(), scrawProperty.getHttpsHost(), scrawProperty.getHttpsPort());
        System.setProperty("http.proxyHost", scrawProperty.getHttpHost());
        System.setProperty("http.proxyPort", scrawProperty.getHttpPort());
        System.setProperty("https.proxyHost", scrawProperty.getHttpsHost());
        System.setProperty("https.proxyPort", scrawProperty.getHttpsPort());
    }


}
