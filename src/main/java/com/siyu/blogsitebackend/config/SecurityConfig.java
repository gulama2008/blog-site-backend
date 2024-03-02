package com.siyu.blogsitebackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.siyu.blogsitebackend.jwt.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomAuthExceptionHandler customAuthExceptionHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            // if there is a request to  "/auth/register" the user doesn't need to be authenticated
			http
            .csrf(CsrfConfigurer::disable)
            .exceptionHandling((exception) -> exception.authenticationEntryPoint(customAuthExceptionHandler))
            .authorizeHttpRequests((requests) -> requests
            // .requestMatchers("/articles/**").permitAll()
            .requestMatchers("/auth/register").permitAll()
            .requestMatchers("/auth/login").permitAll()
            // .requestMatchers("/articles/**").permitAll()
            // .requestMatchers("/auth/register").permitAll()
            .anyRequest().authenticated())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
			return http.build();
		}
    
}
