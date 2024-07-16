package com.example;

//import org.mybatis.spring.annotation.MapperScan;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //表明是项目启动类
@MapperScan("com.example.dao")
public class Demo101Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo101Application.class, args);
    }

}
