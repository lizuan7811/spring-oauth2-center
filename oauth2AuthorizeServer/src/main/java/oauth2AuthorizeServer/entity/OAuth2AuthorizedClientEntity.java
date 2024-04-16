package oauth2AuthorizeServer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "oauth2_authorized_client")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2AuthorizedClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CLIENTREGISTRATIONID")
    private String clientRegistrationId;

    @Column(name = "PRINCIPALNAME")
    private String principalName;

    @Column(name = "ACCESSTOKENTYPE")
    private String accessTokenType;

    @Column(name = "ACCESSTOKENVALUE")
    private String accessTokenValue;

    @Column(name = "ACCESSTOKENISSUEDAT")
    private LocalDateTime accessTokenIssuedAt;

    @Column(name = "ACCESSTOKENEXPIRESAT")
    private LocalDateTime accessTokenExpiresAt;

    @Column(name = "ACCESSTOKENSCOPES")
    private String accessTokenScopes;

    @Lob
    @Column(name = "ACCESSTOKENMETADATA")
    private byte[] accessTokenMetadata;

    @Column(name = "REFRESHTOKENVALUE")
    private String refreshTokenValue;

    @Column(name = "REFRESHTOKENISSUEDAT")
    private LocalDateTime refreshTokenIssuedAt;

    // Getters and setters
}
