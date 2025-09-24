package com.chige;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PhoneUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author wangyc
 * @date 2023/7/20
 */
public class Test2 {

    public static void main(String[] args) throws InterruptedException {

//        System.out.println(PhoneUtil.isPhone("85366888898"));
//        System.out.println(PhoneUtil.isPhone("24500001289"));
//        System.out.println(PhoneUtil.isPhone("13672749791"));
//        System.out.println(System.currentTimeMillis());
//        long tradeNo = IdUtil.getSnowflake(1L).nextId();
//        TimeUnit.SECONDS.sleep(1);
//        long tradeNo2 = IdUtil.getSnowflake(1L).nextId();
//        System.out.println(tradeNo);
//        System.out.println(tradeNo2);
//        System.out.println(String.format("%s%s/wechat/%d%d", "http://abc.com/","pub/v1",1,2));

        String content = "12fa sdgg sg\n13fasd ggsg\n14fas dggsg\n15fas dggsg\n16fasd ggsg\n17fa sdggsg\n18fasd ggsg\n19 123 1\na";
        Test2 test2 = new Test2();
        int i = 0;
        int firstIndex = content.indexOf("\n");
        List<String> contentList = new ArrayList<>(100000);
        while(firstIndex != -1) {
            String cont = content.substring(i, firstIndex).trim();
            contentList.add(cont);
            i = firstIndex + 1;
            firstIndex = content.indexOf("\n", firstIndex + 1);
        }
        if (i < content.length()) {
            contentList.add(content.substring(i).trim());
        }
        contentList.forEach(System.out::println);

    }

    public static String readZipFileEntry(String zipFilePath, String entryName) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            ZipEntry entry = zipFile.getEntry(entryName);

            if (entry != null) {
                try (InputStream inputStream = zipFile.getInputStream(entry);
                     InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        contentBuilder.append(line).append("\n");
                    }
                }
            } else {
                throw new FileNotFoundException("Entry not found: " + entryName);
            }
        }
        return contentBuilder.toString();
    }


}
