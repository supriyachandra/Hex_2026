package com.project.amazecare.service;

import com.project.amazecare.dto.UserDetailsDto;
import com.project.amazecare.model.User;
import com.project.amazecare.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.getUserByUsername(username);
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public UserDetailsDto userRole(String username) {
        User user= userRepository.getRole(username);
        return new UserDetailsDto(
                user.getUsername(),
                user.getRole()
        );
    }
}
