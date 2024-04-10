package oauth2AuthorizeServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name="USERROLE")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;

//	使用者ID
	@Column(name="UID")
	private Integer uid;
//	ROLE的ID
	@Column(name="RID")
	private Integer rid;
}
