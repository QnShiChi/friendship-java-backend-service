package com.example.demo.Controller;

import com.example.demo.DTOs.PostDTOs.PostRequestDTO;
import com.example.demo.DTOs.PostDTOs.PostResponseDTO;
import com.example.demo.Service.PostService;
import com.example.demo.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(new ApiResponse<>(posts, "Lấy danh sách posts thành công!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> getPostById(
            @PathVariable Long id
    ) {
        PostResponseDTO post = postService.getPostById(id);
        return ResponseEntity.ok(new ApiResponse<>(post, "Lấy post có id là " + id + " thành công!"));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<PostResponseDTO>> creatPost(
            @Valid @RequestBody PostRequestDTO dto
            ) {
        PostResponseDTO post = postService.creatPost(dto);
        return ResponseEntity.ok(new ApiResponse<>(post, "Tạo post thành công!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDTO dto
    ) {
        PostResponseDTO updated = postService.updatePost(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(updated, "Cập nhật thành công bài post có id là " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "Xóa thành công bài viết có id là " + id));
    }

}
