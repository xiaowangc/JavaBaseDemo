package com.chige.framework.error;


/** http请求场景下异常
 * @author wangyc
 * @date 2023/4/21
 */
public class HttpException extends ApplicationException {

    public HttpException(String errorMsg) {
        super(errorMsg);
    }
}
