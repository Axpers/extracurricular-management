package api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.models.User;
import api.services.FakeDatabaseService;

@RestController
@RequestMapping("/users")
public class UserController {

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping
  public User[] getUsers() {
    return FakeDatabaseService.getUsers();
  }

}