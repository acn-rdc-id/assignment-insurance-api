package com.azid.auth.backend.AZ.Auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ServerController {

    @GetMapping(value = { "/", "/api" })
    public String server() {
        return "App is running...";
    }

}
