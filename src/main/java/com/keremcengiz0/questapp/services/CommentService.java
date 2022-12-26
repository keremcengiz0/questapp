package com.keremcengiz0.questapp.services;

import com.keremcengiz0.questapp.entities.Comment;
import com.keremcengiz0.questapp.entities.Post;
import com.keremcengiz0.questapp.entities.User;
import com.keremcengiz0.questapp.repository.abstracts.CommentRepository;
import com.keremcengiz0.questapp.requests.CommentCreateRequest;
import com.keremcengiz0.questapp.requests.CommentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return this.commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            return this.commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return this.commentRepository.findByPostId(postId.get());
        } else {
            return this.commentRepository.findAll();
        }
    }

    public Comment getOneCommentById(Long commentId) {
        return this.commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
        User user = this.userService.getOneUserById(newCommentRequest.getUserId());
        Post post = this.postService.getOnePostById(newCommentRequest.getPostId());

        if (user != null && post != null) {
            Comment toSaveComment = new Comment();
            toSaveComment.setId(newCommentRequest.getId());
            toSaveComment.setText(newCommentRequest.getText());
            toSaveComment.setUser(user);
            toSaveComment.setPost(post);
            toSaveComment.setCreateDate(new Date());
            this.commentRepository.save(toSaveComment);
            return toSaveComment;
        } else {
            return null;
        }
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest updateComment) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isPresent()) {
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(updateComment.getText());
            this.commentRepository.save(commentToUpdate);
            return commentToUpdate;
        }
        return null;
    }

    public void deleteOneCommentById(Long commentId) {
        this.commentRepository.deleteById(commentId);
    }
}
