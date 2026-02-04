package com.example.demo.Service;

import com.example.demo.DTOs.FriendDTOs.FriendRequestDTO;
import com.example.demo.DTOs.FriendDTOs.FriendResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendService {
    List<FriendResponseDTO> getAllFriends();
    Page<FriendResponseDTO> getAllFriends(Pageable pageable);
    FriendResponseDTO getFriendById(Long id);
    FriendResponseDTO createFriend(FriendRequestDTO dto);
    FriendResponseDTO updateFriend(Long id, FriendRequestDTO dto);
    void deleteFriend(Long id);
}
