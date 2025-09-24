package com.chige.security.ecc;



/**
 * HTTP方法常量
 */
public enum HttpMethod {

    POST_BODY("POST", "application/octet-stream; charset=utf-8", "application/json; charset=utf-8"),
    ;

    private final String value;
    private final String requestContentType;
    private final String acceptContentType;

    HttpMethod(String value, String requestContentType, String acceptContentType) {
        this.value = value;
        this.requestContentType = requestContentType;
        this.acceptContentType = acceptContentType;
    }

    public String getValue() {
        return value;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public String getAcceptContentType() {
        return acceptContentType;
    }

}
