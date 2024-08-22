package com.app.eventorganizer.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.eventorganizer.security.CustomUserDetailsService;
import com.app.eventorganizer.security.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

          
            if (jwt != null) {
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwt);
                } catch (IllegalArgumentException e) {
                    logger.error("Unable to get JWT Token", e);
                } catch (ExpiredJwtException e) {
                    logger.error("JWT Token has expired", e);
                }
            }
        }

        // Check if username is still null
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            // Check if token is valid before proceeding
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
            	 List<String> roles = jwtTokenUtil.getRolesFromToken(jwt);

//                 // Convert roles to SimpleGrantedAuthority
//                 List<SimpleGrantedAuthority> authorities = roles.stream()
//                         .map(SimpleGrantedAuthority::new)
//                         .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                logger.warn("JWT validation failed for user: " + username);
            }
        } else {
            logger.warn("Username is null or the context already contains an authentication object.");
        }

        chain.doFilter(request, response);
    }

   
}
