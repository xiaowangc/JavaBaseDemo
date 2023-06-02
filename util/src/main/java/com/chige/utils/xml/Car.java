package com.chige.utils.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author wangyc
 * @date 2023/4/19
 */
@XmlRootElement
public class Car {

    private String brand;
    private String price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
