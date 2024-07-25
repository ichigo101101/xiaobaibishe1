package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.JwtTokenUtils;
import com.example.dao.AdminDao;
import com.example.dao.AuditDao;
import com.example.entity.Admin;
import com.example.entity.Audit;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AuditService {

    @Resource
    private AuditDao auditDao;
    @Resource
    private AdminDao adminDao;


    public void add(Audit audit) {
        auditDao.insertSelective(audit);
    }

    public void update(Audit audit) {
        auditDao.updateByPrimaryKeySelective(audit);
    }

    public PageInfo<Audit> findBySearch(Params params) {
        Admin user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException("トークンからユーザ情報が取得できませんでしたので、再ログインしてください。");
            //从token中未解析到用户信息，请重新登录
        }
        if ("ROLE_STUDENT".equals(user.getRole())) {
            params.setUserId(user.getId());
        }
        // 开启分页查询，下一次查询就会分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Audit> list = auditDao.findBySearch(params);
//        for (Audit audit : list) {
//            if (ObjectUtil.isNotEmpty(audit.getUserId())) {
//                Admin user = adminDao.selectByPrimaryKey(audit.getUserId());
//                if (ObjectUtil.isNotEmpty(user)) {
//                    audit.setUserName(user.getName());
//                }
//            }
//        }
        return PageInfo.of(list);
    }

    public void delete(Integer id) {
        auditDao.deleteByPrimaryKey(id);
    }

}
