package com.weatherfit.sign.service;

import com.weatherfit.user.entity.UserEntity;
import com.weatherfit.user.mapper.UserMapper;
import com.weatherfit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignBO {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public boolean setUser(String loginId, String password, String name, String email) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        UserEntity user = userRepository.save(UserEntity.builder()
                            .loginId(loginId)
                            .password(hashedPassword)
                            .name(name)
                            .email(email)
                            .build());
        return user != null;
    }

    public boolean isDuplicateLoginId(String loginId) {
        UserEntity user = userRepository.findByLoginId(loginId).orElse(null);
        return user == null;
    }

    public UserEntity getUser(String loginId, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<UserEntity> userEntity = userRepository.findByLoginId(loginId);
        if (userEntity.isEmpty()) {
            return null;
        }

        UserEntity user = userEntity.get();
        if (encoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    public UserEntity findUserLoginId(String name, String email) {
        return userRepository.findByNameAndEmail(name, email).orElse(null);
    }

    public UserEntity findUserPw(String loginId, String name, String email) {
        return userRepository.findByLoginIdAndNameAndEmail(loginId, name, email).orElse(null);
    }

    public void updatePw(String loginId, String name, String email, String newPw) {
        userMapper.updatePw(loginId, name, email, newPw);
    }
}
