package oauth2ResourcesServer.scrabdatas.service;

import java.util.List;

import oauth2ResourcesServer.scrabdatas.exception.FindHistDataException;
import oauth2ResourcesServer.scrabdatas.model.StockHistModel;
import oauth2ResourcesServer.scrabdatas.request.HistStockRequest;

public interface HistStockDataService {
	public List<StockHistModel> selectHists(HistStockRequest histStockRequest)throws FindHistDataException;
	public List<StockHistModel> selectHists();

}
