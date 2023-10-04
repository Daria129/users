package ru.skillbox.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.users.entity.User;
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

    @GetMapping("/{id}")
    User getUser(@PathVariable long id) {
        return userService.loadUser(id);
    }

    @PostMapping
    void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    void updateUser(@RequestBody User user, @PathVariable long id) {
        userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

}
