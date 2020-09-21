package com.techmahindra.customers.configuration.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

@Component
public class TokenAuthenticationHelper {

    @Value("${application.token.expiration}")
    private long expiration;

    @Value("${application.token.secret}")
    private String secret;

    @Value("${application.token.header}")
    private String header;

    public void addAuthentication(HttpServletResponse response, String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        response.addHeader(header, jwt);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(header);

        if (token != null) {
            try {
                String user = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                return new UsernamePasswordAuthenticationToken(user, null, emptyList());
            } catch (ExpiredJwtException e) {
                return null;
            }
        }
        return null;
    }

    public String createToken(String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return jwt;
    }
}