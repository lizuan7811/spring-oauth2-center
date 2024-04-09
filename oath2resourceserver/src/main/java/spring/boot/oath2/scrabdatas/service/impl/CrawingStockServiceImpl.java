package spring.boot.oath2.scrabdatas.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import spring.boot.oath2.scrabdatas.entity.StockEntity;
import spring.boot.oath2.scrabdatas.model.StockModel;
import spring.boot.oath2.scrabdatas.persistent.StockRepo;
import spring.boot.oath2.scrabdatas.service.CrawingStockService;
import spring.boot.oath2.scrabdatas.util.CrawingStockDatas;

/**
 * 查詢歷史資料 Impl
 */
@Service
@Slf4j
public class CrawingStockServiceImpl implements CrawingStockService {

	private CrawingStockDatas crawingStockDatas;

	private final StockRepo stockRepo;

	@Autowired
	public CrawingStockServiceImpl(CrawingStockDatas crawingStockDatas, StockRepo stockRepo) {
		this.crawingStockDatas = crawingStockDatas;
		this.stockRepo = stockRepo;
	}

	@Override
	public List<String> quoteStockContents() {

		return null;
	}

	@Override
	public String quoteStockContentByCode(String stockCode) {
		return null;
	}

	@Override
	public List<Map<String, StockModel>> tranStockContent() {
		return null;
	}

	@Override
	public List<Map<String, StockModel>> addAndUpdateContent() {
		return null;
	}

	@Override
	public void updateContents() {
		setCrawingStockDatas(new CrawingStockDatas(false));
		stockRepo.saveAll(crawingStockDatas.getStockEntityList());
	}

	/**
	 * 更新時資料內容時，使用的更新CrawingStockData類別的方法
	 */
	private void setCrawingStockDatas(CrawingStockDatas crawingStockDatas) {
		this.crawingStockDatas = crawingStockDatas;
	}

	@Override
	public void saveOneContents(Object object) {
		StockEntity se = crawingStockDatas.getStockEntityList().get(0);
		log.debug(">>> {}",se);
		stockRepo.save(se);
	}

}
