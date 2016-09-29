package com.xuanru.dto.exception;

import com.xuanru.enums.ErrorEnum;

/**
 * Created by Liaoxf on 2016-09-29 18:12.
 */
public class SystemError {

    private int errorCode;

    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public SystemError() {
    }

    public SystemError(ErrorEnum errorEnum) {
        errorCode = errorEnum.getErrorCode();
        errorMsg = errorEnum.getErrorMsg();
    }
}
