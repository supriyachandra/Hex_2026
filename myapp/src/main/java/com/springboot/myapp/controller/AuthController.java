package com.springboot.myapp.controller;

import com.springboot.myapp.dto.UserDto;
import com.springboot.myapp.service.UserService;
import com.springboot.myapp.utility.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

    private final JwtUtility jwtUtility;
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal) {
        String loggedInUser = principal.getName();
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtUtility.generateToken(loggedInUser));
        return ResponseEntity.status(HttpStatus.OK).
                body(map);
    }

    @GetMapping("/user-details")
    public ResponseEntity<UserDto> getUserDetails(Principal principal){
        UserDto userDto= userService.getUserDetails(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
