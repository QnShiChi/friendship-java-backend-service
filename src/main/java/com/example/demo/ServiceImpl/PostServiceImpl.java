package com.example.demo.ServiceImpl;

import com.example.demo.DTOs.PostDTOs.PostRequestDTO;
import com.example.demo.DTOs.PostDTOs.PostResponseDTO;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Service.PostService;
import com.example.demo.Util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private SecurityUtil securityUtil;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, SecurityUtil securityUtil) {
        this.postRepository = postRepository;
        this.securityUtil = securityUtil;
    }

    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }

    public PostResponseDTO getPostById(Long id) {
        Post post  = postRepository.findById(id).
                orElseThrow(() -> new RuntimeException("không tìm thấy post có id là " + id));
        return new PostResponseDTO(post);
    }

    public PostResponseDTO creatPost(PostRequestDTO dto) {
        User currentUser = securityUtil.getCurrentUser();
        Post post = new Post();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(currentUser);

        Post saved = postRepository.save(post);

        currentUser.addPost(post);

        return new PostResponseDTO(saved);
    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết nào có id là " + id));

        User currentUser = securityUtil.getCurrentUser();

        if (!post.getAuthor().getId().equals(currentUser.getId())){
            throw new RuntimeException("Bạn không có quyền chỉnh sửa bài viết này!");
        }
        boolean changed = false;
        if (dto.getTitle() != null && !dto.getTitle().trim().isEmpty()) {
            changed = true;
            post.setTitle(dto.getTitle());
        }

        if (dto.getContent() != null && !dto.getContent().trim().isEmpty()) {
            changed = true;
            post.setContent(dto.getContent());
        }

        if (!changed) {
            return new PostResponseDTO(post);
        }

        post.setUpdateAt(LocalDateTime.now());

        Post updated = postRepository.save(post);

        return new PostResponseDTO(updated);

    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("không tìm thấy bài viết nào có id là " + id));

        User currentUser = securityUtil.getCurrentUser();

        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Bạn không có quyền xóa bài viết này!");
        }

        postRepository.delete(post);
    }

}
