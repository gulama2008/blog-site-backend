package com.siyu.blogsitebackend.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.siyu.blogsitebackend.user.User;
import com.siyu.blogsitebackend.user.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

@Autowired
private JwtService jwtService;

@Autowired
private UserService userService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);
        Long userId = this.jwtService.extractUserId(jwtToken);
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = this.userService.getById(userId);
            if (user.isPresent()) {
                User found = user.get();
                System.out.println(this.jwtService.isTokenValid(jwtToken, found)+"================================");
                if (this.jwtService.isTokenValid(jwtToken, found)) {
                    UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(found,
                            null, found.getAuthorities());
                    userPassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(userPassToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
