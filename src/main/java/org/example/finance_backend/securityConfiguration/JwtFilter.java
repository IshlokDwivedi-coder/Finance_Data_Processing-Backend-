package org.example.finance_backend.securityConfiguration;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final jwtUtil jwtUtil;

    public JwtFilter(jwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // 2. Check if it exists and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " to get just the token

            try {
                // 3. Extract data from the token
                Claims claims = jwtUtil.extractAllClaims(token);
                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                // 4. Tell Spring Security "This user is valid and has this role"
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singletonList(new SimpleGrantedAuthority(role))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // EDGE CASE: If token is expired or fake, stop here and return 401!
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or Expired Token");
                return; // Stop the request completely
            }
        }

        // 5. Let the request continue to the Controller
        filterChain.doFilter(request, response);
    }
}
