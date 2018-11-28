package com.message.smgp3.packet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.util.Common;

/**
 * smgp请求包,smgpsocket发送的时候,即发送这个smgprequestpacket
 * 
 * @author YC
 * @version 1.0
 */
public class SMGPRequestPacket {
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(SMGPRequestPacket.class);

    /**
     * 包长度,包括包头和包体的总长度
     */
    public int lTotal_Length;

    /**
     * 发送包的命令字id
     * 
     * @link #commandID
     */
    public int lCommand_ID;

    /**
     * 发送序列号，步长为1,达到最大值后循环使用
     */
    public int lSerial_ID;

    /**
     * SMGP包体，用接口的方式，实现对其他所有包的一种抽象
     */
    public SMGPRequestBody body;

    /**
     * 设置命令字
     * 
     * @param lCommand_ID
     */
    public void setLCommand_ID(int lCommand_ID) {
        this.lCommand_ID = lCommand_ID;
        log.debug("this.lCommand_ID=="+this.lCommand_ID);
    }

    /**
     * 设置序列号
     * 
     * @param lSerial_ID
     */
    public void setLSerial_ID(int lSerial_ID) {
        this.lSerial_ID = lSerial_ID;
    }

    /**
     * 设置包体
     * 
     * @param body
     */
    public  void setRequestBody(SMGPRequestBody body) {
        this.body = body;
    }

    /**
     * 取得包体
     * 
     * @return
     */
    public  SMGPRequestBody getRequestBody() {
        return this.body;
    }

    /**
     * 取得整个smgp请求包的字节流形式
     * 
     * @return
     */
    public  byte[] getRequestPacket() {
        log.info(body.getClass().getName() + " 消息处理,序列号=" + lSerial_ID);
        byte[] bodybytes = body.getRequestBody();
        this.lTotal_Length = 12 + bodybytes.length;

        byte[] requestPacket = new byte[lTotal_Length];
        log.debug("lTotal_Length=="+lTotal_Length);
        log.debug("lCommand_ID=="+lCommand_ID);
        log.debug("lSerial_ID=="+lSerial_ID);
        System.arraycopy(Common.intToBytes4(lTotal_Length), 0, requestPacket, 0, 4);
        System.arraycopy(Common.intToBytes4(lCommand_ID), 0, requestPacket, 4, 4);
        System.arraycopy(Common.intToBytes4(lSerial_ID), 0, requestPacket, 8, 4);
        System.arraycopy(bodybytes, 0, requestPacket, 12, bodybytes.length);

        return requestPacket;
    }
}