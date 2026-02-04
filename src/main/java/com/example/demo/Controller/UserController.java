package com.example.demo.Controller;

import com.example.demo.DTOs.UserDTOs.UserRequestDTO;
import com.example.demo.DTOs.UserDTOs.UserResponseDTO;
import com.example.demo.Service.UserService;
import com.example.demo.Util.ApiResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(users, "Lấy danh sách users thành công!"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(
            @Valid @RequestBody UserRequestDTO dto
            ) {
        UserResponseDTO result = userService.register(dto);
        return ResponseEntity.ok(new ApiResponse<>(result, "Đăng ký thành công!"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(
            @RequestBody UserRequestDTO dto
    ) {
        UserResponseDTO result = userService.login(dto);
        return ResponseEntity.ok(new ApiResponse<>(result, "Đănh nhập thành công!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(
            @PathVariable Long id
    ) {
        userService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "Xóa thành công!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO dto
    ) {
        UserResponseDTO user = userService.updateUser(dto, id);
        return ResponseEntity.ok(new ApiResponse<>(user, "Update thành công user có id là " + id));
    }

}
