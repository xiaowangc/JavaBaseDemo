package com.chige.test;

import com.chige.utils.json.JacksonUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangyc
 * @date 2023/9/27
 */
public class ListTest {

    public static void main(String[] args) {
//        Set<String> set = new HashSet<>();
//        List<Person> aList = new ArrayList<>();
//        aList = aList.stream().filter(person -> !set.contains(person.getUserId())).collect(Collectors.toList());
//        List<String> collect = aList.stream().map(Person::getUserId).collect(Collectors.toList());
//        System.out.println(collect.size());
        String a = "1,2,3";
        String b = "4,5,6";
        String c = "";
        System.out.println(StringUtils.joinWith(",", a, b));
        System.out.println(StringUtils.joinWith(",", c, b));
        System.out.println(StringUtils.joinWith(",", a, c));

    }

    static class Person {
        private String userId;
        private BigDecimal amount;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
