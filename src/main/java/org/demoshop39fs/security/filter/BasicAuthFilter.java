package org.demoshop39fs.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;


public class BasicAuthFilter extends BasicAuthenticationFilter {


    public BasicAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    private Authentication extractAuthentication(String header) {
        String base64Credentials = header.substring("Basic ".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded);

        // credentials should contain "username:password"

        final String[] values = credentials.split(":", 2);

        if (values.length != 2) {
            throw new IllegalArgumentException("Failed to parse Basic Authentication token");
        }

        String username = values[0];
        String password = values[1];

        return new UsernamePasswordAuthenticationToken(username,password,null);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Basic ")) {
            try {
                Authentication authentication = getAuthenticationManager().authenticate(extractAuthentication(header));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
