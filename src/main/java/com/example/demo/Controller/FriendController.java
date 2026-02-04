package com.example.demo.Controller;

import com.example.demo.DTOs.FriendDTOs.FriendRequestDTO;
import com.example.demo.DTOs.FriendDTOs.FriendResponseDTO;
import com.example.demo.Service.FriendService;
import com.example.demo.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public  FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

//    @GetMapping("")
//    public ResponseEntity<ApiResponse<List<FriendResponseDTO>>> getAllFriends() {
//        List<FriendResponseDTO> friends = friendService.getAllFriends();
//        return ResponseEntity.ok(new ApiResponse<>(friends, "Lấy danh sách friends thành công!"));
//    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<FriendResponseDTO>>> getAllFriends(
            @PageableDefault(size = 10 , sort = "id", direction = Sort.Direction.ASC) Pageable pageable
            ) {
        Page<FriendResponseDTO> page = friendService.getAllFriends(pageable);
        return ResponseEntity.ok(new ApiResponse<>(page,
                String.format("Lấy danh sách bạn thành công (trang %d/%d)",
                        page.getNumber(), page.getTotalPages())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FriendResponseDTO>> getFriendById(
            @PathVariable Long id
    ) {
        FriendResponseDTO friend = friendService.getFriendById(id);
        return ResponseEntity.ok(new ApiResponse<>(friend, "Lấy thành công friend có id là " + id));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<FriendResponseDTO>> createFriend(
            @Valid @RequestBody FriendRequestDTO dto
            ) {
        FriendResponseDTO friend = friendService.createFriend(dto);
        return ResponseEntity.ok(new ApiResponse<>(friend, "Thêm friend thành công!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FriendResponseDTO>> updateFriend(
            @PathVariable Long id,
            @RequestBody FriendRequestDTO dto
    ) {
        FriendResponseDTO friend = friendService.updateFriend(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(friend, "Cập nhật thành công friend có id là " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFriend(
            @PathVariable Long id
    ) {
        friendService.deleteFriend(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "Xóa thành công friend có id là " + id));
    }
}
