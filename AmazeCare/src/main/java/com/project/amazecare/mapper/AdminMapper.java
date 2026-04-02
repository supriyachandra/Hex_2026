package com.project.amazecare.mapper;

import com.project.amazecare.dto.AddAdminDto;
import com.project.amazecare.model.Admin;

public class AdminMapper {

    public static Admin mapToAdmin(AddAdminDto addAdminDto) {
        Admin admin= new Admin();
        admin.setName(addAdminDto.name());
        return admin;
    }
}
