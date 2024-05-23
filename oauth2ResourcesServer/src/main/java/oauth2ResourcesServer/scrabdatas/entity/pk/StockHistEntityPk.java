package oauth2ResourcesServer.scrabdatas.entity.pk;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHistEntityPk implements Serializable {
	/**
	 * 序列號
	 */
	private static final long serialVersionUID = 1L;
	protected String stockCode;
	protected String date;
}
