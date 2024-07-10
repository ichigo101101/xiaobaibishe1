package com.example.service;

import com.example.dao.AdminDao;
import com.example.entity.Admin;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private AdminDao userDao; // Controller调用service，service调用dao

    public List<Admin> getUser() {
        return userDao.getUser();
//        // 3. 使用引入的包
//        return userDao.selectAll();
    }
}
