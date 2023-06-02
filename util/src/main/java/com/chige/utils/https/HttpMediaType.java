package com.chige.utils.https;

public enum HttpMediaType {
    /**
     * application/json
     */
    JSON_UTF8("application/json; charset=utf-8", "json"),

    JSON("application/json", "json"),

    /**
     * application/x-www-form-urlencoded
     */
    FORM_URLENCODED_UTF8("application/x-www-form-urlencoded; charset=utf-8", "form"),
    /**
     * xml
     */
    XML("text/xml", "xml"),

    XML_UTF8("application/xml;charset=utf-8", "xml"),
    ;
    private final String type;
    private final String desc;

    HttpMediaType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}