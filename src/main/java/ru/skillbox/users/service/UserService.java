package ru.skillbox.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.users.entity.User;
import ru.skillbox.users.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> loadAllUsers() {
        return userRepository.findByDeleted(false);
    }

    public User loadUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String createUser(User user) {
        User savedUser = userRepository.save(user);
        return String.format("user with id = %s successfully created", savedUser.getId());
    }

    public String updateUser(User user, long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);
        return String.format("user with id = %s successfully updated", user.getId());
    }

    @Transactional
    public String deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.findById(id)
                .ifPresent(user -> user.setDeleted(true));
        return String.format("user with id = %s successfully deleted", id);
    }
}
