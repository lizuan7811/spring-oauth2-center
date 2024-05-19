package oauth2ResourcesServer.scrabdatas.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2024/5/18
 * @time: 下午 05:11
 **/
public class RSI {
    private final Queue<Double> gain;
    private final Queue<Double> loss;
    private final int period;
    private double avgGain;
    private double avgLoss;

    public RSI(int period) {
        this.period = period-1;
        this.gain = new LinkedList<>();
        this.loss = new LinkedList<>();
    }

    public double calculate(double prevPrice, double currentPrice) {
        double change = currentPrice - prevPrice;

        if (gain.size() == period) {
            avgGain = (avgGain * (period - 1) + Math.max(0, change)) / period;
            avgLoss = (avgLoss * (period - 1) + Math.max(0, -change)) / period;
        } else {
            gain.add(Math.max(0, change));
            loss.add(Math.max(0, -change));
            if (gain.size() == period) {
                avgGain = gain.stream().mapToDouble(a -> a).average().orElse(0);
                avgLoss = loss.stream().mapToDouble(a -> a).average().orElse(0);
            }
        }

        if (avgLoss == 0) {
            return 100;
        } else {
            double rs = avgGain / avgLoss;
            return 100 - (100 / (1 + rs));
        }
    }
}
