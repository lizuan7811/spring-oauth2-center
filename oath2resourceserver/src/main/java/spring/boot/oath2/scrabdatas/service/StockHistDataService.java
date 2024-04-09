package spring.boot.oath2.scrabdatas.service;

public interface StockHistDataService {
	
	public void startToScrawHistData(boolean saveToDb,boolean isHist);
	
	public void startToCrawStockCodeAndType();

}
