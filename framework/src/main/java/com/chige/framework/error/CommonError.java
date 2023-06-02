package com.chige.framework.error;

/**
 * @author wangyc
 * @date 2023/4/21
 */
public class CommonError {
    private static final long serialVersionUID = 1L;
    private Integer errorCode;
    private String errorDesc;

    public CommonError(Integer errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public CommonError message(String message) {
        return new CommonError(this.errorCode, this.errorDesc + "[" + message + "]");
    }
}
