package hospital.web.configuration;

import hospital.web.domain.entity.User;
import hospital.web.service.UserService;
import hospital.web.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    // OncePerRequestFilter 를 상속받아 메서드를 Override 해서 필터를 구현한다.
    //요청할때마다 토큰을 보여줘야 하는 방식

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //HttpServletRequest에서 Header 의 AUTHORIZATION 을 추출한다.
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token;
        try {
            token = authorization.split(" ")[1];
        } catch (Exception e) {
            log.error("token 추출에 실패했습니다.");
            filterChain.doFilter(request, response);
            return;
        }
        // [Bearer sadfadfsaf...토큰] 이런식으로 값이 나오므로 split 후 index 1
        // Jwt 는 OAuth 방식이라서 Bearer 을 붙여서 보낸다.(일종의 식별자)
        // 추출 실패 시 예외처리

        if (JwtTokenFilter.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }
        String userId = JwtTokenUtil.getUserId(extractClaims(token,secretKey));
        User user = userService.getUserByUserId(userId);

        // 권한을 줄지 안줄지 결정하는 메서드
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserId(), null, List.of(new SimpleGrantedAuthority(user.getUserRole().name())));

        //"USER" 라는 권한을 부여,
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // SecurityContext 에 authenticationToken 정보를 등록한다.
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        log.info("expiredDate {} new Date() {} expiredDate.before(new Date()) {}",expiredDate,new Date(),expiredDate.before(new Date()));
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }


}
