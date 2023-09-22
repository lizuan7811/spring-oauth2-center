package spring.boot.oath2.scrabdatas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import spring.boot.oath2.scrabdatas.entity.pk.StockEntityPk;

/**
 * 存Stock的Entity
 */
@Data
@Entity
@Table(name = "STOCK_CONTENT")
@IdClass(value = StockEntityPk.class)
public class StockEntity implements Serializable {

	/**
	 * 序列號
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 股票代碼
	 */
	@Id
	@Column(name = "STOCKCODE")
	private String stockCode;

	/**
	 * 成交價(收盤價)
	 */
	@Column(name = "FINALPRICE")
	private String finalPrice;

	/**
	 * 開盤價
	 */
	@Column(name = "OPENING")
	private String opening;

	/**
	 * 最高
	 */
	@Column(name = "HIGHESTPRICE")
	private String highestPrice;

	/**
	 * 最低
	 */
	@Column(name = "LOWESTPRICE")
	private String lowestPrice;

	/**
	 * 平均價
	 */
	@Column(name = "AVGPRICE")
	private String avgPrice;

	/**
	 * 成交金額
	 */
	@Column(name = "TOTALFINALPRICE")
	private String totalFinalPrice;

	/**
	 * 昨收
	 */
	@Column(name = "YESTFINALPRICE")
	private String yestFinalPrice;

	/**
	 * 漲跌幅
	 */
	@Column(name = "QUOTECHANGE")
	private String quoteChange;

	/**
	 * 漲跌
	 */
	@Column(name = "UPANDDOWN")
	private String upAndDown;

	/**
	 * 總量
	 */
	@Column(name = "TOTALAMOUNT")
	private String totalAmount;

	/**
	 * 昨量
	 */
	@Column(name = "YESTAMOUNT")
	private String yestAmount;

	/**
	 * 振幅
	 */
	@Column(name = "AMPLITUDE")
	private String amplitude;

	/**
	 * 內盤
	 */
	@Column(name = "INNE")
	private String inner;
	/**
	 * 內盤比
	 */
	@Column(name = "INNERPERCENT")
	private String innerPercent;
	/**
	 * 外盤
	 */
	@Column(name = "EXTERN")
	private String external;
	/**
	 * 外盤比
	 */
	@Column(name = "EXTERNALPERCENT")
	private String externalPercent;
	@Id
	@Column(name = "DATET")
	private String dateTime;
}
