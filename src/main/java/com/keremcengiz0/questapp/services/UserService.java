package com.keremcengiz0.questapp.services;

import com.keremcengiz0.questapp.entities.Comment;
import com.keremcengiz0.questapp.entities.Like;
import com.keremcengiz0.questapp.entities.User;
import com.keremcengiz0.questapp.repository.abstracts.CommentRepository;
import com.keremcengiz0.questapp.repository.abstracts.LikeRepository;
import com.keremcengiz0.questapp.repository.abstracts.PostRepository;
import com.keremcengiz0.questapp.repository.abstracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
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
            if(newUser.getUserName() != null && newUser.getPassword() != null) {
                foundUser.setUserName(newUser.getUserName());
                foundUser.setPassword(newUser.getPassword());
            }
            foundUser.setAvatar(newUser.getAvatar());
            this.userRepository.save(foundUser);
            return foundUser;
        } else {
            return null;
        }
    }

    public void deleteById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            User foundUserToDelete = user.get();
            this.userRepository.deleteById(foundUserToDelete.getId());
        }
    }

    public User getOneUserByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = this.postRepository.findTopByUserId(userId);

        if(postIds.isEmpty()) {
            return null;
        }

        List<Object> comments = this.commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = this.likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);

        return result;
    }
}
