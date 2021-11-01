package com.chige.constants;

/**
 * @author wangyc
 * @date 2021/9/19
 */
public enum EnumTest {

    FOLLOW(1,"关注"),
    CONTANCT(8,"通讯录");

    private Integer relationType;
    private String typeName;
    EnumTest(Integer relationType, String typeName) {
        this.relationType = relationType;
        this.typeName = typeName;
    }
    public Integer value() {
        return this.relationType;
    }

    public String getTypeName() {
        return this.typeName;
    }

}
