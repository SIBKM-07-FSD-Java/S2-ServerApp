package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.User;
import com.sibkm.serverapp.model.AppUserDetail;
import com.sibkm.serverapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepository
      .findByUsernameOrEmployeeEmail(username, username)
      .orElseThrow(() ->
        new UsernameNotFoundException("Username or Email not found!!!")
      );

    return new AppUserDetail(user);
  }
}
