package com.ieum.be.domain.user.controller;

import com.ieum.be.domain.user.dto.CreateUserDto;
import com.ieum.be.domain.user.dto.LoginDto;
import com.ieum.be.domain.user.dto.UserInfoDto;
import com.ieum.be.domain.user.dto.UserLocationDto;
import com.ieum.be.domain.user.service.UserService;
import com.ieum.be.global.response.GeneralResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/location")
    public UserLocationDto getUserLocation(Principal principal) {
        return this.userService.getUserLocation(principal.getName());
    }

    @PatchMapping("/location")
    public GeneralResponse updateUserLocation(@Valid @RequestBody UserLocationDto updateLocationDto, Principal principal) {
        return this.userService.updateUserLocation(updateLocationDto, principal.getName());
    }

    @GetMapping("/info")
    public UserInfoDto getUserInfo(Principal principal) {
        return this.userService.getUserInfo(principal.getName());
    }
}
