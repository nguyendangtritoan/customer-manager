package com.example.demo.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.example.demo.user.Permission.*;
import static com.example.demo.user.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
              auth.requestMatchers(
                  "/api/v1/auth/**",
                  "/v2/api-docs",
                  "/v3/api-docs",
                  "/v3/api-docs/**",
                  "/swagger-resources",
                  "/swagger-resources/**",
                  "/configuration/ui",
                  "/configuration/security",
                  "/swagger-ui/**",
                  "/webjars/**",
                  "/swagger-ui.html"
              ).permitAll();
              auth.requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name());
              auth.requestMatchers(GET, "/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name());
              auth.requestMatchers(POST, "/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name());
              auth.requestMatchers(PUT, "/api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name());
              auth.requestMatchers(DELETE, "/api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name());
              auth.anyRequest().authenticated();
            }
        )
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        );

    return http.build();
  }
}