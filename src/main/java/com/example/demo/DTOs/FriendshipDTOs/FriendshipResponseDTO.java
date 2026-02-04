package com.example.demo.DTOs.FriendshipDTOs;

import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;
import com.example.demo.Enum.FriendshipStatus;

import java.time.LocalDateTime;

public class FriendshipResponseDTO {
    private Long id;
    private Long otherUserId;
    private String otherUsername;
    private FriendshipStatus status;
    private LocalDateTime creatAt;

    public FriendshipResponseDTO(Friendship friendship, User currentUser) {
        this.id = friendship.getId();
        this.status = friendship.getStatus();
        this.creatAt = friendship.getCreatAt();

        if (!friendship.getFriend().equals(currentUser)) {
            this.otherUserId = friendship.getFriend().getId();
            this.otherUsername = friendship.getFriend().getUsername();
        } else {
            this.otherUserId = friendship.getUser().getId();
            this.otherUsername = friendship.getUser().getUsername();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatAt() {
        return creatAt;
    }
}
