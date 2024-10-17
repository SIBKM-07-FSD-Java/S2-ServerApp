package com.sibkm.serverapp.controller;

import com.sibkm.serverapp.entity.Employee;
import com.sibkm.serverapp.entity.Role;
import com.sibkm.serverapp.model.request.RegistrationRequest;
import com.sibkm.serverapp.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping
public class AuthController {

  private AuthService authService;

  @PostMapping("/registration")
  public Employee registration(
    @RequestBody RegistrationRequest registrationRequest
  ) {
    return authService.registration(registrationRequest);
  }

  @PostMapping("/add-role/{idEmployee}")
  public Employee addRole(
    @PathVariable Integer idEmployee,
    @RequestBody Role role
  ) {
    return authService.addRole(idEmployee, role);
  }
}
