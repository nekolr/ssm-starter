package com.nekolr.util;

import com.nekolr.vo.JwtAccount;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * JWT 工具
 *
 * @author nekolr
 */
public class JwtUtils {

    /**
     * HS512 需要的私钥
     */
    private static final String SECRET_KEY = ":xgj%eMd#gk+wh.`t2;XW!.dIuC&$#Lua+;%~!F=" +
            "G&16Eo7b3o|GudHr%:?ijHQ3=G&:hVFcnQV?57f*)p!wNMG*Sfz%:pSU~5n,J|G%ro1blr'*'yD&z@Y&1Aa|=Bu:k";

    private JwtUtils() {

    }

    /**
     * 签发令牌
     *
     * @param tokenId     令牌 ID
     * @param subject     被签发者
     * @param issuer      签发者
     * @param period      有效时间
     * @param roles       角色
     * @param permissions 权限（资源）
     * @param algorithm   加密算法
     * @return
     */
    public static String issueJwt(String tokenId, String subject, String issuer, Long period, String roles, String permissions, SignatureAlgorithm algorithm) {
        // 当前时间戳
        Long currentTimeMillis = System.currentTimeMillis();

        // 私钥
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        // JWT builder
        JwtBuilder jwtBuilder = Jwts.builder();

        /**
         * Reserved claims
         */

        // 设置 id payload
        if (!StringUtils.isEmpty(tokenId)) {
            jwtBuilder.setId(tokenId);
        }

        // 设置被签发者 payload
        if (!StringUtils.isEmpty(subject)) {
            jwtBuilder.setSubject(subject);
        }

        // 设置签发者 payload
        if (!StringUtils.isEmpty(issuer)) {
            jwtBuilder.setIssuer(issuer);
        }

        // 设置签发时间 payload
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));

        // 设置到期时间 payload
        if (period != null) {
            jwtBuilder.setExpiration(new Date(currentTimeMillis + period * 1000));
        }

        /**
         * Private claims
         */

        // 角色 payload
        if (!StringUtils.isEmpty(roles)) {
            jwtBuilder.claim("roles", roles);
        }

        // 权限 payload
        if (!StringUtils.isEmpty(permissions)) {
            jwtBuilder.claim("perms", permissions);
        }

        // 压缩方式，此处采用 deflate
        jwtBuilder.compressWith(CompressionCodecs.DEFLATE);

        // 采用算法加密私钥
        SecretKey key = new SecretKeySpec(secretKeyBytes, algorithm.getJcaName());

        // 签发
        jwtBuilder.signWith(key, algorithm);

        // 生成
        String jwt = jwtBuilder.compact();

        return jwt;
    }

    /**
     * 解析 JWT
     *
     * @param jwt       令牌
     * @param algorithm 加密算法
     * @param secretKey 密钥
     * @return
     */
    public static JwtAccount parseJwt(String jwt, SignatureAlgorithm algorithm, String secretKey) {
        // 私钥
        SecretKey key = new SecretKeySpec(DatatypeConverter.parseBase64Binary(secretKey), algorithm.getJcaName());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        JwtAccount jwtAccount = new JwtAccount();
        jwtAccount.setTokenId(claims.getId());
        jwtAccount.setAppId(claims.getSubject());
        jwtAccount.setIssuer(claims.getIssuer());
        jwtAccount.setIssuedAt(claims.getIssuedAt());
        jwtAccount.setAudience(claims.getAudience());
        jwtAccount.setRoles((String) claims.get("roles"));
        jwtAccount.setPerms((String) claims.get("perms"));
        jwtAccount.setHost((String) claims.get("host"));
        return jwtAccount;
    }
}
