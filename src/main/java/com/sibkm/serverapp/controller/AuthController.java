package com.sibkm.serverapp.controller;

import com.sibkm.serverapp.entity.Employee;
import com.sibkm.serverapp.entity.Role;
import com.sibkm.serverapp.model.request.LoginRequest;
import com.sibkm.serverapp.model.request.RegistrationRequest;
import com.sibkm.serverapp.model.response.LoginResponse;
import com.sibkm.serverapp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

  private AuthService authService;

  @PostMapping("/registration")
  public Employee registration(
    @RequestBody RegistrationRequest registrationRequest
  ) {
    return authService.registration(registrationRequest);
  }

  @PostMapping("/add-role/{idEmployee}")
  @PreAuthorize("hasRole('ADMIN')")
  public Employee addRole(
    @PathVariable Integer idEmployee,
    @RequestBody Role role
  ) {
    return authService.addRole(idEmployee, role);
  }

  @PostMapping("/auth/login")
  public LoginResponse login(
    @RequestBody LoginRequest loginRequest,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    return authService.login(loginRequest, request, response);
  }
}
