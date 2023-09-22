package spring.boot.oath2.scrabdatas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import spring.boot.oath2.scrabdatas.service.StockHistDataService;
import spring.boot.oath2.scrabdatas.util.CrawHistStockData;

@Service
@Slf4j
public class StockHistDataServiceImpl implements StockHistDataService {

	@Autowired
	private CrawHistStockData crawHistStockData;
	
	@Override
	public void startToScrawHistData(boolean saveToDb,boolean isHist) {
		log.debug(">>> Start to sraw StockHistData !");
		crawHistStockData.startScrawHistData(saveToDb,isHist);
	}
	@Override
	public void startToCrawStockCodeAndType() {
		log.debug(">>> Start to sraw StockType And Code !");
		crawHistStockData.startCrawStockcodeAndType();
	}

}
