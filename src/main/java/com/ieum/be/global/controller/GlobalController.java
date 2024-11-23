package com.ieum.be.global.controller;

import com.ieum.be.global.response.GeneralResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GlobalController {
    @GetMapping("/")
    public GeneralResponse index() {
        return GeneralResponse.OK;
    }

    @GetMapping("/health")
    public String health() {
        return "Server is running";
    }
}
