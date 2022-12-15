package com.keremcengiz0.questapp.controllers;

import com.keremcengiz0.questapp.entities.Post;
import com.keremcengiz0.questapp.requests.PostCreateRequest;
import com.keremcengiz0.questapp.requests.PostUpdateRequest;
import com.keremcengiz0.questapp.responses.PostResponse;
import com.keremcengiz0.questapp.services.PostService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        return this.postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
        return this.postService.createOnePost(newPostRequest);
    }

    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable Long postId) {
        return this.postService.getOnePostById(postId);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost) {
        return this.postService.updateOnePostById(postId, updatePost);
    }

    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable Long postId) {
        this.postService.deleteOnePostById(postId);
    }
}
