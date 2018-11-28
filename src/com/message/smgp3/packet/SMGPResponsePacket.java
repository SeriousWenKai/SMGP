package com.message.smgp3.packet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 返回的消息部分,返回来的消息先以这种方式接收
 * 
 * @author YC
 * @version 1.0
 */
public abstract class SMGPResponsePacket {

    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(SMGPResponsePacket.class);

    /**
     * 包长度,包括包头和包体的总长度
     */
    protected int lTotal_Length;

    /**
     * 发送包的命令字id
     * 
     * @link #commandID
     */
    protected int lCommand_ID;

    /**
     * 发送序列号，步长为1,达到最大值后循环使用
     */
    protected int lSerial_ID;

    /**
     * 得到解析后的命令字
     * 
     * @return
     */
    public int getLCommand_ID() {
        return this.lCommand_ID;
    }

    /**
     * 得到解析后的序列号
     * 
     * @return
     */
    public int getLSerial_ID() {
        return this.lSerial_ID;
    }

    /**
     * 得到解析后的总长度
     * 
     * @return
     */
    public int getLTotal_Length() {
        return this.lTotal_Length;
    }

    /**
     * 解析返回的输入流（字节形式）
     * 
     * @param body
     */
   public abstract void parseResponseBody(byte[] body);
}