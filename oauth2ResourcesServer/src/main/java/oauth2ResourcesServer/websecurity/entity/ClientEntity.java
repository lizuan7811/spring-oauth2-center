package oauth2ResourcesServer.websecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private String registeredRedirectUris = "https://www.google.com.tw";

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