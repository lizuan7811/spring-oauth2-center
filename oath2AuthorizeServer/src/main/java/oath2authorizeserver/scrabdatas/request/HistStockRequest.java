package oath2authorizeserver.scrabdatas.request;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HistStockRequest {
	
	@JsonProperty("selectType")
	@NotNull(message="{CRAWING.HISTSELECTTYPE.NULLOREMPTY}")
	@NotEmpty(message="{CRAWING.HISTSELECTTYPE.NULLOREMPTY}")
//	@Pattern(regexp="",message="")
	private String selectType;
	
	@JsonProperty("stockCode")
//	@NotNull(message="{CRAWING.HISTSELECTTYPE.NULLOREMPTY}")
//	@NotEmpty(message="{CRAWING.HISTSELECTTYPE.NULLOREMPTY}")
	private String stockCode;
	
	@JsonProperty("startDt")
	@Pattern(regexp="\\d{3}-\\d{2}-\\d{2}",message="{CRAWING.HISTENDDATE.PATTERN}")
	@NotNull(message="{CRAWING.HISTSTARTDATE.NULLOREMPTY}")
	@NotEmpty(message="{CRAWING.HISTSTARTDATE.NULLOREMPTY}")
	private String startDt;
	
	@JsonProperty("endDt")
	@Pattern(regexp="\\d{3}-\\d{2}-\\d{2}",message="{CRAWING.HISTENDDATE.PATTERN}")
	@NotNull(message="{CRAWING.HISTENDDATE.NULLOREMPTY}")
	@NotEmpty(message="{CRAWING.HISTENDDATE.NULLOREMPTY}")
	private String endDt;
}
