package com.example.common;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从HTTP请求的Header中获取Token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 如果Header中没有Token，再尝试从参数中获取
            token = request.getParameter("token");
        }

        // 2. Token为空时，抛出异常
        if (StrUtil.isBlank(token)) {
            throw new CustomException("无Token，请重新登录");
        }

        // 3. 获取Token中的用户ID并查询数据库
        String userId;
        Admin admin;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            admin = adminService.findById(Integer.parseInt(userId));
        } catch (Exception e) {
            String errorMsg = "Token验证失败，请重新登录";
            log.error("{}，Token：{}", errorMsg, token, e);
            throw new CustomException(errorMsg);
        }

        // 4. 用户不存在时，抛出异常
        if (admin == null) {
            throw new CustomException("用户不存在，请重新登录");
        }

        // 5. 验证Token
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token); // 验证Token
        } catch (JWTVerificationException e) {
            String errorMsg = "Token验证失败，请重新登录";
            log.error("{}，Token：{}", errorMsg, token, e);
            throw new CustomException(errorMsg);
        }

        log.info("Token验证成功，允许放行");
        return true;
    }
}
