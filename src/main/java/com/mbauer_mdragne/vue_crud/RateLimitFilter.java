package com.mbauer_mdragne.vue_crud;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    // Speichert die Buckets pro IP. Wir hängen ein Suffix an, um Standard und Auth zu trennen.
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // Standard-Limit: 60 Anfragen pro Minute
    private Bucket createStandardBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(40)
                .refillGreedy(40, Duration.ofMinutes(1))
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    // Strenges Login-Limit: 10 Anfragen pro Minute
    private Bucket createAuthBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(10)
                .refillGreedy(10, Duration.ofMinutes(1))
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String ip = httpRequest.getRemoteAddr();
        String path = httpRequest.getRequestURI();

        Bucket bucket;
        
        // Prüfen, ob die Anfrage an deinen offenen Auth-Bereich geht
        if (path.startsWith("/api/auth")) {
            // Verbindet die IP mit dem Auth-Eimer (z.B. "127.0.0.1-auth")
            bucket = buckets.computeIfAbsent(ip + "-auth", k -> createAuthBucket());
        } else {
            // Verbindet die IP mit dem Standard-Eimer (z.B. "127.0.0.1-standard")
            bucket = buckets.computeIfAbsent(ip + "-standard", k -> createStandardBucket());
        }

        // Token verbrauchen
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            // Limit überschritten!
            httpResponse.setStatus(429); // 429 Too Many Requests
            httpResponse.setContentType("text/plain;charset=UTF-8");
            
            if (path.startsWith("/api/auth")) {
                httpResponse.getWriter().write("Zu viele Login-Versuche. Bitte warte eine Minute.");
            } else {
                httpResponse.getWriter().write("Zu viele Anfragen. Bitte warte einen Moment.");
            }
        }
    }
}