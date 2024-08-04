//package com.example.common;
//
//import java.awt.Font;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import com.wf.captcha.base.Captcha;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class CaptchaUtilAdapter {
//
//    private static final Logger log = LoggerFactory.getLogger(CaptchaUtilAdapter.class);
//    private static final String CAPTCHA_UTIL_CLASS_NAME = "com.wf.captcha.utils.CaptchaUtil";
//    private static final String OUT_METHOD_NAME = "out";
//    private static final String CLEAR_METHOD_NAME = "clear";
//
//    public static void out(int width, int height, int len, Font font, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            Class<?> captchaUtilClass = Class.forName(CAPTCHA_UTIL_CLASS_NAME);
//            Method outMethod;
//
//            if (font != null) {
//                try {
//                    // 尝试获取带 Font 参数的方法
//                    outMethod = captchaUtilClass.getMethod(OUT_METHOD_NAME, int.class, int.class, int.class, Font.class, HttpServletRequest.class, HttpServletResponse.class);
//                } catch (NoSuchMethodException e) {
//                    // 如果失败，尝试不带 Font 参数的方法
//                    outMethod = captchaUtilClass.getMethod(OUT_METHOD_NAME, int.class, int.class, int.class, HttpServletRequest.class, HttpServletResponse.class);
//                }
//                outMethod.invoke(null, width, height, len, font, request, response);
//            } else {
//                outMethod = captchaUtilClass.getMethod(OUT_METHOD_NAME, int.class, int.class, int.class, HttpServletRequest.class, HttpServletResponse.class);
//                outMethod.invoke(null, width, height, len, request, response);
//            }
//        } catch (Exception e) {
//            log.error("Failed to invoke CaptchaUtil out method", e);
//            throw new RuntimeException("Failed to invoke CaptchaUtil out method", e);
//        }
//    }
//
//    public static void out(Captcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            Class<?> captchaUtilClass = Class.forName(CAPTCHA_UTIL_CLASS_NAME);
//            Method outMethod = captchaUtilClass.getMethod(OUT_METHOD_NAME, Captcha.class, HttpServletRequest.class, HttpServletResponse.class);
//            outMethod.invoke(null, captcha, request, response);
//        } catch (Exception e) {
//            log.error("Failed to invoke CaptchaUtil out method", e);
//            throw new RuntimeException("Failed to invoke CaptchaUtil out method", e);
//        }
//    }
//
//    public static void clear(HttpServletRequest request) {
//        try {
//            Class<?> captchaUtilClass = Class.forName(CAPTCHA_UTIL_CLASS_NAME);
//            Method clearMethod = captchaUtilClass.getMethod(CLEAR_METHOD_NAME, HttpServletRequest.class);
//            clearMethod.invoke(null, request);
//        } catch (Exception e) {
//            log.error("Failed to invoke CaptchaUtil clear method", e);
//            throw new RuntimeException("Failed to invoke CaptchaUtil clear method", e);
//        }
//    }
//}
package com.example.common;
import java.awt.Font;
import java.io.IOException;
import java.lang.reflect.Method;
import com.wf.captcha.base.Captcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaUtilAdapter {

    private static final Logger log = LoggerFactory.getLogger(CaptchaUtilAdapter.class);
    private static final String CAPTCHA_UTIL_CLASS_NAME = "com.wf.captcha.utils.CaptchaUtil";
    private static final String OUT_METHOD_NAME = "out";
    private static final String CLEAR_METHOD_NAME = "clear";

    /**
     * 输出验证码（适配器方法，带有字体）
     */
    public static void out(int width, int height, int len, Font font, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class<?> captchaUtilClass = Class.forName(CAPTCHA_UTIL_CLASS_NAME);
            Method outMethod;

            if (font != null) {
                // 尝试获取带 Font 参数的方法
                outMethod = captchaUtilClass.getMethod(OUT_METHOD_NAME, int.class, int.class, int.class, Font.class, HttpServletRequest.class, HttpServletResponse.class);
                outMethod.invoke(null, width, height, len, font, request, response);
            } else {
                // 获取不带 Font 参数的方法
                outMethod = captchaUtilClass.getMethod(OUT_METHOD_NAME, int.class, int.class, int.class, HttpServletRequest.class, HttpServletResponse.class);
                outMethod.invoke(null, width, height, len, request, response);
            }
        } catch (ClassNotFoundException e) {
            log.error("CaptchaUtil class not found", e);
            throw new RuntimeException("CaptchaUtil class not found", e);
        } catch (NoSuchMethodException e) {
            log.error("CaptchaUtil out method not found", e);
            throw new RuntimeException("CaptchaUtil out method not found", e);
        } catch (Exception e) {
            log.error("Failed to invoke CaptchaUtil out method", e);
            throw new RuntimeException("Failed to invoke CaptchaUtil out method", e);
        }
    }

    /**
     * 输出验证码（适配器方法，使用 Captcha 对象）
     */
    public static void out(Captcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class<?> captchaUtilClass = Class.forName(CAPTCHA_UTIL_CLASS_NAME);
            Method outMethod = captchaUtilClass.getMethod(OUT_METHOD_NAME, Captcha.class, HttpServletRequest.class, HttpServletResponse.class);
            outMethod.invoke(null, captcha, request, response);
        } catch (ClassNotFoundException e) {
            log.error("CaptchaUtil class not found", e);
            throw new RuntimeException("CaptchaUtil class not found", e);
        } catch (NoSuchMethodException e) {
            log.error("CaptchaUtil out method with Captcha parameter not found", e);
            throw new RuntimeException("CaptchaUtil out method with Captcha parameter not found", e);
        } catch (Exception e) {
            log.error("Failed to invoke CaptchaUtil out method", e);
            throw new RuntimeException("Failed to invoke CaptchaUtil out method", e);
        }
    }

    /**
     * 清除验证码（适配器方法）
     */
    public static void clear(HttpServletRequest request) {
        try {
            Class<?> captchaUtilClass = Class.forName(CAPTCHA_UTIL_CLASS_NAME);
            Method clearMethod = captchaUtilClass.getMethod(CLEAR_METHOD_NAME, HttpServletRequest.class);
            clearMethod.invoke(null, request);
        } catch (ClassNotFoundException e) {
            log.error("CaptchaUtil class not found", e);
            throw new RuntimeException("CaptchaUtil class not found", e);
        } catch (NoSuchMethodException e) {
            log.error("CaptchaUtil clear method not found", e);
            throw new RuntimeException("CaptchaUtil clear method not found", e);
        } catch (Exception e) {
            log.error("Failed to invoke CaptchaUtil clear method", e);
            throw new RuntimeException("Failed to invoke CaptchaUtil clear method", e);
        }
    }
}

