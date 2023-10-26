package ru.skillbox.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.users.entity.User;
import ru.skillbox.users.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping("/all")
    List<User> getUsers() {
        return userService.loadAllUsers();
    }

    @Operation(summary = "Получение пользователя по его id")
    @GetMapping("/{id}")
    User getUser(@PathVariable long id) {
        return userService.loadUser(id);
    }

    @Operation(summary = "Сохранение пользователя")
    @PostMapping
    void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @Operation(summary = "Обновление пользователя")
    @PutMapping("/{id}")
    void updateUser(@RequestBody User user, @PathVariable long id) {
        userService.updateUser(user, id);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

}
