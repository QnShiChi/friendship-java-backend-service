package com.example.demo.Repository;

import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;
import com.example.demo.Enum.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByUserAndFriend(User user, User friend);
    @Query("SELECT f FROM Friendship f " + "WHERE (f.user = :user OR f.friend = :user) " +"AND f.status = :status")
    List<Friendship> findFriendshipsByUserOrFriendAndStatus(@Param("user") User user,@Param("status") FriendshipStatus status);
    List<Friendship> findByFriendAndStatus(User friend, FriendshipStatus status);
}
