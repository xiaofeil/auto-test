package com.xuanru.dto;

import com.xuanru.dto.exception.SystemError;
import com.xuanru.enums.ErrorEnum;

import java.io.Serializable;

/**
 * http响应结果对象
 * Created by Liaoxf on 2016-09-29 15:40.
 */
public class HttpNetResponse implements Serializable {
    private static final long serialVersionUID = -7384431116511173048L;

    private int statusCode; // http结果状态码

    private String respContent;// http响应结果

    private SystemError error;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRespContent() {
        return respContent;
    }

    public void setRespContent(String respContent) {
        this.respContent = respContent;
    }

    public SystemError getError() {
        return error;
    }

    public void setError(SystemError error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }
}
