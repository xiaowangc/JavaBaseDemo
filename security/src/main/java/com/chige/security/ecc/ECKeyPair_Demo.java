package com.chige.security.ecc;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangyc
 * @Description ECC体系下的密钥对
 * @Date 2025/9/1 00:36
 */
public class ECKeyPair_Demo {

    static Map<String, KeyPair> keyPairMap = new HashMap<>();
    static final SecureRandom SECURE_RANDOM;


    static {
        // 静态注册BouncyCastle Provider（确保算法实现一致性）
        Security.addProvider(new BouncyCastleProvider());
        // 初始化高安全性随机数生成器
        SECURE_RANDOM = createSecureRandom();
    }

    public static PublicKey getPublicKey(String keyId) {
        KeyPair keyPair = keyPairMap.get(keyId);
        if (keyPair != null) {
            return keyPair.getPublic();
        }
        return null;
    }

    public static PrivateKey getPrivateKey(String keyId) {
        KeyPair keyPair = keyPairMap.get(keyId);
        if (keyPair != null) {
            return keyPair.getPrivate();
        }
        return null;
    }

    public static String getPrivateKeyB64(String keyId) {
        PrivateKey privateKey = getPrivateKey(keyId);
        if (privateKey != null) {
            return Base64.getEncoder().encodeToString(privateKey.getEncoded());
        }
        return null;
    }

    public static String getPublicKeyB64(String keyId) {
        PublicKey publicKey = getPublicKey(keyId);
        if (publicKey != null) {
            return Base64.getEncoder().encodeToString(publicKey.getEncoded());
        }
        return null;
    }

    public static void storeKeyPair(String keyId, KeyPair keyPair) {
        keyPairMap.put(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()), keyPair);
        keyPairMap.put(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()), keyPair);
    }

    public static PrivateKey restorePrivateKey(String privateKeyB64) {
        return getPrivateKey(privateKeyB64);
    }

    public static PublicKey restorePublicKey(String publicKeyB64) {
        return getPublicKey(publicKeyB64);
    }

    /**
     * 生成高安全性的ECC密钥对（基于secp256r1曲线）
     * 优化点：指定安全Provider、强化随机数、增加密钥验证
     */
    public static KeyPair generateECKeyPair() throws Exception {
        // 1. 初始化密钥生成器，显式指定BC Provider
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");

        // 2. 获取高强度随机数生成器
        SecureRandom secureRandom = getSecureRandom();

        // 3. 生成密钥对
        keyGen.initialize(ecSpec, secureRandom);
        KeyPair keyPair = keyGen.generateKeyPair();

        // 4. 验证公钥有效性（确保在secp256r1曲线上）
        validateECPublicKey(keyPair.getPublic());

        return keyPair;
    }

    /**
     * 生成取高安全性的随机数生成器
     */
    private static SecureRandom createSecureRandom() {
        try {
            // Linux系统推荐使用NativePRNGNonBlocking（非阻塞，高熵）
            return SecureRandom.getInstance("NativePRNGNonBlocking");
        } catch (NoSuchAlgorithmException e) {
            return new SecureRandom();
        }
    }

    /**
     * 获取高安全性的随机数生成器（单例）
     */
    public static SecureRandom getSecureRandom() {
        return SECURE_RANDOM;
    }


    /**
     * 验证公钥是否符合secp256r1曲线参数（防无效密钥）
     */
    private static void validateECPublicKey(PublicKey publicKey) throws Exception {
        if (!(publicKey instanceof ECPublicKey)) {
            throw new IllegalArgumentException("Public key is not an ECC public key");
        }

        ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
        // 获取Java标准库的公钥坐标（x, y）
        java.security.spec.ECPoint stdPoint = ecPublicKey.getW();
        BigInteger x = stdPoint.getAffineX();
        BigInteger y = stdPoint.getAffineY();

        // 获取BouncyCastle的secp256r1曲线参数
        ECNamedCurveParameterSpec bcSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        // 将Java标准坐标转换为BouncyCastle的ECPoint
        ECPoint bcPoint = bcSpec.getCurve().createPoint(x, y);

        // 验证公钥是否在曲线上（使用BouncyCastle的isValid()方法）
        if (!bcPoint.isValid()) {
            throw new SecurityException("Generated public key is not on secp256r1 curve");
        }
    }

    /**
     * 生成指定长度的随机串32位（数字+大写字母，避免易混淆字符）
     */
    public static String generateNonce(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("随机串长度必须为正数");
        }
        // 字符集：数字 + 大小写字母（避免使用易混淆字符，如0和O、1和l）
        final String CHARACTERS = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";

        StringBuilder nonce = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 从字符集中随机选取一个字符
            int index = ECKeyPair_Demo.getSecureRandom().nextInt(CHARACTERS.length());
            nonce.append(CHARACTERS.charAt(index));
        }
        return nonce.toString();
    }
}
