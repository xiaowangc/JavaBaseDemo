package com.chige.chapter1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** 使用建造者模式创建对象
 * @author wangyc
 * @date 2022/3/26
 */
@Getter
@AllArgsConstructor
@ToString
public class House {
    private String floor; //必选参数
    private String watchTV; //必选参数
    private String safa = "沙发1"; // 可选参数
    private String computer = "MacBook Pro 14" ; //可选参数

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String floor;
        private String watchTV;
        private String safa;
        private String computer;
        private Builder() {}
        public Builder floor(String f) {
            this.floor = f;
            return this;
        }
        public Builder watchTV(String t) {
            this.watchTV = t;
            return this;
        }

        public Builder safa(String safa) {
            this.safa = safa;
            return this;
        }
        public Builder computer(String computer) {
            this.computer = computer;
            return this;
        }
        public House build() {
            return new House(this);
        }
    }
    private House(Builder builder) {
        this.floor = builder.floor;
        this.watchTV = builder.watchTV;
        this.safa = builder.safa;
        this.computer = builder.computer;
    }



    public static void main(String[] args) {
        House house1 = House.builder().build();
        System.out.println(">>> 房子1号：" + house1);
        House house2 = House.builder()
                .safa("沙发2")
                .computer("mac air")
                .build();
        System.out.println(">>> 房子2号：" + house2);
    }
}
