//package com.example.dao;
//import com.example.entity.Admin;
//import com.example.entity.Params;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.stereotype.Repository;
//import tk.mybatis.mapper.common.Mapper;
//import tk.mybatis.spring.annotation.MapperScan;
////import org.mybatis.spring.annotation.MapperScans;
//
//import java.util.List;
//
//
////表示是持久层的
////@Repository是Spring框架中用于标识数据访问层组件的一个非常有用的注解，
//// 它不仅有助于Spring管理DAO层的组件，还提供了异常转换的便利，使得开发者可以更加专注于业务逻辑的实现。
//@Repository
//@MapperScan //自己加的
//public interface AdminDao extends Mapper<Admin> {
//
//    // 1. 基于注解的方式
////    @Select("select * from user")
//
//    List<Admin> getUser();
//
//    List<Admin> findBySearch(@Param("params") Params params);
//
//
//    @Select("select * from admin where name = #{name} limit 1")
//    Admin findByName(@Param("name") String name);
//
//    @Select("select * from admin where name = #{name} and password = #{password} limit 1")
//    Admin findByNameAndPassword(@Param("name") String name, @Param("password") String password);
//}

package com.example.dao;

import com.example.entity.Admin;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminDao extends Mapper<Admin> {

    List<Admin> findBySearch(@Param("params") Params params);

    @Select("select * from admin where name = #{name} limit 1")
    Admin findByName(@Param("name") String name);

    @Select("select * from admin where name = #{name} and password = #{password} limit 1")
    Admin findByNameAndPassword(@Param("name") String name, @Param("password") String password);
}
