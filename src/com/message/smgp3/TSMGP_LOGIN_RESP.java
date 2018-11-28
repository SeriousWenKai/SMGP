package com.message.smgp3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.smgp3.packet.SMGPResponsePacket;
import com.message.util.Common;

public class TSMGP_LOGIN_RESP  extends SMGPResponsePacket{

/**
 * 状态
 * 	 0：正确
	 1：系统忙
	 2：超过最大连接数
	 10：消息结构错
	 11：命令字错
	 22: 版本太高
 */
  public int lStatus;     
  public String sAuthenticatorServer;
  public int cVersion;
  public int length=FinalDef.MAX_SMGP_AUTH_LEN+5;
  
  /**
   * Logger for this class
   */
  private static final Log log = LogFactory.getLog(TSMGP_LOGIN_RESP.class);
  
  
  public void parseResponseBody(byte[] packet)  {
      //try
     // {
      byte[] length = new byte[4];
      System.arraycopy(packet, 0, length, 0, 4);
      this.lTotal_Length = Common.bytes4ToInt(length);
      if (log.isDebugEnabled()) {
          log.debug("返回包长度解析后为:" + lTotal_Length);
      }

      byte[] commandid = new byte[4];
      System.arraycopy(packet, 4, commandid, 0, 4);
      this.lCommand_ID = Common.bytes4ToInt(commandid);
      if (log.isDebugEnabled()) {
          log.debug("返回包命令字解析后=" + lCommand_ID + "，实际=" + FinalDef.CMD_SMGP_LOGIN_RESP);
      }

      byte[] seqid = new byte[4];
      System.arraycopy(packet, 8, seqid, 0, 4);
      this.lSerial_ID = Common.bytes4ToInt(seqid);
      if (log.isDebugEnabled()) {
          log.debug("返回包序列号解析后为:" + lSerial_ID);
      }

      byte stat[] = new byte[4];
      System.arraycopy(packet, 12, stat, 0, 4);
      lStatus = Common.bytes4ToInt(stat);

      byte[] authen = new byte[FinalDef.MAX_SMGP_AUTH_LEN];
      System.arraycopy(packet, 16, authen, 0, FinalDef.MAX_SMGP_AUTH_LEN);
      this.sAuthenticatorServer = new String(authen);

      cVersion = packet[16+FinalDef.MAX_SMGP_AUTH_LEN];
      //}
      //catch(Exception e)
      //{    	  
    //	  log.error("返回包解析错误："+e.toString());
     // }
      log.info("connectResp消息解析成功,status=" + lStatus+",sequenceID="+lSerial_ID);
  }
  
  
  
  
  
  
  
  
  
  
  
  
}
