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
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/location")
    public List<UserLocationDto> getUserLocation(Principal principal) {
        return this.userService.getUserLocation(principal.getName());
    }

    @PostMapping("/location")
    public GeneralResponse createUserLocation(@Valid @RequestBody UserLocationDto createUserLocationDto, Principal principal) {
        return this.userService.createUserLocation(createUserLocationDto, principal.getName());
    }

    @PatchMapping("/location")
    public GeneralResponse updateUserLocation(@RequestParam Integer id, Principal principal) {
        return this.userService.updateUserLocation(Long.valueOf(id), principal.getName());
    }

    @DeleteMapping("/location")
    public GeneralResponse deleteUserLocation(@RequestParam Long id, Principal principal) {
        return this.userService.deleteLocation(id, principal.getName());
    }

    @GetMapping("/info")
    public UserInfoDto getUserInfo(Principal principal) {
        return this.userService.getUserInfo(principal.getName());
    }
}
