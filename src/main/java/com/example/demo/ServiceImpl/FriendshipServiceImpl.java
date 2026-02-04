package com.example.demo.ServiceImpl;

import com.example.demo.DTOs.FriendshipDTOs.FriendshipRequestDTO;
import com.example.demo.DTOs.FriendshipDTOs.FriendshipResponseDTO;
import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;
import com.example.demo.Enum.FriendshipStatus;
import com.example.demo.Repository.FriendshipRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.FriendshipService;
import com.example.demo.Util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    private FriendshipRepository friendshipRepository;
    private UserRepository userRepository;
    private SecurityUtil securityUtil;

    @Autowired
    public FriendshipServiceImpl(
            FriendshipRepository friendshipRepository,
            UserRepository userRepository,
            SecurityUtil securityUtil
    ) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    @Transactional
    public void sendFriendRequest(FriendshipRequestDTO dto) {
        User targetUser = userRepository.findById(dto.getTargetUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người muốn kết bạn!"));

        User currentUser = securityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("Bạn chưa đăng nhập!");
        }

        if (currentUser.equals(targetUser)) {
            throw new RuntimeException("bạn không thể gửi lời mời kết bạn với chính bản thân!");
        }

        Friendship friendship = new Friendship(currentUser, targetUser);
        friendshipRepository.save(friendship);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("không tìm thấy yêu cầu kết bạn!"));

        User currentUser = securityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("Bạn chưa đăng nhập!");
        }

        if (!friendship.getFriend().equals(currentUser)) {
            throw new RuntimeException("Đây không phải yêu cầu gửi đến bạn!");
        }

        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new RuntimeException("không ở trạng thái đang chờ phản hồi!");
        }

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendship.setUpdateAt(LocalDateTime.now());
        friendshipRepository.save(friendship);
    }

    @Override
    @Transactional
    public void rejectFriendRequest(Long friendshipId){
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu kết bạn!"));

        User currentUser = securityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("Bạn cha đăng nhập!");
        }

        if (!friendship.getFriend().equals(currentUser)) {
            throw new RuntimeException("Đây không phải yêu cầu gửi đến bạn!");
        }

        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new RuntimeException("Không ở trạng thái đang chờ phản hồi!");
        }

        friendshipRepository.delete(friendship);
    }

    @Override
    public List<FriendshipResponseDTO> getMyFriends(){
        User currentUser = securityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("Bạn cần đăng nhập vào hệ thống!");
        }

        List<Friendship> friendships = friendshipRepository
                .findFriendshipsByUserOrFriendAndStatus(currentUser, FriendshipStatus.ACCEPTED);

        return friendships.stream()
                .map(friendship -> new FriendshipResponseDTO(friendship, currentUser))
                .collect(Collectors.toList());
    }

    // Test git pull

    @Override
    public List<FriendshipResponseDTO> getFriendsOfOtherUser(Long userId) {
        User currentUser = securityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("Bạn cần đăng nhập hệ thống!");
        }

        User otherUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        List<Friendship> friendshipsOfOtherUser = friendshipRepository
                .findFriendshipsByUserOrFriendAndStatus(otherUser, FriendshipStatus.ACCEPTED);

        return friendshipsOfOtherUser.stream()
                .map(friendship -> new FriendshipResponseDTO(friendship, otherUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendshipResponseDTO> getPendingRequest(){
        User currentUser = securityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("Bạn cần đăng nhập vào hệ thống!");
        }

        List<Friendship> friendRequests = friendshipRepository
                .findByFriendAndStatus(currentUser, FriendshipStatus.PENDING);

        return friendRequests.stream()
                .map(friendship -> new FriendshipResponseDTO(friendship, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFriend(Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mối liên hệ bạn bè!"));

        if (friendship.getStatus() != FriendshipStatus.ACCEPTED) {
            throw new RuntimeException("Hiện không là bạn bè!");
        }

        User currentUser = securityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("Bạn cần đăng nhập vào hệ thống!");
        }

        friendshipRepository.delete(friendship);
    }
}
