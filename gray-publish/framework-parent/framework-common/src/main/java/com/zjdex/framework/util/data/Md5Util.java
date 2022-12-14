package com.zjdex.framework.util.data;


import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * @author: lindj
 * @date: 2018/6/15 16:24
 * @description: md5算法
 */
public class Md5Util {
    public static char[] num_chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String md5H16(String input) {
        return md5(input).substring(8, 24);
    }

    public static String md5(String input) {
        return toLowMD5String(input);
    }

    public static String toLowMD5String(String input) {
        return toMD5String(input).toLowerCase();
    }

    public static String toMD5String(String input) {
        if (input == null) {
            input = "";
        }
        return toMD5String(input.getBytes());
    }

    public static String toMD5String(byte[] input) {
        char[] output = new char[32];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] by = md.digest(input);
            for (int i = 0; i < by.length; ++i) {
                output[(2 * i)] = num_chars[((by[i] & 0xF0) >> 4)];
                output[(2 * i + 1)] = num_chars[(by[i] & 0xF)];
            }
        } catch (Exception localException) {
        }
        return new String(output);
    }

    public static String getFileMD5(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!(file.isFile())) {
            return null;
        }
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            int len;
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, len);
            }

            byte[] by = md.digest();
            char[] output = new char[32];
            for (int i = 0; i < by.length; ++i) {
                output[(2 * i)] = num_chars[((by[i] & 0xF0) >> 4)];
                output[(2 * i + 1)] = num_chars[(by[i] & 0xF)];
            }
            return new String(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception localException3) {
            }
        }
        return null;
    }
}
