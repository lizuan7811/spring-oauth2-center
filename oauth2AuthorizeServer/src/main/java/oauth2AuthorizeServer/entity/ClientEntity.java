package oauth2AuthorizeServer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oauthclientdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "CLIENTID", unique = true, nullable = false)
	private String clientId;

	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "RESOURCESIDS")
	private String resourceIds = "lizuan";
	
	@Column(name = "CLIENTSECRET", nullable = false)
	private String clientSecret;

	@Column(name = "SCOPE", nullable = false)
	private String scope;
	
	@Column(name = "AUTHORIZEDGRANTTYPES", nullable = false)
	private String authorizedGrantTypes = "password,refresh_token,authorization_code";

	@Column(name = "WEBSERVERREDIRECTURIS", nullable = false)
	private String registeredRedirectUris = "https://localhost:9999";

	@Column(name = "AUTHORITIES")
	private String authorities = Strings.EMPTY;

	@Column(name = "ACCESSTOKENVALIDITY")
	private int accessTokenValidity = 0;
	
	@Column(name = "REFRESHTOKENVALIDITY")
	
	private int refreshTokenValidity = 0;
	@Column(name = "ADDITIONALINFORMATION")
	
	private String additionalInformation = Strings.EMPTY;
	
	@Column(name = "AUTOAPPROVE")
	private String autoapprove = Strings.EMPTY;
	
}