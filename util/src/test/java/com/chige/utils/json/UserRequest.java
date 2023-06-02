package com.chige.utils.json;


import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * @author wangyc
 * @date 2023/4/19
 */

public class UserRequest {
    private String userType;
    private String userIdType;
    @JsonUnwrapped
    private User user;
    @JsonUnwrapped
    private Company company;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserIdType() {
        return userIdType;
    }

    public void setUserIdType(String userIdType) {
        this.userIdType = userIdType;
    }
}
