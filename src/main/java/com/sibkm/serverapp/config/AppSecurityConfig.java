package com.sibkm.serverapp.config;

import com.sibkm.serverapp.service.AppUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
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
          .requestMatchers(HttpMethod.POST, "/login")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .userDetailsService(appUserDetailService)
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authConfig
  ) throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
