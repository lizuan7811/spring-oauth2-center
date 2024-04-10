package oath2authorizeserver.scrabdatas.service;

import java.util.List;

import oath2authorizeserver.scrabdatas.exception.FindHistDataException;
import oath2authorizeserver.scrabdatas.model.StockHistModel;
import oath2authorizeserver.scrabdatas.request.HistStockRequest;

public interface HistStockDataService {
	public List<StockHistModel> selectHists(HistStockRequest histStockRequest)throws FindHistDataException;
	public List<StockHistModel> selectHists();

}
