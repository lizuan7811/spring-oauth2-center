package oath2authorizeserver.scrabdatas.entity.pk;

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
	private String stockCode;
	private String date;
}
