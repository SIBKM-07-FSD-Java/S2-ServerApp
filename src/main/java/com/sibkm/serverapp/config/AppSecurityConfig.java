package com.sibkm.serverapp.config;

import com.sibkm.serverapp.service.AppUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class AppSecurityConfig {

  private AppUserDetailService appUserDetailService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .cors(cors -> cors.disable())
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers(HttpMethod.POST, "/registration")
          .permitAll()
          .requestMatchers("/country/**")
          .hasAnyRole("ADMIN", "USER")
          .requestMatchers("/role/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .userDetailsService(appUserDetailService)
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}
