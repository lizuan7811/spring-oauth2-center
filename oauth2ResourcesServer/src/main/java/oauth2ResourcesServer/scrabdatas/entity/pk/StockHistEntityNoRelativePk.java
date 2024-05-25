package oauth2ResourcesServer.scrabdatas.entity.pk;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Builder
public class StockHistEntityNoRelativePk implements Serializable {
	/**
	 * 序列號
	 */
	@Column(name = "STOCKCODE")
	protected String stockCode;

	@Column(name = "DATET")
	protected String date;
}
