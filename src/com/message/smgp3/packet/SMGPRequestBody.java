package com.message.smgp3.packet;

/**
 * 
 * @author YC
 * @version 1.0
 */
public interface SMGPRequestBody {
	/**
	 * 得到这个包的字节流形式
	 * @return
	 */
    byte[] getRequestBody();
}