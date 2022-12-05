package hospital.web.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    public static String getUserId(Claims claims) {
        return claims.get("userId").toString();
    }
    public static String createToken(String userId, String key, long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))//현재시간
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))//현재 시간 +종료 시간
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
