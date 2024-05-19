package oauth2ResourcesServer.scrabdatas.model;

import liquibase.pro.packaged.B;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: Stock RSI
 * @author: Lizuan
 * @date: 2024/5/18
 * @time: 下午 03:13
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRSI {
    private String stockCode;
    private String stockName;
    private List<Double> rsiValues;
}
