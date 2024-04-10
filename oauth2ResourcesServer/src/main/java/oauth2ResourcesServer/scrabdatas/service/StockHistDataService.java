package oauth2ResourcesServer.scrabdatas.service;

public interface StockHistDataService {
	
	public void startToScrawHistData(boolean saveToDb,boolean isHist);
	
	public void startToCrawStockCodeAndType();

}
