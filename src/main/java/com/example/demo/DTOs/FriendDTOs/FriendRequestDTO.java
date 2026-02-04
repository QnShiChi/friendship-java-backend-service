package com.example.demo.DTOs.FriendDTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class FriendRequestDTO {

    @NotBlank(message = "Tên không được để trống!")
    private String name;

    @Min(value = 1, message = "Tuổi phải lớn hơn hoặc bằng 1!")
    private Integer age;

    @NotBlank(message = "Thành phố không được để trống!")
    private String city;

    @NotBlank(message = "Sở thích không được để trống!")
    private String hobby;

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
