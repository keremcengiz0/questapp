package com.keremcengiz0.questapp.services;

import com.keremcengiz0.questapp.entities.Like;
import com.keremcengiz0.questapp.entities.Post;
import com.keremcengiz0.questapp.entities.User;
import com.keremcengiz0.questapp.repository.abstracts.LikeRepository;
import com.keremcengiz0.questapp.requests.LikeCreateRequest;
import com.keremcengiz0.questapp.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {

        List<Like> likeList;

        if (userId.isPresent() && postId.isPresent()) {
            likeList = this.likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            likeList = this.likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            likeList = this.likeRepository.findByPostId(postId.get());
        } else {
            likeList = this.likeRepository.findAll();
        }

       return likeList.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like createOneLike(LikeCreateRequest newLikeRequest) {
        User user = this.userService.getOneUserById(newLikeRequest.getUserId());
        Post post = this.postService.getOnePostById(newLikeRequest.getPostId());

        if (user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setId(newLikeRequest.getId());
            likeToSave.setUser(user);
            likeToSave.setPost(post);
            this.likeRepository.save(likeToSave);
            return likeToSave;
        } else {
            return null;
        }
    }

    public Like getOneLikeById(Long likeId) {
        return this.likeRepository.findById(likeId).orElse(null);
    }

    public void deleteOneLikeById(Long likeId) {
        this.likeRepository.deleteById(likeId);
    }
}
