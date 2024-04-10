package oath2authorizeserver.scrabdatas.service;

public interface StockHistDataService {
	
	public void startToScrawHistData(boolean saveToDb,boolean isHist);
	
	public void startToCrawStockCodeAndType();

}
