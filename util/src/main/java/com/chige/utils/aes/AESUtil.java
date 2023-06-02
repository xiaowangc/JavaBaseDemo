package com.chige.utils.aes;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class AESUtil {

    private static Cipher cipher;
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 此处使用AES-128-ECB加密模式，key需要为16位。
     */
    private static final String AESKEY = "aWai8we6rogeeNga";

    private static final String CHARSETNAME = "utf-8";

    static {
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(("AES初始化失败" +  e));
        } catch (NoSuchPaddingException e) {
            System.out.println(("AES初始化失败" + e));
        } catch (Exception e) {
            System.out.println(("AES初始化失败" + e));
        }
    }

    // 加密 重试一次
    public static synchronized String encrypt(String sSrc) throws Exception {
        try {
            return encryptStr(sSrc);
        } catch (Exception e) {
            return encryptStr(sSrc);
        }
    }


    private static String encryptStr(String sSrc) throws Exception {
        if (sSrc == null || sSrc == "") {
            return null;
        }

        byte[] raw = AESKEY.getBytes(CHARSETNAME);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(CHARSETNAME));

        return encoder.encodeToString(encrypted);


    }

    // 解密 重试一次
    public static synchronized String decrypt(String sSrc) throws Exception {
        try {
            return decryptStr(sSrc);
        } catch (Exception e) {
            return decryptStr(sSrc);
        }

    }

    private static String decryptStr(String sSrc) throws Exception {
//        if (StringUtils.isEmpty(sSrc)) {
//            return null;
//        }
        byte[] raw = AESKEY.getBytes(CHARSETNAME);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        //先用base64解密
        byte[] encrypted1 = decoder.decode(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, CHARSETNAME);
    }

    private static void aesTest() throws Exception {
        // 需要加密的字串
        String cSrc = "{\"text\":\"土坷垃\",\"content_type\":1}";
        System.out.println("加密前的字串是：" + cSrc + "-" + cSrc.length());
//        // 加密
        String enString = encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString + "-" + enString.length());

        // 解密
        String deString = decrypt("Ywsmzzn3sxXD2xemNav8NQoGuT9DCWiusk9SPX3tMX+BgvVOqy9vdu+ztNlJhb0/om+wIhzR78WbNjGsIdqwBH0/ayjQBDtFqP7xAIqbP7WgQnUo2/hyg8qQrb1wGa2A75TESp/cwSPNIBAKL8e5BgBLU+kBBpKFy5KAjxZcIL261/TR+Fn7F1JKxlC9OVV0gAW+k49+wHf3f6AmkvD+JtUlbLFZ6T2ya6ftEokge8IwJS+ToP3phXmmcGGS0s34sI6ZDXPOBpZj4wldG6rtFqPVs3skgZtrlFbJeCyx/bO2pQ5I3qOh0/dQgrd/akd+vsjXoQQMBBZVudb9E/V/iJTpb02Ra4MbUixdUzyZCVdB/QNASlXpLmcMfdZ8Meb+ibR+OTOvmCdrAArH1Uxgow==");
        System.out.println("解密后的字串是：" + deString + "-" + deString.length());
    }


    public static void main(String[] args) throws Exception {
//        aesTest();
        HashMap<Integer, String> hashMap = new HashMap<>();
        LinkedHashMap<Integer, String> linkedMap = new LinkedHashMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();

        hashMap.put(5,"1a");
        hashMap.put(6,"1b");
        hashMap.put(2,"1c");
        hashMap.put(1,"1r");
        hashMap.put(15,"1g");
        hashMap.put(173,"1g");
        hashMap.put(184,"1g");
        hashMap.put(192,"1g");

        linkedMap.put(1,"2a");
        linkedMap.put(3,"2b");
        linkedMap.put(2,"2g");
        linkedMap.put(4,"2c");
        linkedMap.put(5,"2bb");

        treeMap.put(1,"3a");
        treeMap.put(3,"3ea");
        treeMap.put(2,"3ar");
        treeMap.put(4,"3af");
        treeMap.put(5,"3aff");


        hashMap.keySet().forEach(key -> System.out.println(key + ",hash:" + key % 16));
        hashMap.forEach((key,value) -> System.out.println("hashMap:" + key + ":" + value));
        System.out.println();
        for (Map.Entry<Integer, String> stringEntry : hashMap.entrySet()) {
            System.out.println("hashMap: " + stringEntry.getKey() + ":" + stringEntry.getValue());
        }

        for (Map.Entry<Integer, String> stringEntry : linkedMap.entrySet()) {
            System.out.println("LinkedMap: " + stringEntry.getKey() + ":" + stringEntry.getValue());
        }

        for (Map.Entry<Integer, String> stringEntry : treeMap.entrySet()) {
            System.out.println("TreeMap: " + stringEntry.getKey() + ":" + stringEntry.getValue());
        }
    }

}
