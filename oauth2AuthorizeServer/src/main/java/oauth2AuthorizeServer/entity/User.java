package oath2resourceserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="USERTB")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="USERNAME")
	private String username;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="ENABLED")
	private Boolean enabled;
	@Column(name="ACCOUNTNONEXPIRED")
	private Boolean accountNonExpired;
	@Column(name="ACCOUNTNONLOCKED")
	private Boolean accountNonLocked;
	@Column(name="CREDENTIALSNONEXPIRED")
	private Boolean credentialsNonExpired;
}
