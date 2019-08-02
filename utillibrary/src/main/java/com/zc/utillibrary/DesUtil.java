package com.zc.utillibrary;

import android.os.Build;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description Des加密工具类
 */
public class DesUtil {
    private final static String TYPE = "DES";
    /** SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法*/
    private static final String SHA1PRNG = "SHA1PRNG";
    private static final String chaset = "utf-8";
    private static final String provide = "Crypto";
    /**
     * DES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
     */
    private final static String TRANSFORMATION = "DES/ECB/PKCS5Padding";
    /**
     * DES加密
     * @param encryptString 需要加密的字符串
     * @param encryptKey 密钥
     * @return String
     */
    public static String encryptDES(String encryptString, String encryptKey) {
        String encryString = null;
        try {
            byte[] encrKeyByte = encryptKey.getBytes(chaset);
            DESKeySpec deskey = new DESKeySpec(encrKeyByte);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TYPE);
            SecretKey key = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 在4.2以上版本中，SecureRandom获取方式发生了改变
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Build.VERSION.SDK_INT < 28){
                sr = SecureRandom.getInstance(SHA1PRNG,new CryptoProvider());
            }
            else if ( Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ) {
                sr = SecureRandom.getInstance(SHA1PRNG, provide);
            } else {
                sr = SecureRandom.getInstance(SHA1PRNG);
            }
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            encryString = Base64.encode(encryptedData);
        }catch (Exception e){
            Log.e("DesUtil", "加密失败：" + e.getMessage());
        }
        return encryString;
    }

    /**
     * Des解密
     * @param decryptString 需要解密的字符串
     * @param decryptKey 密钥
     * @return String
     */
    public static String decryptDES(String decryptString, String decryptKey)  {
        String decryString = null;
        try {
            byte[] byteMi = Base64.decode(decryptString);
            byte[] encrKeyByte = decryptKey.getBytes(chaset);
            DESKeySpec deskey = new DESKeySpec(encrKeyByte);
            SecureRandom sr = new SecureRandom();


            // 在4.2以上版本中，SecureRandom获取方式发生了改变
           if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Build.VERSION.SDK_INT < 28){
                sr = SecureRandom.getInstance(SHA1PRNG,new CryptoProvider());
            }
            else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ) {
                sr = SecureRandom.getInstance(SHA1PRNG, provide);
            } else {
                sr = SecureRandom.getInstance(SHA1PRNG);
            }
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TYPE);
            SecretKey key = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            byte decryptedData[] = cipher.doFinal(byteMi);
            decryString = new String(decryptedData);
        }catch (Exception e){
            Log.e("DesUtil", "解密失败：" + e.getMessage());
        }


        return decryString;
    }

    public static  class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
    private static SecretKeySpec deriveKeyInsecurely(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(passwordBytes, 14), "DES");
    }
}