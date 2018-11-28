package com.message.smgp3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.util.Common;

/**
unsigned int            lTotal_Length;                            // 数据包长度,包头+包体
unsigned int            lCommand_ID;                              // 请求命令标志
unsigned int            lSerial_ID;                               // 序列号
*/

public class TSMGP_HEAD 
{
	public int lTotal_Length;
	public int lCommand_ID;
	public int lSerial_ID;
	
	private static final Log log = LogFactory.getLog(TSMGP_HEAD.class);
	 
	public  byte[] getHeadPacket() 
	{
        log.info( " 消息头处理,序列号=" + lSerial_ID+"消息头处理,命令标志= "+lCommand_ID);
        byte[] packet = new byte[12];
        System.arraycopy(Common.intToBytes4(lTotal_Length), 0, packet, 0, 4);
        System.arraycopy(Common.intToBytes4(lCommand_ID), 0, packet, 4, 4);
        System.arraycopy(Common.intToBytes4(lSerial_ID), 0, packet, 8, 4);
     
        return packet;
    }
	
	public void parsePacketHead(byte[] packet)
	{
	   byte[] length = new byte[4];
       System.arraycopy(packet, 0, length, 0, 4);
       this.lTotal_Length = Common.bytes4ToInt(length);
       if (log.isDebugEnabled()) 
       {
          log.debug("包长度解析后为:" + lTotal_Length);
       }

       byte[] commandid = new byte[4];
       System.arraycopy(packet, 4, commandid, 0, 4);
       this.lCommand_ID = Common.bytes4ToInt(commandid);
       if (log.isDebugEnabled()) 
       {
        log.debug("返回包命令字解析后=" + lCommand_ID + "，实际=" + FinalDef.CMD_SMGP_LOGIN_RESP);
       }

       byte[] seqid = new byte[4];
       System.arraycopy(packet, 8, seqid, 0, 4);
       this.lSerial_ID = Common.bytes4ToInt(seqid);
       if (log.isDebugEnabled()) 
       {
        log.debug("返回包序列号解析后为:" + lSerial_ID);
       }

	}
}
