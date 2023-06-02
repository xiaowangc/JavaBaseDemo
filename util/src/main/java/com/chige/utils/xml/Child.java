package com.chige.utils.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author wangyc
 * @date 2023/4/19
 */
@XmlRootElement(name = "ROOT")
public class Child {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
