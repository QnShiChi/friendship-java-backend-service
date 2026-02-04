package com.example.demo.Controller;

import com.example.demo.DTOs.FriendshipDTOs.FriendshipRequestDTO;
import com.example.demo.DTOs.FriendshipDTOs.FriendshipResponseDTO;
import com.example.demo.Service.FriendshipService;
import com.example.demo.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {
    private FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }


    @PostMapping("/sendRequest")
    public ResponseEntity<ApiResponse<Void>> sendFriendRequest(
            @Valid
            @RequestBody FriendshipRequestDTO dto
            ) {
        friendshipService.sendFriendRequest(dto);
        return ResponseEntity.ok(new ApiResponse<>(null, "Gửi lời mời kết bạn thành công!"));
    }

    @PostMapping("/acceptRequest/{friendshipId}")
    public ResponseEntity<ApiResponse<Void>> acceptFriendRequest(
            @PathVariable Long friendshipId
    ) {
        friendshipService.acceptFriendRequest(friendshipId);
        return ResponseEntity.ok(new ApiResponse<>(null, "Chấp nhận lời mời kết bạn thành công!"));
    }

    @PostMapping("/reject/{friendShipId}")
    public ResponseEntity<ApiResponse<Void>> rejectFriendRequest(
            @PathVariable Long friendShipId
    ) {
        friendshipService.rejectFriendRequest(friendShipId);
        return ResponseEntity.ok(new ApiResponse<>(null, "Từ chối lời mời kết bạn thành công!"));
    }

    @GetMapping("/getFriends")
    public ResponseEntity<ApiResponse<List<FriendshipResponseDTO>>> getMyFriends() {
        List<FriendshipResponseDTO> friends = friendshipService.getMyFriends();
        return ResponseEntity.ok(new ApiResponse<>(friends, "Lấy danh sách bạn bè thành công!"));
    }

    @GetMapping("/getFriendsOfOtherUser/{targetUserId}")
    public ResponseEntity<ApiResponse<List<FriendshipResponseDTO>>> getFriendsOfOtherUser(
            @PathVariable Long targetUserId
    ) {
        List<FriendshipResponseDTO> friends = friendshipService.getFriendsOfOtherUser(targetUserId);
        return ResponseEntity
                .ok(new ApiResponse<>(friends,
                        "Lấy danh sách bạn bè của user có id là "
                + targetUserId + " thành công!"));
    }

    @GetMapping("/getPending")
    public ResponseEntity<ApiResponse<List<FriendshipResponseDTO>>> getPendingRequest() {
        List<FriendshipResponseDTO> pendingList = friendshipService.getPendingRequest();
        return ResponseEntity.ok(new ApiResponse<>(pendingList, "Lấy danh sách chờ thành công!"));
    }

    @DeleteMapping("/deleteFriend/{friendshipId}")
    public ResponseEntity<ApiResponse<Void>> deleteFriend(
            @PathVariable Long friendshipId
    ) {
        friendshipService.deleteFriend(friendshipId);
        return ResponseEntity.ok(new ApiResponse<>(null, "Xóa kết bạn thành công!"));
    }

}
