package com.example.service;
import com.example.common.JwtTokenUtils;
import com.example.dao.AdminDao;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    public PageInfo<Admin> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Admin> list = adminDao.findBySearch(params);
        return PageInfo.of(list);
    }

//    public void add(Admin admin) {
//        //1 用户名一定要有，否则不让新增（后面需要用户名登录）
//        if (admin.getName() == null || admin.getName().isEmpty()) {
//            throw new CustomException("用户名不能为空");
//        }
//        //2 进行重复性判断 同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就就可以了
//        Admin User = adminDao.findByName(admin.getName());
//        if (User != null) {
//            //说明已经有了 这里我们就要提示前台不允许新增另外
//            throw new CustomException("该用户名已存在，请更换用户名");
//        }
//        // 初始化一个密码
//        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
//            admin.setPassword("123456");
//        }
//        adminDao.insertSelective(admin);
//    }

    public void add(Admin admin) {
        // 用户名不能为空
        if (admin.getName() == null || admin.getName().isEmpty()) {
            throw new CustomException("ユーザー名は必ず入力してください");
        }

        // 查询数据库中是否存在相同用户名的管理员
        Admin existingUser = adminDao.findByName(admin.getName());
        if (existingUser != null) {
            throw new CustomException("ユーザー名は既に存在して別のをご利用ください");
        }

        // 如果密码为空，设置默认密码
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            admin.setPassword("123456");
        }

        // 执行插入操作
        adminDao.insertSelective(admin);
    }


    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }

    public Admin login(Admin admin) {
        // 1. 进行一些非空判断
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("ユーザー名は必ず入力してください");
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            throw new CustomException("パスワードは必ず入力してください");
        }
        // 2. 从数据库里面根据这个用户名和密码去查询对应的管理员信息，
        Admin user = adminDao.findByNameAndPassword(admin.getName(), admin.getPassword());
        if (user == null) {
            // 如果查出来没有，那说明输入的用户名或者密码有误，提示用户，不允许登录
            throw new CustomException("ユーザー名またはパスワードが間違っています");
        }
        // 如果查出来了有，那说明确实有这个管理员，而且输入的用户名和密码都对；
        // 生成jwt token给前端
        String token = JwtTokenUtils.getToken(user.getId().toString(), user.getPassword());
        user.setToken(token);
        return user;
    }

    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }


}


//    public void add(Admin admin) {
//        if (admin.getName() == null || admin.getName().isEmpty()) {
//            throw new CustomException("用户名不能为空");
//        }
//
//        Admin existingUser = adminDao.findByName(admin.getName());
//        if (existingUser != null) {
//            throw new CustomException("该用户名已存在，请更换用户名");
//        }
//
//        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
//            admin.setPassword("123456");
//        }
//
//        adminDao.insertSelective(admin);
//
//    }
//
//    public void update(Admin admin) {
//        adminDao.updateByPrimaryKeySelective(admin);
//    }
//
//    public void delete(Integer id) {
//        adminDao.deleteByPrimaryKey(id);
//    }
//
//    public Admin login(Admin admin) {
//        if (admin.getName() == null || admin.getName().isEmpty()) {
//            throw new CustomException("用户名不能为空");
//        }
//        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
//            throw new CustomException("密码不能为空");
//        }
//
//        Admin user = adminDao.findByNameAndPassword(admin.getName(), admin.getPassword());
//        if (user == null) {
//            throw new CustomException("用户名或密码输入错误");
//        }
//
//        return user;
//    }
//
//}
