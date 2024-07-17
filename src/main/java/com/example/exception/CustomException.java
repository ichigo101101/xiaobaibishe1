//package com.example.exception;
//
//public class CustomException extends RuntimeException {
//    private String msg;
//
//    public CustomException(String msg) {
//        this.msg = msg;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//}
package com.example.exception;

public class CustomException extends RuntimeException {
//    private String msg;
    private final String msg;

    public CustomException(String msg) {
        super(msg); // 调用父类 RuntimeException 的构造函数，并传递异常消息
        this.msg = msg; // 设置异常消息
    }

    public String getMsg() {
        return msg;
    }

//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
}
