package com.ieum.be.global.controller;

import com.ieum.be.global.response.GeneralResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalController {

    @GetMapping("/health")
    public GeneralResponse health() {
        return GeneralResponse.OK;
    }
}
