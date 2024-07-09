package com.example.dao;
import com.example.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


//表示是持久层的
//@Repository是Spring框架中用于标识数据访问层组件的一个非常有用的注解，
// 它不仅有助于Spring管理DAO层的组件，还提供了异常转换的便利，使得开发者可以更加专注于业务逻辑的实现。
@Repository
public interface UserDao extends Mapper<User> {

    // 1. 基于注解的方式
//    @Select("select * from user")

    List<User> getUser();
}