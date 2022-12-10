package hospital.web.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    private static long expireTimeMs = 1000 * 60 * 60; //1시간
    public static String createToken(String userAccount, String key) {
        Claims claims = Jwts.claims();
        claims.put("userAccount", userAccount);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))//현재시간
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))//현재 시간 +종료 시간
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
