package com.example.demo.ServiceImpl;

import com.example.demo.DTOs.FriendDTOs.FriendRequestDTO;
import com.example.demo.DTOs.FriendDTOs.FriendResponseDTO;
import com.example.demo.Entity.Friend;
import com.example.demo.Exception.FriendNotFoundException;
import com.example.demo.Repository.FriendRepository;
import com.example.demo.Service.FriendService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {
    private FriendRepository friendRepository;

    @Autowired
    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public List<FriendResponseDTO> getAllFriends() {
        List<Friend> friends = friendRepository.findAll();
        return friends.stream().map(FriendResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Page<FriendResponseDTO> getAllFriends(Pageable pageable) {
        Page<Friend> page = friendRepository.findAll(pageable);
        return page.map(FriendResponseDTO::new);
    }

    @Override
    public FriendResponseDTO getFriendById(Long id) {
        Friend friend = friendRepository.findById(id)
                .orElseThrow(() -> new FriendNotFoundException("Không tìm thấy friend nào có id là " + id + " !"));
        return new FriendResponseDTO(friend);
    }

    @Override
    @Transactional
    public FriendResponseDTO createFriend(FriendRequestDTO dto) {
        Friend friend = new Friend();
        friend.setName(dto.getName());
        friend.setAge(dto.getAge());
        friend.setCity(dto.getCity());
        friend.setHobby(dto.getHobby());

        Friend saved = friendRepository.save(friend);

        return new FriendResponseDTO(saved);
    }

    @Override
    @Transactional
    public FriendResponseDTO updateFriend(Long id, FriendRequestDTO dto) {
        Friend friend = friendRepository.findById(id)
                .orElseThrow(() -> new FriendNotFoundException("Không tìm thấy friend nào có id là " + id));

        boolean checked = false;

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            friend.setName(dto.getName());
            checked  = true;
        }

        if (dto.getAge() != null && dto.getAge() > 0) {
            friend.setAge(dto.getAge());
            checked  = true;
        }

        if (dto.getCity() != null && !dto.getCity().trim().isEmpty()) {
            friend.setCity(dto.getCity());
            checked  = true;
        }

        if (dto.getHobby() != null && !dto.getHobby().trim().isEmpty()) {
            friend.setHobby(dto.getHobby());
            checked  = true;
        }

        if (!checked) {
            return new FriendResponseDTO(friend);
        }

        Friend updated = friendRepository.save(friend);

        return new FriendResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteFriend(Long id) {
        if (!friendRepository.existsById(id)) {
            throw new FriendNotFoundException("Không tìm thấy friend nào có id là " + id);
        }
        friendRepository.deleteById(id);
    }
}
