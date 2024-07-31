package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.CaptchaUtilAdapter;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;

    @PostMapping("/login")
//    @AutoLog("システムにログインする")
    public Result login(@RequestBody Admin admin, @RequestParam String key, HttpServletRequest request) {
        // 获取验证码
        String verCode = admin.getVerCode();

        // 验证验证码是否为空
        if (verCode == null || !verCode.toLowerCase().equals(CaptureConfig.CAPTURE_MAP.get(key))) {
            // 如果验证码为空或不相等，说明验证不通过
            CaptchaUtilAdapter.clear(request);
            return Result.error("認証コードが正しくありません");
        }

        try {
            Admin loginUser = adminService.login(admin);
            CaptureConfig.CAPTURE_MAP.remove(key);
            return Result.success(loginUser);
        } catch (CustomException e) {
            return Result.error(e.getMsg());
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        try {
            adminService.add(admin);
            return Result.success();
        } catch (CustomException e) {
            return Result.error(e.getMsg());
        }
    }

    @GetMapping
    public Result getUser() {
        List<Admin> list = adminService.getUser();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("拦截器已放行，正式调用接口内部，查询管理员信息");
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Admin admin) {
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
}
