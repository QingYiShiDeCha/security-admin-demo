package org.qingcha.security.common.utils;


import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;
import org.qingcha.security.common.constant.JwtConstant;
import org.qingcha.security.common.result.CheckResult;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtils {

    /**
     * 签发JWT
     * @param id id
     * @param subject 可以是json数据， 尽可能少
     * @param ttlMillis 时间
     * @return token
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("QingCha")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expMills = currentTimeMillis + ttlMillis;
            Date expDate = new Date(expMills);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    public static String genrateJwtToken(Long userId, String username) throws Base64DecodingException {
        return createJWT(String.valueOf(userId), username, 60 * 60 * 1000);
    }


    /**
     * 验证token是否有效
     *
     * @param jwt token
     * @return CheckResult
     */
    public static CheckResult validateJWT(String jwt)  {
        CheckResult checkResult = new CheckResult();
        Claims claims;
        try {
            claims = parseJwt(jwt);
            checkResult.setSuccess(true);
            checkResult.setClaim(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    private static SecretKey generalKey()  {
        byte[] decode = Base64.decode(JwtConstant.JWT_SECERT);
        return new SecretKeySpec(decode, 0, decode.length, "AES");
    }

    public static Claims parseJwt(String token) throws Base64DecodingException {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}
