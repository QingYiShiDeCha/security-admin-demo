package org.qingcha.security.common.constant;

public class JwtConstant {
    /**
     * token
     */
    public static final Integer JWT_ERRCODE_NULL = 400;   // token不存在
    public static final Integer JWT_ERRCODE_EXPIRE = 4001;   // token过期
    public static final Integer JWT_ERRCODE_FAIL = 4002;   // 验证不通过

    /**
     * JWT
     */
    public static final String JWT_SECERT = "1651agdsg1651scg154";   // 密钥
    public static final long JWT_TTL = 24 * 60 * 60 * 1000;

}
