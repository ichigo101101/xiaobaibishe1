package com.example.controller;

import com.example.entity.Admin;
import com.example.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //表明是个接口的入口
//controller是后台接口的入口，这个“接口”跟我们学习Java基础里面的“接口interface”是有区别，
// 我们这里的接口是针对于前端来说的，前端操作数据会调用后台的接口，前后台交互的入口
@RequestMapping ("/admin") //入口的钥匙，匹配到一个地址
public class AdminController {
    /*
    *controller里的一个方法，它其实就是我们平常说的web项目的一个接口的入口
    *可以在这个方法上加一个url
    * 也可以指定请求方式，GET增 POST删 PUT改 DELETE查
    * */

    @Resource //引入Java bean
    private AdminService adminService; // Controller调用service，service调用dao


    @GetMapping("/start")
    public  String start(){
        return "欢迎！springboot启动!";
    }

    @GetMapping("/")
    public List<Admin> getUser() {
        return adminService.getUser();
    }
}
