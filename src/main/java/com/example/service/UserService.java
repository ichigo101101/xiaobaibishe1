package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserDao userDao; // Controller调用service，service调用dao

    public List<User> getUser() {
        return userDao.getUser();
//        // 3. 使用引入的包
//        return userDao.selectAll();
    }
}
