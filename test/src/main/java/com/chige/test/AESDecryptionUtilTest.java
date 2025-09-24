package com.chige.test;


import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * aes工具类
 *
 * @author weishi
 * @time 2014-5-23下午06:01:34
 */
public class AESDecryptionUtilTest {


    /**
     * 加密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String encrypt(String message) {
        String authKey = "gh&*$P3124334343";

        String hexString = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes = authKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec iv = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            hexString = toHexString(cipher.doFinal(message.getBytes("UTF-8")));
            return hexString;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密数据异常:" + message);
        }

        return message;
    }

    /**
     * 解密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message) {
        String authKey = "gh&*$P3124334343";
        try {
            byte[] bytesrc = convertHexString(message);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(authKey.getBytes("UTF-8"), "AES");
            IvParameterSpec iv = new IvParameterSpec(authKey.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    public static void main(String[] args) {
        String encrypt = AESDecryptionUtilTest.encrypt("yongchi.w98@gmail.com");
        System.out.println("加密结果: " + encrypt);
        System.out.println(AESDecryptionUtilTest.decrypt("3fddf02942a81ff9692b9bd5158283d9"));
    }



    /**
     * 字符串转byte数组
     *
     * @param ss
     * @return
     */
    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }

    /**
     * 转字符串
     *
     * @param b
     * @return
     */
    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }

        return hexString.toString();
    }


}
