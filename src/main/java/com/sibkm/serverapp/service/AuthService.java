package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.Employee;
import com.sibkm.serverapp.entity.Role;
import com.sibkm.serverapp.entity.User;
import com.sibkm.serverapp.model.request.LoginRequest;
import com.sibkm.serverapp.model.request.RegistrationRequest;
import com.sibkm.serverapp.model.response.LoginResponse;
import com.sibkm.serverapp.repository.EmployeeRepository;
import com.sibkm.serverapp.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private EmployeeRepository employeeRepository;
  private EmployeeService employeeService;
  private RoleService roleService;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authManager;
  private AppUserDetailService appUserDetailService;

  public Employee registration(RegistrationRequest registrationRequest) {
    // ? setup data employee & user
    Employee employee = new Employee();
    User user = new User();

    BeanUtils.copyProperties(registrationRequest, employee);
    BeanUtils.copyProperties(registrationRequest, user);

    // set password
    user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

    // add role
    List<Role> roles = Collections.singletonList(roleService.getById(1));
    user.setRoles(roles);

    user.setEmployee(employee);
    employee.setUser(user);

    return employeeRepository.save(employee);
  }

  public Employee addRole(Integer idEmployee, Role role) {
    // todo: cek employee -> ada / tidak?
    Employee employee = employeeService.getById(idEmployee);
    User user = employee.getUser();

    // todo: cek role -> ada / tidak?
    List<Role> roles = user.getRoles();
    roles.add(roleService.getById(role.getId()));

    // todo: set role -> user
    user.setRoles(roles);
    userRepository.save(user);
    return employee;
  }

  public LoginResponse login(LoginRequest loginRequest) {
    // todo: authentication
    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
      loginRequest.getUsername(),
      loginRequest.getPassword()
    );

    // todo: set principle
    Authentication auth = authManager.authenticate(authReq);
    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(auth);

    // ? get username for login response
    UserDetails userDetails = appUserDetailService.loadUserByUsername(
      loginRequest.getUsername()
    );

    // ? get email for login response
    User user = userRepository
      .findByUsernameOrEmployeeEmail(
        loginRequest.getUsername(),
        loginRequest.getUsername()
      )
      .get();

    // ? get authorities for login response
    List<String> authorities = userDetails
      .getAuthorities()
      .stream()
      .map(authority -> authority.getAuthority())
      .collect(Collectors.toList());

    // todo: set response
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setUsername(userDetails.getUsername());
    loginResponse.setEmail(user.getEmployee().getEmail());
    loginResponse.setAuthorities(authorities);

    return loginResponse;
  }
}
