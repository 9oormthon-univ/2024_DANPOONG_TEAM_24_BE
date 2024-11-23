package com.ieum.be.domain.user.service;

import com.ieum.be.domain.user.dto.UserInfoDto;
import com.ieum.be.domain.user.dto.UserLocationDto;
import com.ieum.be.domain.user.entity.User;
import com.ieum.be.domain.user.repository.UserRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserLocationDto getUserLocation(String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        return new UserLocationDto(user.getLatitude(), user.getLongitude());
    }

    public GeneralResponse updateUserLocation(UserLocationDto updateLocationDto, String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        user.setLatitude(updateLocationDto.latitude());
        user.setLongitude(updateLocationDto.longitude());

        this.userRepository.save(user);

        return GeneralResponse.OK;
    }

    public UserInfoDto getUserInfo(String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        return new UserInfoDto(user.getEmail(), user.getName(), user.getProfileUrl());
    }
}
