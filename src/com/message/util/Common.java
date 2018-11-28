package com.message.util;

import java.io.UnsupportedEncodingException;

/**
 * 公用类
 * 
 * @author YC
 * @version 1.0 
 */
public final class Common {
    private static final char chars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

    public static String byte2hex(byte b) {
        char hex[] = new char[2];
        hex[0] = chars[(new Byte(b).intValue() & 0xf0) >> 4];
        hex[1] = chars[new Byte(b).intValue() & 0xf];
        return new String(hex);
    }
    public static String bytes2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byte2hex(b[i]));
            sb.append(" ");
        }
        return sb.toString();
    }
    /**
     * 将字符串转化成特定长度的byte[],如果value的长度小于idx,则右补零。比如 getText(5,"1"),结果为{49,0,0,0,0}; 如果value的长度大于idx,则截取掉一部分。比如
     * getText(2,"11111"),结果为{49,49};
     * 
     * @param idx
     *            转化后byte[]的长度
     * @param value
     *            要转化的字符串
     * @return byte[]
     */
    public static byte[] getText(int idx, String value) {
        byte[] b1 = new byte[idx];
        int i = 0;
        if (value != null || !value.equals("")) {
            byte[] b2 = value.getBytes();
            while (i < b2.length && i < idx) {
                b1[i] = b2[i];
                i++;
            }
        }
        while (i < b1.length) {
            b1[i] = 0;
            i++;
        }
        return b1;
    }

    /**
     * 8位的byte[]数组转换成long型
     * 
     * @param mybytes
     * @return long
     */
    public static long bytes8ToLong(byte mybytes[]) {
        long tmp = (0xff & mybytes[0]) << 56 | (0xff & mybytes[1]) << 48 | (0xff & mybytes[2]) << 40
                | (0xff & mybytes[3]) << 32 | (0xff & mybytes[4]) << 24 | (0xff & mybytes[5]) << 16
                | (0xff & mybytes[6]) << 8 | 0xff & mybytes[7];
        return tmp;
    }

    /**
     * long类型转化成8个字节
     * 
     * @param i
     *            要转化的长整形
     * @return byte[]
     */
    public static byte[] longToBytes8(long i) {
        byte mybytes[] = new byte[8];
        mybytes[7] = (byte) (int) ((long) 255 & i);
        mybytes[6] = (byte) (int) (((long) 65280 & i) >> 8);
        mybytes[5] = (byte) (int) (((long) 0xff0000 & i) >> 16);
        mybytes[4] = (byte) (int) (((long) 0xff000000 & i) >> 24);
        int high = (int) (i >> 32);
        mybytes[3] = (byte) (0xff & high);
        mybytes[2] = (byte) ((0xff00 & high) >> 8);
        mybytes[1] = (byte) ((0xff0000 & high) >> 16);
        mybytes[0] = (byte) ((0xff000000 & high) >> 24);
        return mybytes;
    }

    /**
     * int转化成4个字节的数组
     * 
     * @param i
     *            要转化的整形变量
     * @return byte[]
     */

    public static byte[] intToBytes4(int i) {
        byte mybytes[] = new byte[4];
        mybytes[3] = (byte) (0xff & i);
        mybytes[2] = (byte) ((0xff00 & i) >> 8);
        mybytes[1] = (byte) ((0xff0000 & i) >> 16);
        mybytes[0] = (byte) ((0xff000000 & i) >> 24);
        return mybytes;
    }

    /**
     * byte数组转化成int类型
     * 
     * @param mybytes
     *            要转化的
     * @return int
     */
    public static int bytes4ToInt(byte mybytes[]) {
        int tmp = (0xff & mybytes[0]) << 24 | (0xff & mybytes[1]) << 16 | (0xff & mybytes[2]) << 8 | 0xff & mybytes[3];
        return tmp;
    }
    
    
    
    
    
    /**
     * 2个字节的byte数组转化成short类型
     *
     * @param mybytes
     *            要转化的长度为2的byte数组
     * @return int
     */
    public static short bytes2Short(byte mybytes[]) {

        short tmp = (short) ((0xff & mybytes[0]) << 8 | 0xff & mybytes[1]);
        return tmp;
    }
    
    
    //short转字节
    public static byte[] short2Byte(short i) {

        byte mybytes[] = new byte[2];
        mybytes[1] = (byte) (0xff & i);
        mybytes[0] = (byte) ((0xff00 & i) >> 8);
        return mybytes;
    }
    
    
    //取字节的16进制字符串
	public static String getHexStr(byte[] bs)
	{
		String retStr="";
		for (int i=0 ;i<bs.length;i++)
		{
			if (Integer.toHexString((int)bs[i]).length()>1)
			{
			  retStr+= Integer.toHexString((int)bs[i]).substring(Integer.toHexString((int)bs[i]).length()-2);
			}else
			{ 
		      retStr+="0"+Integer.toHexString((int)bs[i]).substring(Integer.toHexString((int)bs[i]).length()-1);;
			}
		}
		return retStr;
	}
	
	
    //16进制字符串转字节数组
	public static byte[] getByte(String byteStr)
	{
		if (byteStr.length()%2!=0)
		{
			byteStr="0"+byteStr;
		}
		byte[] retByte=new byte[byteStr.length()/2];
	
		//System.out.println(byteStr.length()/2);
		for (int i=0 ;i<byteStr.length()/2;i++)
		{	
			//System.out.println(i);
			byte[] b=new byte[1];
			b[0]=toByte(Integer.parseInt(byteStr.substring(2*i, 2*i+2), 16))[3];
			
			retByte[i]=toByte(Integer.parseInt(byteStr.substring(2*i, 2*i+2), 16))[3];            
		}
		return retByte;
	}

    //int转字节
    public static byte[] toByte(int i) {

        byte mybytes[] = new byte[4];
        mybytes[3] = (byte) (0xff & i);
        mybytes[2] = (byte) ((0xff00 & i) >> 8);
        mybytes[1] = (byte) ((0xff0000 & i) >> 16);
        mybytes[0] = (byte) ((0xff000000 & i) >> 24);
        return mybytes;
    }

    
	 public static String getStr(byte[] bRefArr) { 
	    	int length=0;
	    	for (length=0;length<bRefArr.length;length++)
	    	{
	    		if (bRefArr[length]==0)
	    	    break;
	    	}
	        byte[] temp = new byte[length];
	        for (int i=0;i<length;i++)
	        {
	        	temp[i]=bRefArr[i];
	        }
	        String tempStr="";
			try {
				tempStr = new String(temp,0,temp.length,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return tempStr; 
	    } 
}