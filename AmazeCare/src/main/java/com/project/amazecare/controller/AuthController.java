package com.project.amazecare.controller;

import com.project.amazecare.dto.UserDetailsDto;
import com.project.amazecare.service.UserService;
import com.project.amazecare.utility.JwtUtility;
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
    public ResponseEntity<Map<String, String>> login(Principal principal) {
        String loggedInUser = principal.getName();
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtUtility.generateToken(loggedInUser));
        return ResponseEntity.status(HttpStatus.OK).
                body(map);
    }

    @GetMapping("/user-details")
    public UserDetailsDto userDetails(Principal principal){
        return userService.userRole(principal.getName());
    }
}
