package com.sibkm.serverapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/region/**")
          .permitAll()
          .requestMatchers("/country/**")
          .hasAnyRole("ADMIN", "USER")
          .requestMatchers("/role/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .userDetailsService(userDetailsService())
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User
      .builder()
      .username("user")
      .password(passwordEncoder().encode("user"))
      .roles("USER")
      .build();

    UserDetails admin = User
      .builder()
      .username("admin")
      .password(passwordEncoder().encode("admin"))
      .roles("ADMIN")
      .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance(); // admin
  }
}
