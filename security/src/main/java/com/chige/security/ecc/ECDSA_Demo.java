package com.chige.security.ecc;


import com.alibaba.fastjson.JSON;

import java.net.URLEncoder;
import java.security.*;
import java.util.*;

import static com.chige.security.ecc.ECKeyPair_Demo.*;

/**
 * @Author wangyc
 * @Description ECC体系下的数字签名算法ECDSA
 * @Date 2025/9/1 00:20
 */
public class ECDSA_Demo {


    /**
     * 使用ECC私钥签名数据
     */
    public static String signWithECC(String data, String privateKeyB64) throws Exception {
        try {
            // 恢复私钥
            PrivateKey privateKey = restorePrivateKey(privateKeyB64);

            // 创建签名实例，使用 ECDSA 算法
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privateKey);

            // 对数据进行签名
            signature.update(data.getBytes("UTF-8"));
            byte[] signedData = signature.sign();

            // 将签名结果转换为 Base64 编码返回
            return Base64.getEncoder().encodeToString(signedData);
        } catch (Exception e) {
            throw new Exception("ECC签名失败: " + e.getMessage(), e);
        }
    }

    /**
     * 使用ECC公钥验证签名
     */
    public static boolean verifyWithECC(String data, String signatureB64, String publicKeyB64) throws Exception {
        try {
            // 恢复公钥
            PublicKey publicKey = restorePublicKey(publicKeyB64);
            // 创建签名实例，使用 ECDSA 算法
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initVerify(publicKey);

            // 更新要验证的数据
            signature.update(data.getBytes("UTF-8"));

            // 解码签名数据
            byte[] signatureData = Base64.getDecoder().decode(signatureB64);

            // 验证签名
            return signature.verify(signatureData);
        } catch (Exception e) {
            throw new Exception("ECC验签失败: " + e.getMessage(), e);
        }
    }


    /**
     * 使用TreeMap自动排序
     * TreeMap的键会按自然顺序（ASCII码升序）排列
     */
    public static String sortParamsWithTreeMap(Map<String, Object> params) {
        // 创建TreeMap，自动按key的ASCII码升序排序
        Map<String, Object> sortedMap = new TreeMap<>(params);

        // 拼接排序后的参数（key=value&key=value）
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            Object value = Objects.isNull(entry.getValue()) ? "" : entry.getValue(); // 处理null值
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataJson = new HashMap<>();
        dataJson.put("device_private_key","xxxsdfsafasfsd");
        dataJson.put("orderId","123");
        dataJson.put("num",123);
        String dataParam = sortParamsWithTreeMap(dataJson);
        map.put("code", 1);
        map.put("data", URLEncoder.encode(dataParam, "UTF-8"));
        map.put("timestamp", System.currentTimeMillis() / 1000);
        map.put("nonce_str", generateNonce(32));

        KeyPair keyPair = generateECKeyPair();
        ECKeyPair_Demo.storeKeyPair("device", keyPair);
        String sortedParams = sortParamsWithTreeMap(map);
        String signWithECC = ECDSA_Demo.signWithECC(sortedParams, Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        System.out.println("排序后的参数: " + sortedParams);
        System.out.println("签名结果: " + signWithECC);

        boolean verifyWithECC = ECDSA_Demo.verifyWithECC(sortedParams, signWithECC, Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("验签结果: " + verifyWithECC);
    }


}
