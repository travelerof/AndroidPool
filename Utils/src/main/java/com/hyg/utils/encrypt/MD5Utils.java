package com.hyg.utils.encrypt;

import com.hyg.utils.ByteUtils;
import com.hyg.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author 韩永刚
 * @Date 2021/03/05
 * @Desc
 */
public class MD5Utils {

    /**
     * MD5加密不可逆的(理论)
     *
     * @param text
     * @return
     */
    public static String encryptMD5(String text) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(ByteUtils.toByteArray(text));
            byte[] digest = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                String hexStr = Integer.toHexString(0xff & digest[i]);
                if (hexStr.length() == 1) {
                    sb.append("0").append(hexStr);
                } else {
                    sb.append(hexStr);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件已加密方式返回
     *
     * @param file
     * @return
     */
    public static String encryptMD5File(File file) {

        FileInputStream fis = null;
        DigestInputStream dis = null;
        try {
            fis = new FileInputStream(file);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md5);
            byte[] buffer = new byte[1024];
            dis.getMessageDigest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileUtils.close(fis);
            FileUtils.close(dis);
        }
        return null;
    }

}
