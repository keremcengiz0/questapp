package com.keremcengiz0.questapp.services;

import com.keremcengiz0.questapp.entities.Like;
import com.keremcengiz0.questapp.entities.Post;
import com.keremcengiz0.questapp.entities.User;
import com.keremcengiz0.questapp.repository.abstracts.PostRepository;
import com.keremcengiz0.questapp.requests.PostCreateRequest;
import com.keremcengiz0.questapp.requests.PostUpdateRequest;
import com.keremcengiz0.questapp.responses.LikeResponse;
import com.keremcengiz0.questapp.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    private LikeService likeService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;

        if(userId.isPresent()) {
            list = this.postRepository.findByUserId(userId.get());
        } else {
            list = this.postRepository.findAll();
        }
        return list.stream().map(post -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(null, Optional.of(post.getId()));
            return new PostResponse(post, likes);}).collect(Collectors.toList());
    }

    public Post getOnePostById(Long postId) {
        return this.postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = this.userService.getOneUserById(newPostRequest.getUserId());

        if(user == null) {
            return null;
        }

        Post toSave = new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        this.postRepository.save(toSave);
        return toSave;
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent()) {
            Post postToUpdate = post.get();
            postToUpdate.setTitle(updatePost.getTitle());
            postToUpdate.setText(updatePost.getText());
            this.postRepository.save(postToUpdate);
            return postToUpdate;
        }
        return null;
    }

    public void deleteOnePostById(Long postId) {
        this.postRepository.deleteById(postId);
    }
}
