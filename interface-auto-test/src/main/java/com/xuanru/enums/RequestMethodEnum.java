package com.xuanru.enums;

/**
 * Created by Liaoxf on 2016-09-29 15:58.
 */
public enum RequestMethodEnum {
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    ;

    private String desc;

    RequestMethodEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
