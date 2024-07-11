package com.example.service;

import com.example.dao.AdminDao;
import com.example.entity.Admin;
import com.example.entity.Params;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminDao adminDao; // Controller调用service，service调用dao

    public List<Admin> getUser() {
        return adminDao.getUser();
//        // 3. 使用引入的包
//        return userDao.selectAll();
    }

    public List<Admin> findBySearch(Params params) {
        return adminDao.findBySearch(params);
    }
}
