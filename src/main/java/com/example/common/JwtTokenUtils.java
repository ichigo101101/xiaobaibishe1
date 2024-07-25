//package com.example.common;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.StrUtil;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.entity.Admin;
//import com.example.service.AdminService;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.Resource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import java.util.Date;
//import jakarta.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletRequest;
//
//
//@Component
//public class JwtTokenUtils {
//
//    private static AdminService staticAdminService;
//    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);
//
//    @Resource
//    private AdminService adminService;
//
//    @PostConstruct
//    public void setUserService() {
//        staticAdminService = adminService;
//    }
//
//    /**
//     * 生成token
//     */
//    public static String getToken(String adminId, String sign) {
//        return JWT.create().withAudience(adminId) // 将 user id 保存到 token 里面,作为载荷
//                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
//                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
//    }
//
//    /**
//     * 获取当前登录的用户信息
//     */
//    public static Admin getCurrentUser() {
//        String token = null;
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            token = request.getHeader("token");
//            if (StrUtil.isBlank(token)) {
//                token = request.getParameter("token");
//            }
//            if (StrUtil.isBlank(token)) {
//                log.error("获取当前登录的token失败， token: {}", token);
//                return null;
//            }
//            // 解析token，获取用户的id
//            String adminId = JWT.decode(token).getAudience().get(0);
//            return staticAdminService.findById(Integer.valueOf(adminId));
//        } catch (Exception e) {
//            log.error("获取当前登录的管理员信息失败, token={}", token,  e);
//            return null;
//        }
//    }
//}

package com.example.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtils {

    private static AdminService staticAdminService;
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private AdminService adminService;

    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
    }

    /**
     * 生成token
     */
    public static String getToken(String adminId, String sign) {
        return JWT.create().withAudience(adminId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     */
    public static Admin getCurrentUser() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = getTokenFromRequest(request);
            if (StrUtil.isBlank(token)) {
                log.error("現在のログイントークンの取得に失敗しました。トークンが空です。");
                //获取当前登录的token失败， token为空
                throw new CustomException("有効なトークンが見つかりませんでしたので、再ログインしてください。");
                //未检测到有效的token，请重新登录
            }
            // 解析token，获取用户的id
            String adminId = JWT.decode(token).getAudience().get(0);
            return staticAdminService.findById(Integer.valueOf(adminId));
        } catch (CustomException ce) {
            throw ce; // 已经是 CustomException 类型的异常，直接抛出
        } catch (Exception e) {
            log.error("現在の管理者情報の取得に失敗しました。トークン={}。", token, e);
            //获取当前登录的管理员信息失败, token={}
            throw new CustomException("ユーザ情報を解析できませんでしたので、再ログインしてください");
            //从token中未解析到用户信息，请重新登录
        }
    }

    private static String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
