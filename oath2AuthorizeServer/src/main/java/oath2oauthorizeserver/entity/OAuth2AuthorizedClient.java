package oath2oauthorizeserver.entity;

import lombok.Data;
import oath2oauthorizeserver.entity.pk.OAuth2AuthorizedClientPk;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="oauth2_authorized_client")
@IdClass(value= OAuth2AuthorizedClientPk.class)
public class OAuth2AuthorizedClient implements Serializable {

    @Id
    @Column(name="clientRegistrationId")
    private String clientRegistrationId;

    @Id
    @Column(name="PRINCIPALNAME")
    private String principalName;
    @Column(name="ACCESSTOKENTYPE")
    private String accessTokenType;
    @Column(name="ACCESSTOKENVALUE")
    private String accessTokenValue;
    @Column(name="ACCESSTOKENISSUEDAT")
    private Timestamp accessTokenIssuedAt;
    @Column(name="ACCESSTOKENEXPIRESAT")
    private Timestamp accessTokenExpiresAt;
    @Column(name="ACCESSTOKENSCOPES")
    private String accessTokenScopes;
    @Column(name="ACCESSTOKENMETADATA")
    private byte[] accessTokenMetadata;
    @Column(name="REFRESHTOKENVALUE")
    private String refreshTokenValue;
    @Column(name="REFRESHTOKENISSUEDAT")
    private Timestamp refreshTokenIssuedAt;
}