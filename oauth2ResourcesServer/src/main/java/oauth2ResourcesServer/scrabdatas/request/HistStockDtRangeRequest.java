package oauth2ResourcesServer.scrabdatas.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class HistStockDtRangeRequest {
	@JsonProperty("startDt")
	private String startDt;
	@JsonProperty("endDt")
	private String endDt;
}
