package com.example.demo.Repository;

import com.example.demo.Entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByCityIgnoreCase(String city);
}
