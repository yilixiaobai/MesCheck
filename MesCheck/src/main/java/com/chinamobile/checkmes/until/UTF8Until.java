package com.chinamobile.checkmes.until;

import java.io.UnsupportedEncodingException;

public class UTF8Until {

    public static byte[] utf8to(String str){
        try{
            ByteArray byteArr=new ByteArray();
            byte[]data=str.getBytes("utf-8");
            for(int i=0;i<data.length;i++){
                byte b=data[i];
            if(0==(b&0x80)){
                byteArr.addByte((byte) 0x00);
                byteArr.addByte(b);
            }
            else if(0==(b&0x20)){
                byte a=data[++i];
                byteArr.addByte((byte)((b&0x3F)>>2));
                byteArr.addByte((byte)(a&0x3F|b<<6));
            }
            else if(0==(b&0x10)){
                byte a=data[++i];
                byte c=data[++i];
                byteArr.addByte((byte)(b<<4|(a&0x3F)>>2));
                byteArr.addByte((byte)(a<<6|c&0x3F));
            }

            }
            return byteArr.toArray();
        }catch(UnsupportedEncodingException e)
        {
         e.printStackTrace();
        }
        return null;
    }
    public static String DecodeUCS2(String src) {
        byte[] bytes = new byte[src.length() / 2];

        for (int i = 0; i < src.length(); i += 2) {
            bytes[i / 2] = (byte) (Integer
                    .parseInt(src.substring(i, i + 2), 16));
        }
        String reValue=null;
        try {
            reValue = new String(bytes, "UTF-16BE");
        } catch (Exception e) {
           e.printStackTrace();
        }
        return reValue;

    }

    /**
     * UCS2编码
     *
     * @param src
     *            UTF-16BE编码的源串
     * @return 编码后的UCS2串
     */
    public static String EncodeUCS2(String src) {

        byte[] bytes=null;
        try {
            bytes = src.getBytes("UTF-16BE");
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer reValue = new StringBuffer();
        StringBuffer tem = new StringBuffer();
        for (int i = 0; null!=bytes&&i < bytes.length; i++) {
            tem.delete(0, tem.length());
            tem.append(Integer.toHexString(bytes[i] & 0xFF));
            if(tem.length()==1){
                tem.insert(0, '0');
            }
            reValue.append(tem);
        }
        return reValue.toString().toUpperCase();
    }
    }


