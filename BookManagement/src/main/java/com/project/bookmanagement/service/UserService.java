package com.project.bookmanagement.service;

import com.project.bookmanagement.dto.UserDto;
import com.project.bookmanagement.model.User;
import com.project.bookmanagement.repository.BookRepository;
import com.project.bookmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.atLevel(Level.INFO).log("Called loadUserByUsername");
        User user= userRepository.getUserByUsername(username);
        return user;
    }

    public User saveUser(User user) {
        log.atLevel(Level.INFO).log("Called saveUser");
        return userRepository.save(user);
    }

    public void addUser(UserDto userDto) {
        log.atLevel(Level.INFO).log("Called addUser: user will be added as per the DTO");
        User user= new User();
        user.setRole(userDto.role());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setUsername(userDto.username());
        userRepository.save(user);
        log.atLevel(Level.INFO).log("User Added!");
    }
}
