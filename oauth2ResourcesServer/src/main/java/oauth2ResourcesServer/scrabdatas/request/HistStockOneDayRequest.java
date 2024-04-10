package oauth2ResourcesServer.scrabdatas.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class HistStockOneDayRequest {
	@JsonProperty("stockCode")
	private String stockCode;
	@JsonProperty("startDt")
	private String startDt;
	@JsonProperty("endDt")
	private String endDt;
}
