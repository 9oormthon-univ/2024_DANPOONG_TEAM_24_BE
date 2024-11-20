package com.ieum.be.domain.user.controller;

import com.ieum.be.domain.user.dto.CreateUserDto;
import com.ieum.be.domain.user.dto.LoginDto;
import com.ieum.be.domain.user.dto.UserLocationDto;
import com.ieum.be.domain.user.service.UserService;
import com.ieum.be.global.response.GeneralResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public GeneralResponse createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return this.userService.createUser(createUserDto);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        return this.userService.login(loginDto);
    }

    @GetMapping("/location")
    public UserLocationDto getUserLocation(@RequestHeader("X-USER-ID") Long userId) {
        return this.userService.getUserLocation(userId);
    }

    @PatchMapping("/location")
    public GeneralResponse updateUserLocation(@Valid @RequestBody UserLocationDto updateLocationDto, @RequestHeader("X-USER-ID") Long userId) {
        return this.userService.updateUserLocation(updateLocationDto, userId);
    }
}
