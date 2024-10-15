package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.Employee;
import com.sibkm.serverapp.repository.EmployeeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EmployeeService {

  private EmployeeRepository employeeRepository;

  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  public Employee getById(Integer Id) {
    return employeeRepository
      .findById(Id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Employee not found!!!"
        )
      );
  }

  public Employee update(Integer id, Employee employee) {
    getById(id);
    employee.setId(id);
    return employeeRepository.save(employee);
  }

  public Employee delete(Integer id) {
    Employee employee = getById(id);
    employeeRepository.delete(employee);
    return employee;
  }
}
