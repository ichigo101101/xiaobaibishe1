package com.example.controller;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
//表明是个接口的入口
//controller是后台接口的入口，这个“接口”跟我们学习Java基础里面的“接口interface”是有区别，
// 我们这里的接口是针对于前端来说的，前端操作数据会调用后台的接口，前后台交互的入口
@RequestMapping ("/admin")
//入口的钥匙，匹配到一个地址
public class AdminController {
    /*
    *controller里的一个方法，它其实就是我们平常说的web项目的一个接口的入口
    *可以在这个方法上加一个url
    * 也可以指定请求方式，GET 查 POST 增 PUT 改 DELETE 删
    * */

    @Resource //引入Java bean
    private AdminService adminService; // Controller调用service，service调用dao

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin loginUser = adminService.login(admin);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @GetMapping
    public Result getUser() {
        List<Admin> list = adminService.getUser();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Admin admin) {
//        if (admin.getId() == null) {
//            adminService.add(admin);
//        } else {
//            adminService.update(admin);
//        }
//        return Result.success();
        try {
            if (admin.getId() == null) {
                adminService.add(admin);
            } else {
                adminService.update(admin);
            }
            return Result.success();
        } catch (CustomException e) {
            return Result.error(e.getMsg());
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return Result.success();
    }



//    @GetMapping("/start")
//    public  String start(){
//        return "欢迎！springboot启动!";
//    }
//
//    @GetMapping("/")
//    public List<Admin> getUser() {
//        return adminService.getUser();
//    }

}
