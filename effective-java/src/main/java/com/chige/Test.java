package com.chige;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangyc
 * @date 2022/11/24
 */
public class Test {

    public static boolean isUpdate(String originLinkType, String newLinkType) {
        Set<String> originLinkTypeSet = StringUtils.isBlank(originLinkType) ? new HashSet<>(4) : Arrays.stream(originLinkType.split(",")).collect(Collectors.toSet());
        return StringUtils.isNotBlank(newLinkType) && !originLinkTypeSet.contains(newLinkType);
    }

    static String addNewValueSet(String originVal, String newVal) {
        if (StringUtils.isBlank(originVal) && Objects.isNull(newVal)) {
            return null;
        }
        if (StringUtils.isBlank(originVal)) {
            return newVal;
        }
        if (StringUtils.isBlank(newVal)) {
            return originVal;
        }
        Set<String> originLinkTypeSet = Arrays.stream(originVal.split(",")).collect(Collectors.toSet());
        originLinkTypeSet.add(newVal);
        return StringUtils.join(originLinkTypeSet, ",");
    }

    public static void main(String[] args) {
        Father father = new Father("粑粑");
        List<Son> sonList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Son son = new Son("姓名" + i);
            sonList.add(son);
        }
        father.getSonList().addAll(sonList);
        for (Son son : sonList) {
            son.setFather(father);
        }
        System.out.println(father);
    }

    static class Father {
        private String name;
        List<Son> sonList = new ArrayList<>();

        public Father() {
        }

        public Father(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Father{" +
                    "name='" + name + '\'' +
                    ", sonList=" + sonList +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Son> getSonList() {
            return sonList;
        }

        public void setSonList(List<Son> sonList) {
            this.sonList = sonList;
        }


    }

    static class Son {
        private String name;
        Father father;

        public Son() {
        }

        public Son(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Son{" +
                    "name='" + name + '\'' +
                    ", father=" + father +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Father getFather() {
            return father;
        }

        public void setFather(Father father) {
            this.father = father;
        }
    }
}
