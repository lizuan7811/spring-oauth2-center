package oauth2ResourcesServer.scrabdatas.model;

import lombok.Data;

/**
 * 存Stock的model
 */
@Data
public class StockModel {

	/**
	 * 股票代碼
	 */
	private String stockCode;
	/**
	 * 成交價(收盤價)
	 */
	private String finalPrice = " ";

	/**
	 * 開盤價
	 */
	private String opening = " ";

	/**
	 * 最高
	 */
	private String highestPrice = " ";

	/**
	 * 最低
	 */
	private String lowestPrice = " ";

	/**
	 * 平均價
	 */
	private String avgPrice = " ";

	/**
	 * 成交金額
	 */
	private String totalFinalPrice = " ";

	/**
	 * 昨收
	 */
	private String yestFinalPrice = " ";

	/**
	 * 漲跌幅
	 */
	private String quoteChange = " ";

	/**
	 * 漲跌
	 */
	private String upAndDown = " ";

	/**
	 * 總量
	 */
	private String totalAmount = " ";

	/**
	 * 昨量
	 */
	private String yestAmount = " ";

	/**
	 * 振幅
	 */
	private String amplitude = " ";

	/**
	 * 內盤
	 */
	private String inner = " ";
	/**
	 * 內盤比
	 */
	private String innerPercent = " ";
	/**
	 * 外盤
	 */
	private String external = " ";
	/**
	 * 外盤比
	 */
	private String externalPercent = " ";

	/**
	 * 時間
	 */
	private String dateTime;

	@Override
	public String toString() {
		return stockCode + ";" + finalPrice + ";" + opening + ";" + highestPrice + ";" + lowestPrice + ";" + avgPrice
				+ ";" + totalFinalPrice + ";" + yestFinalPrice + ";" + quoteChange + ";" + upAndDown + ";" + totalAmount
				+ ";" + yestAmount + ";" + amplitude + ";" + inner + ";" + innerPercent + ";" + external + ";"
				+ externalPercent + ";" + dateTime;
	}

}
