package com.ieum.be.domain.user.service;

import com.ieum.be.domain.user.dto.UserInfoDto;
import com.ieum.be.domain.user.dto.UserLocationDto;
import com.ieum.be.domain.user.entity.Location;
import com.ieum.be.domain.user.entity.User;
import com.ieum.be.domain.user.repository.LocationRepository;
import com.ieum.be.domain.user.repository.UserRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public UserService(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    public List<UserLocationDto> getUserLocation(String email) {
        List<Location> locations = this.locationRepository.findByUserEmailOrderByPriorityAsc(email);

        return locations.stream().map(UserLocationDto::of).toList();
    }

    public GeneralResponse createUserLocation(UserLocationDto updateLocationDto, String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        Location location = Location.builder()
                .latitude(updateLocationDto.latitude())
                .longitude(updateLocationDto.longitude())
                .user(user)
                .priority(0)
                .build();

        List<Location> locations = user.getLocations();
        locations.forEach(userLocation -> userLocation.setPriority(userLocation.getPriority() + 1));

        locationRepository.save(location);
        locationRepository.saveAll(locations);

        return GeneralResponse.OK;
    }

    public GeneralResponse updateUserLocation(Long id, String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        List<Location> userLocations = this.locationRepository.findByUserEmailOrderByPriorityAsc(user.getEmail());

        Location targetLocation = userLocations.stream()
                .filter(location -> location.getId().equals(id))
                .findFirst().orElseThrow(() -> new GlobalException(GeneralResponse.LOCATION_NOT_FOUND));

        if (targetLocation.getPriority() == 0) {
            return GeneralResponse.OK;
        }

        userLocations.forEach(location -> {
            if (location.getPriority() < targetLocation.getPriority()) {
                location.setPriority(location.getPriority() + 1);
            }
        });

        targetLocation.setPriority(0);

        this.locationRepository.saveAll(userLocations);

        return GeneralResponse.OK;
    }

    public GeneralResponse deleteLocation(Long id, String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        List<Location> userLocations = this.locationRepository.findByUserEmailOrderByPriorityAsc(user.getEmail());

        Location targetLocation = userLocations.stream()
                .filter(location -> location.getId().equals(id))
                .findFirst().orElseThrow(() -> new GlobalException(GeneralResponse.LOCATION_NOT_FOUND));

        userLocations.remove(targetLocation);

        userLocations.forEach(location -> {
            if (location.getPriority() > targetLocation.getPriority()) {
                location.setPriority(location.getPriority() - 1);
            }
        });

        this.locationRepository.delete(targetLocation);
        this.locationRepository.saveAll(userLocations);

        return GeneralResponse.OK;
    }

    public UserInfoDto getUserInfo(String email) {
        User user = this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new GlobalException(GeneralResponse.USER_NOT_FOUND));

        return new UserInfoDto(user.getEmail(), user.getName(), user.getProfileUrl());
    }
}
