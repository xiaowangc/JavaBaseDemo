package com.chige.security.ecc;

import com.alibaba.fastjson.JSON;
import com.chige.domain.Constants;
import com.chige.domain.SystemHeader;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

/**
 * @Author wangyc
 * @Description ecc测试
 * @Date 2025/9/1 00:40
 */
public class EccTest {

    public static void main(String[] args) throws Exception {
        //生成设备密钥对、平台密钥对
        KeyPair keyPair = ECKeyPair_Demo.generateECKeyPair();
        ECKeyPair_Demo.storeKeyPair("device1", keyPair);
        String devicePublic = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String devicePrivate = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("设备公钥: " + devicePublic);
        System.out.println("设备私钥: " + devicePrivate);

        PrivateKey ec = decode(devicePrivate, "EC");

        KeyPair systemKeyPair = ECKeyPair_Demo.generateECKeyPair();
        ECKeyPair_Demo.storeKeyPair("system", systemKeyPair);
        System.out.println("系统公钥: " + Base64.getEncoder().encodeToString(systemKeyPair.getPublic().getEncoded()));
        System.out.println("系统私钥: " + Base64.getEncoder().encodeToString(systemKeyPair.getPrivate().getEncoded()));


        Map<String, String> headerMap2 = new TreeMap<>();
        headerMap2.put("algorithm", "01");
        headerMap2.put("version", "001");
        headerMap2.put("timestamp", "1756979740");
        headerMap2.put("nonce", "FSDFSDCS12341232");
        headerMap2.put("serialNo", "I5FSC001032");
        headerMap2.put("productSerialNo", "80128301840213");

        //排序并&拼接
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headerMap2.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }



        //设备侧发起请求：设备私钥签名
        //服务端验证签名：设备公钥验签
        //服务端生成临时对称密钥，加密数据
        //服务端使用设备公钥加密对称密钥的原始数组
        //服务端响应：平台私钥签名
        //设备端验证签名：平台公钥验签
        //设备端使用设备私钥解密对称密钥
        //设备端使用对称密钥解密数据
        Long timestamp = System.currentTimeMillis();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(SystemHeader.X_CA_TIMESTAMP, String.valueOf(timestamp));
        headerMap.put(SystemHeader.X_CA_NONCE, UUID.randomUUID().toString());
        headerMap.put(SystemHeader.X_CA_VERSION, "1.0");
        headerMap.put(SystemHeader.X_CA_ALGORITHM, "01");
        headerMap.put(SystemHeader.HTTP_HEADER_ACCEPT, HttpMethod.POST_BODY.getAcceptContentType());
        headerMap.put(SystemHeader.HTTP_HEADER_CONTENT_TYPE, HttpMethod.POST_BODY.getRequestContentType());
        headerMap.put(SystemHeader.X_CA_SIGNATURE_HEADERS, StringUtils.joinWith(",", SystemHeader.X_CA_NONCE,
                SystemHeader.X_CA_TIMESTAMP, SystemHeader.X_CA_VERSION, SystemHeader.X_CA_ALGORITHM));

        RequestApi requestApi = new RequestApi();
        requestApi.setSerialNo("abc");
        String bodyStr = JSON.toJSONString(requestApi);
        boolean sign = sign(HttpMethod.POST_BODY, headerMap, "/path/to/api", bodyStr.getBytes(Constants.ENCODING), devicePrivate, devicePublic);
        System.out.println("签名对比：" + sign);

    }

    private static boolean sign(HttpMethod httpMethod, Map<String, String> headers, String uri, byte[] bytes, String privateKeyB64, String devicePublic) throws Exception {
        String stringToSign = buildStringToSign(httpMethod, headers, uri, bytes);
        String signWithECC = ECDSA_Demo.signWithECC(stringToSign, privateKeyB64);
        System.out.println(signWithECC);
        return ECDSA_Demo.verifyWithECC(stringToSign, signWithECC, devicePublic);
    }

    private static String buildStringToSign(HttpMethod httpMethod, Map<String, String> headers, String uri, byte[] bytes) {
        return httpMethod.getValue() + Constants.LF +
                headers.get(SystemHeader.HTTP_HEADER_ACCEPT) + Constants.LF +
                md5(bytes) + Constants.LF +
                headers.get(SystemHeader.HTTP_HEADER_CONTENT_TYPE) + Constants.LF +
                headersStr(headers) + uri;
    }


    private static String md5(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bytes);
            byte[] md5Result = md.digest();
            String base64Result = Base64.getEncoder().encodeToString(md5Result);
            return base64Result.length() > 24 ? base64Result.substring(0, 23) : base64Result;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }

    private static String headersStr(Map<String, String> headers) {
        String signatureHeaders = headers.get(SystemHeader.X_CA_SIGNATURE_HEADERS);
        StringBuilder sb = new StringBuilder();
        Map<String, String> sortMap = new TreeMap<>();
        if (StringUtils.isNotBlank(signatureHeaders)) {
            for (String signatureHeader : signatureHeaders.split(Constants.SPE1)) {
                sortMap.put(signatureHeader, headers.get(signatureHeader));
            }
        } else {
            sortMap.putAll(headers);
        }
        for (Map.Entry<String, String> header : sortMap.entrySet()) {
            //header参数过滤再考虑处理
            if (!header.getKey().startsWith("x-ca")) {
                continue;
            }

            if (header.getKey().equalsIgnoreCase(SystemHeader.X_CA_SIGNATURE_HEADERS)) {
                continue;
            }
            if (header.getKey().equalsIgnoreCase(SystemHeader.X_CA_SIGNATURE)) {
                continue;
            }
            sb.append(header.getKey().trim().toLowerCase());
            sb.append(Constants.SPE4);

            if (StringUtils.isNotBlank(header.getValue())) {
                sb.append(Objects.isNull(header.getValue()) ? "" : header.getValue().trim());
            }
            sb.append(Constants.LF);
        }
        return sb.toString();
    }

    /**
     * 将Base64编码的私钥字符串转换为PrivateKey对象
     * @param base64PrivateKey Base64编码的私钥字符串（PKCS#8格式）
     * @param algorithm 算法名称（如"RSA"、"EC"）
     * @return 转换后的PrivateKey对象
     * @throws Exception 转换过程中可能抛出的异常（如格式错误、算法不支持等）
     */
    public static PrivateKey decode(String base64PrivateKey, String algorithm) throws Exception {
        // 1. 解码Base64字符串为字节数组
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);

        // 2. 创建PKCS#8格式的密钥规范（通用私钥格式）
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        // 3. 根据算法获取密钥工厂并生成PrivateKey对象
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 对比两个PrivateKey是否为同一个私钥（基于密钥编码内容）
     * @param key1 第一个PrivateKey对象
     * @param key2 第二个PrivateKey对象
     * @return true：相同私钥；false：不同私钥或参数异常
     */
    public static boolean isSamePrivateKey(PrivateKey key1, PrivateKey key2) {
        // 1. 处理空值（避免空指针异常）
        if (key1 == null || key2 == null) {
            return key1 == key2; // 均为null时返回true，否则false
        }

        // 2. 先快速校验算法（不同算法的私钥必然不同）
        if (!key1.getAlgorithm().equals(key2.getAlgorithm())) {
            return false;
        }

        // 3. 核心：对比密钥的编码字节数组（PKCS#8格式二进制）
        byte[] encoded1 = key1.getEncoded();
        byte[] encoded2 = key2.getEncoded();

        // 4. 字节数组对比（Arrays.equals会逐字节校验）
        return Arrays.equals(encoded1, encoded2);
    }

    @Data
    static class RequestApi {
        private String serialNo;
    }
}
