package com.app.eventorganizer.controller;

import com.app.eventorganizer.dto.AuthRequestDTO;
import com.app.eventorganizer.dto.AuthResponseDTO;
import com.app.eventorganizer.dto.UserDTO;
import com.app.eventorganizer.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        authService.registerUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponse = authService.login(authRequestDTO);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
