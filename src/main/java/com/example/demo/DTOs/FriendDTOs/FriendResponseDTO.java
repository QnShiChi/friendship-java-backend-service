package com.example.demo.DTOs.FriendDTOs;

import com.example.demo.Entity.Friend;

public class FriendResponseDTO {
    private Long id;
    private String name;
    private Integer age;
    private String city;
    private String hobby;
    private String displayName;

    public FriendResponseDTO(Friend friend) {
        this.id = friend.getId();
        this.name = friend.getName();
        this.age = friend.getAge();
        this.city = friend.getCity();
        this.hobby = friend.getHobby();
        this.displayName = friend.getName() + " " + friend.getCity();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getHobby() {
        return hobby;
    }

    public String getDisplayName() {
        return displayName;
    }
}
