package com.chige.security.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author wangyc
 * @Description aes密钥生成器
 * @Date 2025/9/1 01:02
 */
public class AESKeyGenerator_Demo {

    // GCM模式推荐的IV长度：12字节（96位），Tag长度：16字节（128位）
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    // 示例用法
    public static void main(String[] args) throws Exception {
        // 生成AES-128密钥
        SecretKey aesKey = generateAES128Key();

        // 生成AES-128密钥（实际使用中从安全存储获取）
        System.out.println("使用的AES密钥 (Base64): " + keyToBase64(aesKey));

        // 待加密的数据
        String originalData = "这是一段需要加密的敏感数据：userID=12345&token=abcdef";
        System.out.println("原始数据: " + originalData);

        // 加密
        String encrypted = encrypt(originalData, aesKey);
        System.out.println("加密后的数据: " + encrypted);

        // 解密
        String decrypted = decrypt(encrypted, aesKey);
        System.out.println("解密后的数据: " + decrypted);

        // 验证解密结果
        System.out.println("解密是否成功: " + originalData.equals(decrypted));
    }


    /**
     * 使用AES-GCM模式加密数据
     * 特点：同时提供机密性和完整性校验，防篡改
     *
     * @param plaintext 明文数据（字符串）
     * @param secretKey AES对称密钥（128/256位）
     * @return 加密后的数据（格式：Base64(IV + 密文 + Tag)）
     * @throws Exception 加密过程中发生错误时抛出
     */
    public static String encrypt(String plaintext, SecretKey secretKey) throws Exception {
        // 1. 生成随机IV（初始向量），每次加密都需不同
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // 2. 初始化加密器（AES-GCM模式）
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        // 3. 加密数据（返回密文 + 认证Tag）
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        // 4. 拼接IV + 密文+Tag，便于解密时使用（IV无需保密，但必须与加密时一致）
        byte[] combined = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

        // 5. 转换为Base64字符串，便于存储和传输
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 使用AES-GCM模式解密数据
     *
     * @param encryptedData 加密后的数据（Base64格式）
     * @param secretKey     对应的AES对称密钥
     * @return 解密后的明文
     * @throws Exception 解密失败（如密钥错误、数据被篡改）时抛出
     */
    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        // 1. 解码Base64数据
        byte[] combined = Base64.getDecoder().decode(encryptedData);

        // 2. 分离IV和密文+Tag
        byte[] iv = new byte[GCM_IV_LENGTH];
        byte[] cipherTextWithTag = new byte[combined.length - iv.length];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        System.arraycopy(combined, iv.length, cipherTextWithTag, 0, cipherTextWithTag.length);

        // 3. 初始化解密器
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        // 4. 解密（自动验证Tag，若数据被篡改会抛出异常）
        byte[] decryptedData = cipher.doFinal(cipherTextWithTag);

        // 5. 转换为明文字符串
        return new String(decryptedData, StandardCharsets.UTF_8);
    }


    /**
     * 生成AES-128对称密钥
     * AES-128使用128位（16字节）密钥，安全性高且性能均衡
     *
     * @return 生成的AES密钥（SecretKey对象）
     * @throws NoSuchAlgorithmException 当JVM不支持AES算法时抛出
     */
    public static SecretKey generateAES128Key() throws NoSuchAlgorithmException {
        // 获取AES密钥生成器实例
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        // 配置密钥长度为128位
        keyGenerator.init(128, getSecureRandom());

        // 生成密钥
        return keyGenerator.generateKey();
    }

    /**
     * 获取高强度随机数生成器
     * 优先使用系统原生随机数源，确保密钥的不可预测性
     *
     * @return 安全的随机数生成器
     * @throws NoSuchAlgorithmException 当指定的随机数算法不被支持时抛出
     */
    private static SecureRandom getSecureRandom() throws NoSuchAlgorithmException {
        SecureRandom secureRandom;
        try {
            // 优先使用系统原生非阻塞随机数源（适用于Linux系统）
            secureRandom = SecureRandom.getInstance("NativePRNGNonBlocking");
        } catch (NoSuchAlgorithmException e) {
            // 备选方案：使用SHA1PRNG（跨平台兼容）
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // 补充高熵种子，增强随机性
            secureRandom.setSeed(secureRandom.generateSeed(32));
        }
        return secureRandom;
    }

    /**
     * 将SecretKey转换为Base64字符串，便于存储和传输
     *
     * @param key 要转换的AES密钥
     * @return Base64编码的密钥字符串
     */
    public static String keyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 将Base64字符串转换回SecretKey对象
     *
     * @param base64Key Base64编码的密钥字符串
     * @return 恢复的AES密钥
     */
    public static SecretKey base64ToKey(String base64Key) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new javax.crypto.spec.SecretKeySpec(keyBytes, "AES");
    }

}
