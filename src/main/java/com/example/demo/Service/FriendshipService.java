package com.example.demo.Service;

import com.example.demo.DTOs.FriendshipDTOs.FriendshipRequestDTO;
import com.example.demo.DTOs.FriendshipDTOs.FriendshipResponseDTO;

import java.util.List;

public interface FriendshipService {
    void sendFriendRequest(FriendshipRequestDTO dto);
    void acceptFriendRequest(Long friendshipId);
    void rejectFriendRequest(Long friendshipId);
    List<FriendshipResponseDTO> getMyFriends();
    List<FriendshipResponseDTO> getFriendsOfOtherUser(Long userId);
    List<FriendshipResponseDTO> getPendingRequest();
    void deleteFriend(Long friendshipId);
}
