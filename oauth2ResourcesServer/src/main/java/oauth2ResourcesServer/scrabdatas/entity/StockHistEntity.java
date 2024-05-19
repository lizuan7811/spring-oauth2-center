package oauth2ResourcesServer.scrabdatas.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityPk;

@SuppressWarnings("serial")
@Entity
@Table(name = "STOCK_HIST_DATA")
@Data
@IdClass(StockHistEntityPk.class)
public class StockHistEntity implements Serializable {

    @Id
    @Column(name = "STOCKCODE")
    private String stockCode;
    @Id
    @Column(name = "DATET")
    private String date;
    @Column(name = "TRANSACTVOLUME")
    private String transactVolume;
    @Column(name = "TOTALPRICE")
    private String totalPrice;
    @Column(name = "STARTPRICE")
    private String startPrice;
    @Column(name = "HIGHESTPRICE")
    private String highestPrice;
    @Column(name = "LOWESTPRICE")
    private String lowestPrice;
    @Column(name = "ENDPRICE")
    private String endPrice;
    @Column(name = "UPANDDOWN")
    private String upAndDown;
    @Column(name = "SHARESTRADENUM")
    private String sharesTradedNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockcode", referencedColumnName = "securityCode", insertable = false, updatable = false)
    private SecuritiesEntity securitiesEntity;
}
