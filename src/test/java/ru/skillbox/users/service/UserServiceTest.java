package ru.skillbox.users.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.users.entity.User;
import ru.skillbox.users.repository.UserRepository;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @Before
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void loadAllUsers() {
        User user1 = new User(1L, "user25", "user25@mail.ru");
        User user2 = new User(2L, "cooluser", "cooluser@gmail.com");
        Mockito.when(userRepository.findByDeleted(false)).thenReturn(Arrays.asList(
                user1, user2));
        List<User> result = userService.loadAllUsers();
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(user1, result.stream()
                .filter(u -> u.getId().equals(user1.getId()))
                .findAny().orElse(null));
        Assert.assertEquals(user2, result.stream()
                .filter(u -> u.getId().equals(user2.getId()))
                .findAny().orElse(null));
    }

    @Test
    public void loadUserSuccess() {
        User user = new User(1L, "user25", "user25@mail.ru");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.loadUser(1L);
        Assert.assertEquals(user, result);
    }

    @Test
    public void loadUserFail() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ThrowingRunnable runnable = () -> userService.loadUser(1L);
        Assert.assertThrows(ResponseStatusException.class, runnable);
    }

    @Test
    public void createUserSuccess() {
        User user = new User("user25", "user25@mail.ru");
        User savedUser = new User(1L, "user25", "user25@mail.ru");
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        String result = userService.createUser(user);
        Assert.assertEquals("user with id = 1 successfully created", result);
    }

    @Test
    public void createUserFail() {
        User user = new User("user25", "user25@mail.ru");
        Mockito.when(userRepository.save(user)).thenThrow(PersistenceException.class);
        ThrowingRunnable runnable = () -> userService.createUser(user);
        Assert.assertThrows(PersistenceException.class, runnable);
    }

    @Test
    public void updateUserSuccess() {
        User user = new User(1L, "user25", "user25@mail.ru");
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        String result = userService.updateUser(user, 1);
        Assert.assertEquals("user with id = 1 successfully updated", result);
    }

    @Test
    public void updateUserFail() {
        User user = new User(1L, "user25", "user25@mail.ru");
        Mockito.when(userRepository.existsById(1L)).thenReturn(false);
        ThrowingRunnable runnable = () -> userService.updateUser(user, 1L);
        Assert.assertThrows(ResponseStatusException.class, runnable);
    }

    @Test
    public void deleteUserSuccess() {
        User user = new User(1L, "user25", "user25@mail.ru");
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        String result = userService.deleteUser(1L);
        Assert.assertTrue(user.isDeleted());
        Assert.assertEquals("user with id = 1 successfully deleted", result);
    }

    @Test
    public void deleteUserFail() {
        User user = new User(1L, "user25", "user25@mail.ru");
        Mockito.when(userRepository.existsById(1L)).thenReturn(false);
        ThrowingRunnable runnable = () -> userService.deleteUser(1L);
        Assert.assertThrows(ResponseStatusException.class, runnable);
    }

}