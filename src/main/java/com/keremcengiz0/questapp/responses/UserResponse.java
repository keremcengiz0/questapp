package com.keremcengiz0.questapp.responses;

import com.keremcengiz0.questapp.entities.User;
import lombok.Data;

@Data
public class UserResponse {
     Long id;
     int avatarId;
     String userName;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.avatarId = entity.getAvatar();
        this.userName = entity.getUserName();
    }
}
