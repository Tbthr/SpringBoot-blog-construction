package com.lyq.blog.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String code(String str){
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest=md.digest();
            int i;
            StringBuilder buf=new StringBuilder("");
            for (byte b : byteDigest) {
                i = b;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString(); //32位加密
//            return buf.toString().substring(8,32); //16位加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(code("admin151"));
    }
}
