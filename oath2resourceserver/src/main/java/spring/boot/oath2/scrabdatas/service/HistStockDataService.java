package spring.boot.oath2.scrabdatas.service;

import java.util.List;

import spring.boot.oath2.scrabdatas.exception.FindHistDataException;
import spring.boot.oath2.scrabdatas.model.StockHistModel;
import spring.boot.oath2.scrabdatas.request.HistStockRequest;

public interface HistStockDataService {
	public List<StockHistModel> selectHists(HistStockRequest histStockRequest)throws FindHistDataException;
	public List<StockHistModel> selectHists();

}
