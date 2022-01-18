package com.chige.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangyc
 * @date 2021/12/9
 */
public class ExcelUtils {

    public static void main(String[] args) throws IOException {
//查询得到数据
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        String storeExcelArea = "D:/tmp/bill/";
        List<Person> billResponses = Arrays.asList(new Person("aaf", "啊啊"),new Person("bb","版本"));
        FileUtils.forceMkdir(new File(storeExcelArea));
        //导出excel
        String sheetName = String.valueOf(System.currentTimeMillis());
        String fileName = storeExcelArea + sheetName + ".xls";
        String[] columnNames = {"A","B","C"};
        exportExcelUtil.exportExcel(sheetName, columnNames, billResponses, new FileOutputStream(fileName), ExportExcelUtil.EXCEL_FILE_2003);

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private String count;
        private String number;
    }
}
