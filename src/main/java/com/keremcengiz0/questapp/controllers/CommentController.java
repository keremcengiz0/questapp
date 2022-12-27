package com.keremcengiz0.questapp.controllers;

import com.keremcengiz0.questapp.entities.Comment;
import com.keremcengiz0.questapp.requests.CommentCreateRequest;
import com.keremcengiz0.questapp.requests.CommentUpdateRequest;
import com.keremcengiz0.questapp.responses.CommentResponse;
import com.keremcengiz0.questapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return this.commentService.getAllCommentsWithParam(userId, postId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest newCommentRequest) {
        return this.commentService.createOneComment(newCommentRequest);
    }


    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId) {
        return this.commentService.getOneCommentById(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest updateComment) {
        return this.commentService.updateOneCommentById(commentId, updateComment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId) {
        this.commentService.deleteOneCommentById(commentId);
    }
}
