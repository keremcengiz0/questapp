package com.keremcengiz0.questapp.services;

import com.keremcengiz0.questapp.entities.User;
import com.keremcengiz0.questapp.repository.abstracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return this.userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            this.userRepository.save(foundUser);
            return foundUser;
        } else {
            return null;
        }
    }

    public void deleteById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {                                         // this.userRepository.deleteById(userId) olarak değişebilir.
            User foundUserToDelete = user.get();
            this.userRepository.deleteById(foundUserToDelete.getId());
        }
    }
}
