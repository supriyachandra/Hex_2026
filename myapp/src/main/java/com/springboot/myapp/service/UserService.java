package com.springboot.myapp.service;


import com.springboot.myapp.dto.UserDto;
import com.springboot.myapp.model.User;
import com.springboot.myapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUserDetails(String username) {
        User user= (User) userRepository.getUserByUsername(username);
        return new UserDto(
                user.getUsername(),
                user.getRole()
        );
    }

    public User insertUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.getUserByUsername(username);
        return user;
    }

}
