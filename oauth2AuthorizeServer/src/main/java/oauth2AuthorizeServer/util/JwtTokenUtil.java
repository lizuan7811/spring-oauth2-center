package oauth2AuthorizeServer.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

/**
 * @description: JwtTokenUtil
 * @author: Lizuan
 * @date: 2023/11/26
 * @time: 上午 11:15
 **/
public class JwtTokenUtil {

    private static final long EXPIRATION_TIME = 86400000;

    private static final RSAPrivateKey privateKey;

    private static final RSAPublicKey publicKey;

    private static final SecureRandom secureRandom;

    private static final String SINGLEIDENTIFIER_UUID="SINGLEIDENTIFIER_UUID";
    static {
        try {
            KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = (RSAPublicKey) keyPair.getPrivate();
            secureRandom=SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * description: 產生token
     * author: Lizuan
     * date: 2023/11/26
     * time: 12:52:48
     **/
    public static String generateToken(UserDetails userDetails) throws JOSEException {
        UUID uuid= new UUID(secureRandom.nextLong(),secureRandom.nextLong());
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("kid").build();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().claim(SINGLEIDENTIFIER_UUID,new UUID(secureRandom.nextLong(),secureRandom.nextLong()).randomUUID()).subject(userDetails.getUsername()).expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        JWSSigner signer = new RSASSASigner(privateKey);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    /**
     * description: 驗證token是否合法
     * author: Lizuan
     * date: 2023/11/26
     * time: 12:52:36
     **/
    public static boolean validateIdentifierToken(UserDetails userDetails, String inputToken) {
        String result;
        boolean validResult = false;
        String uname = userDetails.getUsername();
        try {
            JWSObject jwsObject = JWSObject.parse(inputToken);
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            if (!jwsObject.verify(verifier)) {
                throw new BadJWTException("Signature verification failed");
            }
            JWTClaimsSet claimsSet = jwsObject.getPayload().toSignedJWT().getJWTClaimsSet();
            result = claimsSet.getSubject();
            boolean isExpired = isTokenExpired(claimsSet);
            boolean sameIdentifierUid= validateIdentifierUid(uname,inputToken);
            if (!isExpired && result.equals(uname) && sameIdentifierUid) {
                validResult = true;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (BadJWTException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return validResult;
    }

    /**
     * description: 驗證是否過期
     * author: Lizuan
     * date: 2023/11/26
     * time: 12:52:09
     **/
    private static boolean isTokenExpired(JWTClaimsSet claimsSet) {
        Date expiration = claimsSet.getExpirationTime();
        return expiration.before(new Date());
    }

//    /**
//     * description: 將產生的session-token存入redis
//     * author: Lizuan
//     * date: 2023/11/26
//     * time: 12:55:34
//     **/
//    public static void setSingleIdentifierToRedis(String uname, String token) {
//        String tokenKey = "jwttoken:" + uname;
//        RedisConnBuilder.setUserIdentifier(tokenKey, token);
//    }
//
//    public static void delSingleIdentifierToRedis(String uname) {
//        String tokenKey = "jwttoken:" + uname;
//        RedisConnBuilder.delIdentifier(tokenKey);
//    }
  /**
    * description: 不存在，代表先前沒有登入過，第一次可以存入Redis，
    * 存在代表前面登入過，若相同就不需要logout再重新登入。
    * author: Lizuan
    * date: 2023/11/26
    * time: 15:17:20
    **/
    private static boolean validateIdentifierUid(String uname,String inputToken) throws ParseException {
        String tokenKey = "jwttoken:" + uname;
        String singleIdentifierUUID= Strings.EMPTY;
        boolean valide=false;
        if(StringUtils.isNotBlank(inputToken)){
//            String preSavedToken=RedisConnBuilder.getUserIdentifier(tokenKey);
            JWSObject jwsObject = JWSObject.parse(inputToken);
            JWTClaimsSet claimsSet = jwsObject.getPayload().toSignedJWT().getJWTClaimsSet();
            singleIdentifierUUID=claimsSet.getClaim(SINGLEIDENTIFIER_UUID).toString();
            valide=inputToken.equals(singleIdentifierUUID);
        }else{
            valide=true;
        }
        return valide;
    }

}
