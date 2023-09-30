package ru.skillbox.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.users.entity.User;
import ru.skillbox.users.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> loadAllUsers() {
        return userRepository.findAllByDeleted(false);
    }


}
