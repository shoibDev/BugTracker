package com.project.bugtracker.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(path = "/register")
        public ResponseEntity<AuthenticationResponse> response(
                @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
        }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> response(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

}
