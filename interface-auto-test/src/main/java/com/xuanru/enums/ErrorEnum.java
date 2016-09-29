package com.xuanru.enums;

/**
 * Created by Liaoxf on 2016-09-29 15:58.
 */
public enum ErrorEnum {
    ERROR_10001(-10001, "系统异常"),
    ERROR_10002(-10002, "非法参数值"),
    ERROR_10003(-10003, "无访问权限"),
    ERROR_10004(-10004, "非法请求"),
    ERROR_10005(-10005, "请求过于频繁"),

    ERROR_20001(-20001, "Apache HttpResponse StatusLine返回null"),
    ERROR_20002(-20002, "URL非法"),
    ;

    private int errorCode;

    private String errorMsg;

    ErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
