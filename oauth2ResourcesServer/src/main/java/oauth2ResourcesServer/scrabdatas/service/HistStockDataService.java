package oauth2ResourcesServer.scrabdatas.service;

import java.util.List;

import oauth2ResourcesServer.scrabdatas.exception.FindHistDataException;
import oauth2ResourcesServer.scrabdatas.model.StockHistModel;
import oauth2ResourcesServer.scrabdatas.model.StockRSI;
import oauth2ResourcesServer.scrabdatas.request.HistStockRequest;

public interface HistStockDataService {
	List<StockHistModel> selectHists(HistStockRequest histStockRequest)throws FindHistDataException;
	List<StockHistModel> selectHists();

	List<StockRSI> queryRSI(List<String> stockCodes,Integer periodDays);

	List<StockRSI> queryMAs(List<String> stockCodes,Integer periodDays);

}
