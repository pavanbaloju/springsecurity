package com.pavanbaloju.springsecurity.bank.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.pavanbaloju.springsecurity.bank.constants.SecurityConstants.JWT_HEADER;
import static com.pavanbaloju.springsecurity.bank.constants.SecurityConstants.JWT_KEY;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = getJwt(secretKey, authentication.getName(), getAuthoritiesString(authentication.getAuthorities()));
            response.setHeader(JWT_HEADER, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean isLoginEndpoint = request.getServletPath().equals("/user");
        return !isLoginEndpoint;
    }

    private String getJwt(SecretKey key, String username, String authorities) {
        return Jwts.builder().setIssuer("Bank").setSubject("JWT Token")
            .claim("username", username)
            .claim("authorities", authorities)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + 30000000))
            .signWith(key).compact();
    }

    private String getAuthoritiesString(Collection<? extends GrantedAuthority> collection) {
        List<String> strings = collection.stream().map(GrantedAuthority::getAuthority).toList();
        return String.join(",", strings);
    }

}
