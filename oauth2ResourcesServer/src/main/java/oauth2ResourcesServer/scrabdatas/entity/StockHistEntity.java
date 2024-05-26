package oauth2ResourcesServer.scrabdatas.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityPk;

@SuppressWarnings("serial")
@Entity
@Table(name = "STOCK_HIST_DATA")
@Data
@IdClass(StockHistEntityPk.class)
@NoArgsConstructor
@AllArgsConstructor
public class StockHistEntity implements Serializable {

    @Id
    @Column(name = "STOCKCODE")
    protected String stockCode;
    @Id
    @Column(name = "DATET")
    protected String datet;
    @Column(name = "TRANSACTVOLUME")
    protected String transactVolume;
    @Column(name = "TOTALPRICE")
    protected String totalPrice;
    @Column(name = "STARTPRICE")
    protected String startPrice;
    @Column(name = "HIGHESTPRICE")
    protected String highestPrice;
    @Column(name = "LOWESTPRICE")
    protected String lowestPrice;
    @Column(name = "ENDPRICE")
    protected String endPrice;
    @Column(name = "UPANDDOWN")
    protected String upAndDown;
    @Column(name = "SHARESTRADENUM")
    protected String sharesTradedNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stockCode")
    @JoinColumn(name = "stockcode", referencedColumnName = "securityCode", insertable = false, updatable = false)
    private SecuritiesEntity securitiesEntity;

    @Override
    public String toString() {
        return "StockHistEntity{" +
                "stockCode='" + stockCode + '\'' +
                ", datet='" + datet + '\'' +
                ", transactVolume='" + transactVolume + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", startPrice='" + startPrice + '\'' +
                ", highestPrice='" + highestPrice + '\'' +
                ", lowestPrice='" + lowestPrice + '\'' +
                ", endPrice='" + endPrice + '\'' +
                ", upAndDown='" + upAndDown + '\'' +
                ", sharesTradedNum='" + sharesTradedNum + '\'' +
                ", securitiesEntity=" + securitiesEntity +
                '}';
    }
}
