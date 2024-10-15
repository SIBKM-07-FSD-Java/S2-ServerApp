package com.sibkm.serverapp.service;

import com.sibkm.serverapp.entity.User;
import com.sibkm.serverapp.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getById(Integer Id) {
    return userRepository
      .findById(Id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!!")
      );
  }

  public User update(Integer id, User user) {
    getById(id);
    user.setId(id);
    return userRepository.save(user);
  }

  public User delete(Integer id) {
    User user = getById(id);
    userRepository.delete(user);
    return user;
  }
}
