package com.example.demo.DTOs.FriendshipDTOs;

import jakarta.validation.constraints.NotNull;

public class FriendshipRequestDTO {
    @NotNull(message = "Không được bỏ trống giá trị!")
    private Long targetUserId;

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
