package com.chige.chapter1;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/** 使用@Builder注解创建对象
 * @author wangyc
 * @date 2022/3/26
 */
@ToString
@Getter
@Builder
public class HouseBuilder {
    private String floor;
    private String tv;
    private String safa;
    private String computer;

}
