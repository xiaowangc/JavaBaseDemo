package com.chige.utils.json;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * @author wangyc
 * @date 2023/4/19
 */
@JsonTypeName(value = "userInfo")
public class User {

    private String userName;
    private String userAge;
    private String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
}
