package com.keremcengiz0.questapp.responses;

import com.keremcengiz0.questapp.entities.Like;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class LikeResponse {

    private Long id;
    private Long userId;
    private Long postId;

    public LikeResponse(Like entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
    }

}
