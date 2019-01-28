package com.nekolr.admin.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nekolr.admin.common.vo.JwtAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Strings;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具
 *
 * @author nekolr
 */
public class JwtUtils {

    /**
     * HS512 需要的私钥
     */
    public static final String SECRET_KEY = ":xgj%eMd#gk+wh.`t2;XW!.dIuC&$#Lua+;%~!F=" +
            "G&16Eo7b3o|GudHr%:?ijHQ3=G&:hVFcnQV?57f*)p!wNMG*Sfz%:pSU~5n,J|G%ro1blr'*'yD&z@Y&1Aa|=Bu:k";
    /**
     * Jackson 对象映射
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 压缩处理器
     */
    private static CompressionCodecResolver codecResolver = new DefaultCompressionCodecResolver();

    private JwtUtils() {

    }

    /**
     * 签发令牌
     *
     * @param tokenId     令牌 ID
     * @param subject     被签发者
     * @param issuer      签发者
     * @param period      有效时间，单位秒
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
        return jwtAccount;
    }

    /**
     * 解析 JWT 的 Payload
     *
     * @param jwt
     * @return
     */
    public static String parsePayload(String jwt) {
        if (jwt == null) {
            throw new IllegalArgumentException("JWT string argument cannot be null or empty");
        }
        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedSignature = null;
        int delimiterCount = 0;
        StringBuilder builder = new StringBuilder();
        char[] jwtCharArray = jwt.toCharArray();
        for (char c : jwtCharArray) {
            if (c == '.') {
                // 去除头部和尾部的空白符
                CharSequence sequence = Strings.clean(builder);
                String token = sequence != null ? sequence.toString() : null;
                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }
                delimiterCount++;
                builder.setLength(0);
            } else {
                builder.append(c);
            }
        }
        // 正常情况下此时 delimiterCount 应该是 2
        if (delimiterCount != 2) {
            throw new MalformedJwtException("JWT string must contain exactly 2 period characters. Found: " + delimiterCount);
        }

        // 剩下的就是 Signature
        if (builder.length() > 0) {
            base64UrlEncodedSignature = builder.toString();
        }

        // 没有 Payload 则抛异常
        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload");
        }

        // 解析 Header
        Header header;
        CompressionCodec compressionCodec = null;
        if (base64UrlEncodedHeader != null) {
            String originalValue = new String(Decoders.BASE64URL.decode(base64UrlEncodedHeader), Charset.forName("UTF-8"));
            Map<String, Object> map = readValue(originalValue);
            if (base64UrlEncodedSignature != null) {
                header = new DefaultJwsHeader(map);
            } else {
                header = new DefaultHeader(map);
            }
            // 解析压缩方式
            compressionCodec = codecResolver.resolveCompressionCodec(header);
        }

        // 解析 Payload
        String payload;
        if (compressionCodec != null) {
            // 解压缩
            byte[] decompressed = compressionCodec.decompress(Decoders.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, Charset.forName("UTF-8"));
        } else {
            payload = new String(Decoders.BASE64URL.decode(base64UrlEncodedPayload), Charset.forName("UTF-8"));
        }

        return payload;
    }

    /**
     * JSON 字符串转 java.util.Map
     *
     * @param jsonText
     * @return
     */
    public static Map<String, Object> readValue(String jsonText) {
        try {
            return OBJECT_MAPPER.readValue(jsonText, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + jsonText, e);
        }
    }
}
