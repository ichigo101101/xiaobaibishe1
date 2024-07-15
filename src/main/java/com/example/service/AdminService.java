package com.example.service;
import com.example.dao.AdminDao;
import com.example.entity.Admin;
import com.example.entity.Params;
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
        List<Admin> list  = adminDao.findBySearch(params);
        return PageInfo.of(list);
    }
}
