package com.pdx.utils;

import com.pdx.constant.Constant;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
@Slf4j
public class JwtTokenUtil {

    private static String secretKey = "low-code-for-secret";
    private static long accessTokenExpireTime = 864000000;
    private static String issuer = "low-code-by-pdx";

    /**
     * 生成 access_token
     * @param subject
     * @param claims
     * @return
     */
    public static String getAccessToken(String subject, Map<String,Object> claims){
        return generateToken(issuer,subject,claims,accessTokenExpireTime,secretKey);
    }

    /**
     * 签发Token
     * @param issuer 签发人
     * @param subject 代表Jwt的主体，及它的所有人，一般都是用户Id
     * @param claims 存储Jwt里面的信息，一般放些用户的权限/角色信息
     * @param ttlMillis 有效时间
     * @param secret
     * @return
     */
    public static String generateToken(String issuer, String subject, Map<String, Object> claims, long ttlMillis, String secret) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);

        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ","JWT");
        if(null!=claims){
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        builder.setIssuedAt(now);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        builder.signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    /**
     * 从令牌中获取数据声明
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims=null;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            if(e instanceof ClaimJwtException){
                claims=((ClaimJwtException) e).getClaims();
            }
        }
        return claims;
    }

    /**
     * 获取用户id
     * @param token
     * @return
     */
    public static String getUserId(String token){
        String userId=null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            log.error("eror={}",e);
        }
        return userId;
    }

    /**
     * 验证Token是否过期
     * @param token
     * @return
     */
    public static Boolean isTokenExpired(String token) {

        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("error={}",e);
            return true;
        }
    }

    /**
     * 校验令牌
     * @param token
     * @return
     */
    public static Boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return (null!=claimsFromToken && !isTokenExpired(token));
    }

    /**
     * 获取Token的剩余过期时间
     * @param token
     * @return
     */
    public static long getRemainingTime(String token){
        long result=0;
        try {
            long nowMillis = System.currentTimeMillis();
            result= getClaimsFromToken(token).getExpiration().getTime()-nowMillis;
        } catch (Exception e) {
            log.error("error={}",e);
        }
        return result;
    }
}
