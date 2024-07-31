//package com.example.exception;
//
//import com.example.common.Result;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice(basePackages="com.example.controller")
//public class GlobalExceptionHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    //统一异常处理@ExceptionHandler,主要用于Exception
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result error(HttpServletRequest request, Exception e){
//        log.error("例外情報：",e);
//        return Result.error("例外情報");
//    }
//
//    @ExceptionHandler(CustomException.class)
//    @ResponseBody
//    public Result customError(HttpServletRequest request, CustomException e){
//        return Result.error(e.getMsg());
//    }
//}

package com.example.exception;

import com.example.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice(basePackages = "com.example.controller")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(HttpServletRequest request, Exception e) {
        log.error("全局异常处理：", e);
        return Result.error("系统异常：" + e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result handleCustomException(HttpServletRequest request, CustomException e) {
        log.error("自定义异常处理：", e);
        return Result.error(e.getMessage());
    }
}
