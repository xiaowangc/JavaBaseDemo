package com.chige.utils.file;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.Collectors;

/**
 * @Author wangyc
 * @Description TODO
 * @Date 2024/12/30 15:58
 */
public class FileReadUtils {

    public static void main(String[] args) {

        FileReadUtils fileReadUtils = new FileReadUtils();
        fileReadUtils.readFile_update();
    }

    private void readFile() {
        File file = new File("/Users/yongchiwang/work/nykj/temp/sms241230-2.txt");
        File file2 = new File("/Users/yongchiwang/work/nykj/temp/sms241230-1.txt");
        List<String> allList = new ArrayList<>();
        List<String> stringList = FileUtil.readLines(file, "UTF-8");
        List<String> stringList2 = FileUtil.readLines(file2, "UTF-8");
        allList.addAll(stringList);
        allList.addAll(stringList2);

        System.out.println("机构数量为：" + allList.size());
        List<String> unitIds = allList.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后机构数量为：" + unitIds.size());
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO sms_black(BLACK_TYPE, BLACK_VALUE, BLACK_SMS_TYPES, BLACK_TYPE_CODE) VALUES ");
        int count = 0;
        int num = 0;
        for (String unitId : unitIds) {
            stringBuilder.append("(")
                    .append("'UNIT_ID',")
                    .append("'").append(unitId).append("',")
                    .append("'102','order,get_num_remind,pay,inspection_project_report,today_order,today_pay,cancel_yuyue,refund_suc,doc_not_approve_sch_remind_user,yuyue_cancel,later,stop,visit_notify,addorder_notify,change,replace,already_got_number,pay_failed,pay_notify,refund_pass_notify,expired_notify,hos_diversion,b2c_paid_and_yuyue_ing,comment_notify,stop_diagnose,order_fail,yuyue_confirm_notify,e_prescription_expire_remind,refund_failed_notify'),");
            System.out.println(unitId);
            count++;
        }

        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(";");
        String path = "/Users/yongchiwang/work/nykj/temp/sms241230_sql_" + num + ".sql";
        FileUtil.touch(path);
        FileUtil.writeUtf8String(stringBuilder.toString(), path);
    }

    private void readFile_update() {
        File file = new File("/Users/yongchiwang/work/nykj/temp/sms250102-1.txt");
        List<String> allList = new ArrayList<>();
        List<String> stringList = FileUtil.readLines(file, "UTF-8");
        allList.addAll(stringList);

        System.out.println("机构数量为：" + allList.size());
        List<String> unitIds = allList.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后机构数量为：" + unitIds.size());
        StringBuilder stringBuilder = new StringBuilder("update sms_black set black_type_code = 'order,get_num_remind,pay,inspection_project_report,today_order,today_pay,cancel_yuyue,refund_suc,doc_not_approve_sch_remind_user,yuyue_cancel,later,stop,visit_notify,addorder_notify,change,replace,already_got_number,pay_failed,pay_notify,refund_pass_notify,expired_notify,hos_diversion,b2c_paid_and_yuyue_ing,comment_notify,stop_diagnose,order_fail,yuyue_confirm_notify,e_prescription_expire_remind,refund_failed_notify'  where black_type = 'UNIT_ID' and black_value in(");
        int count = 0;
        int num = 0;
        for (String unitId : unitIds) {
            stringBuilder.append("'").append(unitId).append("'");
            stringBuilder.append(",");
            count++;
        }

        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append(");");
        String path = "/Users/yongchiwang/work/nykj/temp/sms250102_sql_update.sql";
        FileUtil.touch(path);
        FileUtil.writeUtf8String(stringBuilder.toString(), path);
    }

}
