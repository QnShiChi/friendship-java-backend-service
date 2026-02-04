package com.example.demo.DTOs.PostDTOs;

import com.example.demo.Entity.Post;

public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorEmail;
    private String createAt;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorEmail = post.getAuthor().getEmail();
        this.createAt = post.getCreateAt().toString();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getCreateAt() {
        return createAt;
    }
}
