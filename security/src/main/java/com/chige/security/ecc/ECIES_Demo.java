package com.chige.security.ecc;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static com.chige.security.ecc.ECKeyPair_Demo.restorePrivateKey;
import static com.chige.security.ecc.ECKeyPair_Demo.restorePublicKey;

/**
 * @Author wangyc
 * @Description ECC体系下的加密算法ECIES
 * @Date 2025/9/1 00:20
 */
public class ECIES_Demo {


    public static void main(String[] args) throws Exception {
        KeyPair keyPair = ECKeyPair_Demo.generateECKeyPair();
        ECKeyPair_Demo.storeKeyPair("device1", keyPair);
        String content = "{\"productKey\":\"888XBC\",\"deviceName\":\"23JKSHJKSLSDFSDF\",\"deviceSecret\":\"ASDGERAERGD4F45YDH5HDHEYEHG\"}";
        String s = encryptWithECC(content, Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        String decryptWithECC = decryptWithECC(s, Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        System.out.println("原始数据: " + content);
        System.out.println("s1:" + s);
        System.out.println("解密数据:" + decryptWithECC);
    }


    /**
     * 使用ECC公钥加密数据
     */
    public static String encryptWithECC(String data, String publicKeyB64) throws Exception {
        return encryptWithECC(data.getBytes("UTF-8"), publicKeyB64);
    }

    public static String encryptWithECC(byte[] data, String publicKeyB64) throws Exception {
        try {
            // 1. 恢复公钥
            PublicKey publicKey = restorePublicKey(publicKeyB64);
            // 2. 使用ECIES算法加密
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 3. 加密数据
            byte[] encryptedData = cipher.doFinal(data);
            // 4. Base64编码返回
            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (Exception e) {
            throw new Exception("ECC加密失败: " + e.getMessage(), e);
        }
    }

    /**
     * 使用ECC私钥解密数据
     */
    public static String decryptWithECC(String encryptedDataBase64, String privateKeyB64) throws Exception {
        try {
            // 1. 恢复私钥
            PrivateKey privateKey = restorePrivateKey(privateKeyB64);

            // 2. 使用ECIES算法解密
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 3. 解密数据
            byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);
            byte[] decryptedData = cipher.doFinal(encryptedData);

            // 4. 返回解密后的字符串
            return new String(decryptedData, "UTF-8");

        } catch (Exception e) {
            throw new Exception("ECC解密失败: " + e.getMessage(), e);
        }
    }
}
