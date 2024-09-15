package org.qingcha.security.common.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {

    /**
     * 签发JWT
     * @param id id
     * @param suject 可以是json数据， 尽可能少
     * @param ttlMillis 时间
     * @return token
     */
    public static String createJWT(String id, String suject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(suject)
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

    public static String genJwtToken(String username) {
        return createJWT(username, username, 60 * 60 * 1000);
    }


    private static SecretKey generalKey() {
        Base64.de
    }
}
