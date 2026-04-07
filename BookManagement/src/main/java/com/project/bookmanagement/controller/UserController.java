package com.project.bookmanagement.controller;

import com.project.bookmanagement.dto.UserDto;
import com.project.bookmanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
        userService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
