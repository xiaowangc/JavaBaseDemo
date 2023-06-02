package com.chige.utils.xml;


import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangyc
 * @date 2023/4/19
 */
@XmlRootElement(name = "ROOT")
@JsonTypeName(value = "endEvent")
public class User implements Serializable {

    private static final long serialVersionUID = 5448675689289238736L;
    @XStreamAlias(value = "USER_NAME")
    private String userName;
    private String userAge;
    private String idNo;
    private List<String> list;
    private Child child;

    @XmlElement(name = "USER_NM")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @XmlElement(name = "USER_AGE")
    public String getUserAge() {
        return userAge;
    }
    @XmlElement(name = "ID_NO")
    public String getIdNo() {
        return idNo;
    }
    @XmlElementWrapper(name = "LISTS")
    @XmlElement(name = "LIST")
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
    @XmlElement(name = "CHILD_TEST")
    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userAge='" + userAge + '\'' +
                ", idNo='" + idNo + '\'' +
                '}';
    }
}
