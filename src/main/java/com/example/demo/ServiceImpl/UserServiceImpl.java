package com.example.demo.ServiceImpl;

import com.example.demo.DTOs.UserDTOs.UserRequestDTO;
import com.example.demo.DTOs.UserDTOs.UserResponseDTO;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private  UserRepository userRepository;
    private  JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDTO::new).collect(Collectors.toList());
    }

    public UserResponseDTO register(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        User user = new User();

        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);
        return new UserResponseDTO(saved);
    }

    public UserResponseDTO login(UserRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new UserResponseDTO(user, token);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy user có id là " + id);
        }
        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser(UserRequestDTO dto, Long id) {

        User user  = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user nào có id là " + id));

        boolean changed = false;
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            String newEmail = dto.getEmail().toLowerCase();

            Optional<User> userExisting = userRepository.findByEmail(newEmail);
            if (userExisting.isPresent()) {
                throw new RuntimeException("Đã có user sử dụng email này!");
            }

            user.setEmail(newEmail);
            changed = true;
        }

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            if (dto.getPassword().length() < 6) {
                throw new RuntimeException("Mật khẩu phải hơn 6 kí tự!");
            }
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            changed = true;
        }

        if (!changed) {
            return new UserResponseDTO(user);
        }

        User updated = userRepository.save(user);

        return new UserResponseDTO(updated);
    }
}
