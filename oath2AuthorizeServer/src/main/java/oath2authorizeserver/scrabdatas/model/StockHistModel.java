package oath2authorizeserver.scrabdatas.model;

import lombok.Data;

@Data
public class StockHistModel {
	private String stockCode;
	private String date;
	private String transactVolume;
	private String totalPrice;
	private String startPrice;
	private String highestPrice;
	private String lowestPrice;
	private String endPrice;
	private String upAndDown;
	private String sharesTradedNum;
}
