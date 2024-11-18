package com.ieum.be.domain.user.service;

import com.ieum.be.domain.user.dto.CreateUserDto;
import com.ieum.be.domain.user.dto.LoginDto;
import com.ieum.be.domain.user.dto.UserLocationDto;
import com.ieum.be.domain.user.entity.User;
import com.ieum.be.domain.user.repository.UserRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public GeneralResponse createUser(CreateUserDto createUserDto) {
        this.userRepository.findUserByName(createUserDto.name())
                .ifPresent(user -> {
                    throw new GlobalException(GeneralResponse.USER_ALREADY_EXISTS);
                });

        // TODO: 기본 profileImageUrl 설정
        String profileImageUrl = createUserDto.profileUrl() == null ? "DEFAULT" : createUserDto.profileUrl();

        User user = User.builder()
                .name(createUserDto.name())
                .password(bCryptPasswordEncoder.encode(createUserDto.password()))
                .profileUrl(profileImageUrl)
                .build();

        this.userRepository.save(user);

        return GeneralResponse.OK;
    }

    public String login(LoginDto loginDto) {
        User user = this.userRepository.findUserByName(loginDto.name())
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new GlobalException(GeneralResponse.WRONG_PASSWORD);
        }

        // TODO: JWT 토큰 발급
        return "login success";
    }

    public UserLocationDto getUserLocation(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        return new UserLocationDto(user.getLatitude(), user.getLongitude());
    }

    public GeneralResponse updateUserLocation(UserLocationDto updateLocationDto, Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        user.setLatitude(updateLocationDto.latitude());
        user.setLongitude(updateLocationDto.longitude());

        this.userRepository.save(user);

        return GeneralResponse.OK;
    }
}
