package com.chige.utils.json;

import com.chige.utils.https.HttpMediaType;
import com.chige.utils.https.HttpsUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author wangyc
 * @Description TODO
 * @Date 2024/11/26 14:24
 */
public class HttpClientTest {


    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientTest test = new HttpClientTest();
        Map<String, String> params = new HashMap<>();
        String url = "http://snsapi.91160.com/getUserFunctionVersionInfo";
        params.put("userId", "220174042");
        params.put("versionCode", "100018");
        AtomicReference<Integer> oldNum = new AtomicReference<>(0);
        AtomicReference<Integer> newNum = new AtomicReference<>(0);
        int allNum = 100;
        for (int i = 0; i < allNum; i++) {

            params.put("cityId", "5");
            params.put("deviceId", UUID.randomUUID().toString());
            String data = JsonUtils.toJSONString(params);

            Response response = null;
            try {
                response = HttpsUtils.doPost(url, data, HttpMediaType.JSON, Response.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String version = response.getData().getVersion();
            if (Objects.equals("old", version)) {
                oldNum.getAndSet(oldNum.get() + 1);
            }else if (Objects.equals("new", version)) {
                newNum.getAndSet(newNum.get() + 1);
            }
        }

        System.out.println("总次数：" + allNum + "，老版本次数：" + oldNum + "，占比：" + (oldNum.get() * 1.0 / allNum) * 100 + "%" + "，新版本次数：" + newNum + "，占比：" + (newNum.get() * 1.0 / allNum) * 100 + "%");
    }



    static class Response {
        private Integer code;
        private Result data;
        private String msg;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Result getData() {
            return data;
        }

        public void setData(Result data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


    static class Result {
        private String version;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

}
