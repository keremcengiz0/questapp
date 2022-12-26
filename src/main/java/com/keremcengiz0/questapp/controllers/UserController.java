package com.keremcengiz0.questapp.controllers;

import com.keremcengiz0.questapp.entities.User;
import com.keremcengiz0.questapp.responses.UserResponse;
import com.keremcengiz0.questapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return this.userService.getAllUser();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {     // Gelen istekte bodydeki bilgileri User objesinde mapler.
        return this.userService.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId) {
        return new UserResponse(this.userService.getOneUserById(userId));
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        return this.userService.updateOneUser(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        this.userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId) {
        return this.userService.getUserActivity(userId);
    }
}
