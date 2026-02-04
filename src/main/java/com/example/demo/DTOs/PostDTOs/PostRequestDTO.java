package com.example.demo.DTOs.PostDTOs;

import jakarta.validation.constraints.NotBlank;

public class PostRequestDTO {
    @NotBlank(message = "Titile không được để trống!")
    private String title;

    @NotBlank(message = "Content không được để trống!")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
