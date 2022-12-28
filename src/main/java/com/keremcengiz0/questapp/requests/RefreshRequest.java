package com.keremcengiz0.questapp.requests;

import lombok.Data;

@Data
public class RefreshRequest {

    private Long userId;
    private String refreshToken;
}
