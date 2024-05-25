package oauth2ResourcesServer.scrabdatas.entity;

import lombok.Data;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityNoRelativePk;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "STOCK_HIST_DATA")
@Data
public class StockHistNoRealtiveEntity implements Serializable {

    @EmbeddedId
    StockHistEntityNoRelativePk stockHistEntityNoRelativePk;
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

}
