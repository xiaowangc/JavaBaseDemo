package com.chige.util.encryption;

import cn.hutool.crypto.digest.DigestAlgorithm;
import com.sensorsdata.webhook.entry.SfWebhookEntry;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/** hash 加密算法
 * 通过Hash算法对目标信息生成一段【特定长度】的【唯一的】Hash值，却不能通过这个Hash值重新获得目标信息
 * 优点：不可逆、易计算、特征化
 * 缺点：可能存在散列冲突
 * 使用场景：文件或字符串一致性校验、数字签名、鉴权协议
 * 常见算法：MD2、MD4、MD5、HAVAL、SHA、SHA-1、HMAC、HMAC-MD5、HMAC-SHA1
 *
 * @author wangyc
 * @date 2021/12/12
 */
public class HashAlgorithm {
    /**
     *  MD5算法的使用
     * @param text 明文
     * @return 经过md5算法生成的唯一的hash值
     */
    public static String md5(String text) {
        // 获得MD5摘要算法的 MessageDigest 对象
        try {
            MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = DigestUtils.md5(text);
        String s = String.valueOf(bytes);
        System.out.println("s = " + s);
        return "4";
    }

    /**
     *  验证数据 - 签名
     */
    public static void hmac(String secretToken, String requestBody) {
        String signature = HmacUtils.hmacSha1Hex(secretToken, requestBody);
        System.out.println("请求头签名：" + signature);

    }
    public void handleWebhookEntries(List<SfWebhookEntry> entries) {
        for (SfWebhookEntry entry : entries) {
            System.out.println("触发的用户设备 ID: " + entry.getRequest().getUserProfile().getFirstId());
            System.out.println("触发的用户登录 ID: " + entry.getRequest().getUserProfile().getSecondId());
            Map<String, String> params = entry.getRequest().getParams();
            System.out.println("用户的年龄：" + params.get("age"));
            System.out.println("用户的等级：" + params.get("level"));

            entry.getResponse().succeeded();
        }
    }

    public static void main(String[] args) {
//        md5("支付中心");
        hmac("abc","123");
    }


}
