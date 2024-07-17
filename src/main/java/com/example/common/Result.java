//package com.example.common;
//
////封装统一的返回数据结构
//public class Result {
//
//    private static final String SUCCESS = "0";
//    private static final String ERROR = "-1";
//
//    private String code;
//    private String msg;
//    private Object data;
//
//    public static Result success() {
//        Result result = new Result();
//        result.setCode(SUCCESS);
//        return result;
//    }
//
//    public static Result success(Object data) {
//        Result result = new Result();
//        result.setCode(SUCCESS);
//        result.setData(data);
//        return result;
//    }
//
//    public static Result error(String msg) {
//        Result result = new Result();
//        result.setCode(ERROR);
//        result.setData(msg);
//        return result;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//}
//

package com.example.common;

public class Result {

    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";

    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(SUCCESS, null, null);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS, null, data);
    }

    public static Result error(String errorMsg) {
        return new Result(ERROR, errorMsg, null);
    }

    private Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}

