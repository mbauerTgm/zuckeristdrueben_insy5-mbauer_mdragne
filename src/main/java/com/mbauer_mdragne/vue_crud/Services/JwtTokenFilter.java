package com.mbauer_mdragne.vue_crud.Services;

import com.mbauer_mdragne.vue_crud.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtTokenFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        
        // 1. Suche das "jwt" Cookie
        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(c -> "jwt".equals(c.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        // 2. Token validieren
        if (token != null) {
            try {
                Jws<Claims> claims = jwtUtil.validateToken(token);
                String userId = claims.getBody().getSubject();
                
                // Rollen aus dem Token extrahieren
                List<String> roles = (List<String>) claims.getBody().get("roles");
                
                // In Spring-Format umwandeln (SimpleGrantedAuthority)
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Wichtig: Prefix ROLE_
                        .collect(Collectors.toList());

                // 3. User im System "einloggen" für diesen Request
                UsernamePasswordAuthenticationToken auth = 
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
                
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // Token ungültig -> SecurityContext bleibt leer -> User fliegt raus
                System.out.println("JWT Invalid: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}