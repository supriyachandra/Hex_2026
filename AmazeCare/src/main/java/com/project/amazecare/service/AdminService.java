package com.project.amazecare.service;

import com.project.amazecare.dto.AddAdminDto;
import com.project.amazecare.dto.DoctorReqDto;
import com.project.amazecare.enums.Role;
import com.project.amazecare.mapper.AdminMapper;
import com.project.amazecare.mapper.DoctorMapper;
import com.project.amazecare.model.Admin;
import com.project.amazecare.model.Doctor;
import com.project.amazecare.model.Specialization;
import com.project.amazecare.model.User;
import com.project.amazecare.repository.AdminRepository;
import com.project.amazecare.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public void addAdmin(AddAdminDto addAdminDto) {
        Admin admin= AdminMapper.mapToAdmin(addAdminDto);

        // user creation, add username and encoded password, save user, DI into admin
        User user= new User();
        user.setRole(Role.ADMIN);
        user.setUsername(addAdminDto.username());
        user.setPassword(passwordEncoder.encode(addAdminDto.password()));
        userService.saveUser(user);

        admin.setUser(user);

        adminRepository.save(admin);
    }
}
