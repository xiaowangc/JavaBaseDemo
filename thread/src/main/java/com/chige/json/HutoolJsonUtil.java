package com.chige.json;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/** hutool - json工具
 * @author wangyc
 * @date 2022/3/19
 */
public class HutoolJsonUtil {

    public static void main(String[] args) {
        // 解析字符串Json
        String str = "{'a':1,'b':\"2\"}";
        JSON jsonObj = new JSONObject(str);
        User user = jsonObj.toBean(User.class); //JSON字符串转成对象
        TreeMap treeMap = jsonObj.toBean(TreeMap.class); // JSON字符串转成Map

        System.out.println("User=" + user);

        List<User> userList = new ArrayList<>();
        userList.add(new User("1","11"));
        userList.add(new User("2","22"));
        // json数组对象存储列表对象
        JSON jsonArray = new JSONArray(userList);
        System.out.println(jsonArray.toJSONString(1));
        // json数组对象转成 json字符串对象
        String userListJson = jsonArray.toStringPretty();
        List list = new JSONArray(userListJson).toBean(ArrayList.class);
        for (Object o : list) {
            User user1 = new JSONObject(o).toBean(User.class);
            System.out.println(user1);
        }
        // 解析json数组字符串对象
        List<User> users = new JSONArray(userListJson).toList(User.class);


    }
    @AllArgsConstructor
    @ToString
    @Data
    public static class User {
        private String a;
        private String b;
    }

}
