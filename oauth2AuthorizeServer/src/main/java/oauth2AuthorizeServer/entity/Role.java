package oauth2AuthorizeServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "NAME_ZH", columnDefinition = "NVARCHAR(32)")
	private String name_zh;
}
