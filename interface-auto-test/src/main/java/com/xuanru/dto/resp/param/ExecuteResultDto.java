package com.xuanru.dto.resp.param;

import com.xuanru.dto.resp.BaseRespDto;

/**
 * Created by Liaoxf on 2016-09-30 15:33.
 */
public class ExecuteResultDto extends BaseRespDto {
    private String url;

    private String params;

    private String request_method;

    private String expect_result;

    private String pass_flag;

    private String status_code;

    private String error_msg;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRequest_method() {
        return request_method;
    }

    public void setRequest_method(String request_method) {
        this.request_method = request_method;
    }

    public String getExpect_result() {
        return expect_result;
    }

    public void setExpect_result(String expect_result) {
        this.expect_result = expect_result;
    }

    public String getPass_flag() {
        return pass_flag;
    }

    public void setPass_flag(String pass_flag) {
        this.pass_flag = pass_flag;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
