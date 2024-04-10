package oauth2ResourcesServer.scrabdatas.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import oauth2ResourcesServer.scrabdatas.model.StockModel;


/**
 * 查詢Stock資料 Service
 */
public interface CrawingStockService {
	
	public List<String> quoteStockContents();
	
	public String quoteStockContentByCode(String stockCode);

	public List<Map<String,StockModel>> tranStockContent();
	
	public List<Map<String,StockModel>> addAndUpdateContent();
	
	/**
	 * 更新資料內容
	 */
	public void updateContents();
	
	/**
	 * 更新一筆資料
	 */
	public void saveOneContents(Object object);
	
}
