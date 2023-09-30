package ru.skillbox.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.users.entity.User;
import ru.skillbox.users.repository.UserRepository;
import ru.skillbox.users.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    List<User> getUsers() {
        return userService.loadAllUsers();
    }

}
