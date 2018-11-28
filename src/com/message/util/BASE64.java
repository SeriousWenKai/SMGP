package com.message.util;

/**
 * base64的解码编码 <br>
 * base64的基本原理如下：
 * 
 * <pre><code>
 * 
 *  
 *       Base64要求把每三个8Bit的字节转换为四个6Bit的字节（3*8 = 4*6 = 24），然后把6Bit再添两位高位0，组成四个8Bit的字节，也就是说，转换后的字符串理论上将要比原来的长1/3。
 *       即:如果3个字节为01001001 01001001 00100010,则拆成如下4个6位的字节，010010 010100 100100 100010，然后在每个拆成的6个byte高位添0，
 *       即最后为如下：00010010 00010100 00100100 00100010，因此的话，变化后每个字节的10进值介于0-63之间,每个10进制值对应一个字节，对应关系见如下的pem_array数组
 *      
 *       如果最后只有1个字节或2个字节，则拆完后在后面补0，即01000110变为00010001 00100000等
 * 
 *  
 * </code></pre>
 * 
 * @author YC
 * @version 1.0
 */
public class BASE64 {

    private static final char pem_array[] = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', '+', '/'
    };

    /**
     * base64解码的时候需要用到的临时字节数组
     */
    private static final byte pem_convert_array[];
    static {
        pem_convert_array = new byte[256];
        for (int i = 0; i < 255; i++) {
            pem_convert_array[i] = -1;
        }

        for (int j = 0; j < pem_array.length; j++) {
            pem_convert_array[pem_array[j]] = (byte) j;
        }
    }

    /**
     * 将1个byte流经过base64编码
     * 
     * @param abyte0
     *            要编码的字节流
     * @return 经过base64编码后的字节流
     */
    public static byte[] encode(byte abyte0[]) {
        if (abyte0.length == 0) {
            return abyte0;
        }
        byte abyte1[] = new byte[((abyte0.length + 2) / 3) * 4];
        int i = 0;
        int j = 0;
        for (int k = abyte0.length; k > 0; k -= 3) {
            if (k == 1) {
                byte byte0 = abyte0[i++];
                int l = 0;
//                boolean flag = false;
                abyte1[j++] = (byte) pem_array[byte0 >>> 2 & 0x3f];
                abyte1[j++] = (byte) pem_array[(byte0 << 4 & 0x30) + (l >>> 4 & 0xf)];
                abyte1[j++] = 61;
                abyte1[j++] = 61;
            }
            else if (k == 2) {
                byte byte1 = abyte0[i++];
                byte byte3 = abyte0[i++];
                int i1 = 0;
                abyte1[j++] = (byte) pem_array[byte1 >>> 2 & 0x3f];
                abyte1[j++] = (byte) pem_array[(byte1 << 4 & 0x30) + (byte3 >>> 4 & 0xf)];
                abyte1[j++] = (byte) pem_array[(byte3 << 2 & 0x3c) + (i1 >>> 6 & 3)];
                abyte1[j++] = 61;
            }
            else {
                byte byte2 = abyte0[i++];
                byte byte4 = abyte0[i++];
                byte byte5 = abyte0[i++];
                abyte1[j++] = (byte) pem_array[byte2 >>> 2 & 0x3f];
                abyte1[j++] = (byte) pem_array[(byte2 << 4 & 0x30) + (byte4 >>> 4 & 0xf)];
                abyte1[j++] = (byte) pem_array[(byte4 << 2 & 0x3c) + (byte5 >>> 6 & 3)];
                abyte1[j++] = (byte) pem_array[byte5 & 0x3f];
            }
        }

        return abyte1;
    }

    /**
     * 将1个base64的字节流解码
     * 
     * @param abyte0
     *            要解码的字节流
     * @return 经过base64解码后的字节流
     */
    public static byte[] decode(byte abyte0[]) {
        int i = (abyte0.length / 4) * 3;
        if (i == 0) {
            return abyte0;
        }
        if (abyte0[abyte0.length - 1] == 61) {
            i--;
            if (abyte0[abyte0.length - 2] == 61) {
                i--;
            }
        }
        byte abyte1[] = new byte[i];
        int k = 0;
        int l = 0;
        for (int j = abyte0.length; j > 0; j -= 4) {
            byte byte0 = pem_convert_array[abyte0[k++] & 0xff];
            byte byte1 = pem_convert_array[abyte0[k++] & 0xff];
            abyte1[l++] = (byte) (byte0 << 2 & 0xfc | byte1 >>> 4 & 3);
            if (abyte0[k] == 61) {
                return abyte1;
            }
            byte0 = byte1;
            byte1 = pem_convert_array[abyte0[k++] & 0xff];
            abyte1[l++] = (byte) (byte0 << 4 & 0xf0 | byte1 >>> 2 & 0xf);
            if (abyte0[k] == 61) {
                return abyte1;
            }
            byte0 = byte1;
            byte1 = pem_convert_array[abyte0[k++] & 0xff];
            abyte1[l++] = (byte) (byte0 << 6 & 0xc0 | byte1 & 0x3f);
        }

        return abyte1;
    }
}
