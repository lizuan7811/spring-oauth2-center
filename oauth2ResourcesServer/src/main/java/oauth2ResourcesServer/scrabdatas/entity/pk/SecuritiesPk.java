package oauth2ResourcesServer.scrabdatas.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @description: TODO
 * @author: Lizuan
 * @date: 2024/5/19
 * @time: 下午 04:05
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class SecuritiesPk implements Serializable {

    private Integer seqNo;

    private String ISIN;
}
