package com.example.demo.Service;

import com.example.demo.DTOs.PostDTOs.PostRequestDTO;
import com.example.demo.DTOs.PostDTOs.PostResponseDTO;

import java.util.List;

public interface PostService {
    List<PostResponseDTO> getAllPosts();
    PostResponseDTO getPostById(Long id);
    PostResponseDTO creatPost(PostRequestDTO dto);
    PostResponseDTO updatePost(Long id, PostRequestDTO dto);
    void deletePost(Long id);
}
