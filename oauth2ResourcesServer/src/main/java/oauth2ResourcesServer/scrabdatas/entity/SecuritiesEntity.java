package oauth2ResourcesServer.scrabdatas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import oauth2ResourcesServer.scrabdatas.entity.pk.SecuritiesPk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: Entity的Field欄位均要大寫，否則對應到SQL SERVER對不到。
 * @author: Lizuan
 * @date: 2024/5/19
 * @time: 下午 04:03
 **/
@Entity
@Table(name = "Securities")
@Data
@IdClass(SecuritiesPk.class)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SecuritiesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQNO")
    private Integer seqNo;
    @Id
    @Column(name = "ISIN")
    private String ISIN;

    @Column(name = "SECURITYCODE")
    private String securityCode;

    @Column(name = "SECURITYNAME")
    private String securityName;

    @Column(name = "MARKETTYPE")
    private String marketType;

    @Column(name = "SECURITYTYPE")
    private String securityType;

    @Column(name = "INDUSTRYTYPE")
    private String industryType;

    @Column(name = "PUBLICOFFERINGDATE")
    private Date publicOfferingDate;

    @Column(name = "CFICode")
    private String CFICode;

    @Column(name = "REMARKS")
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "securitiesEntity")
    private Set<StockHistEntity> stockHistEntitySet = new HashSet();

}