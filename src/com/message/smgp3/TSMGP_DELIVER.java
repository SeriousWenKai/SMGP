package com.message.smgp3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.util.Common;

/*
unsigned char           sMsgID[MAX_SMGP_MSGID_LEN];                // 短消息标识  
unsigned char           cIsReport;                                 // 是否状态报告(0：不是 1：是)
unsigned char           ucMsgFormat;                                // 短消息格式(参见短消息格式表)
unsigned char           sRecvTime[MAX_SMGP_RECVTIME_LEN];          // 短消息接收时间(格式：yyyymmddhhmmss)
unsigned char           sSrcTermID[MAX_SMGP_TERMINALID_LEN];       // 短消息发送号码
unsigned char           sDestTermID[MAX_SMGP_TERMINALID_LEN];      // 短消息接收号码
unsigned char           ucMsgLength;                                // 短消息长度
unsigned char           sMsgContent[MAX_SMGP_MSGCONTENT_LEN];      // 短消息内容
unsigned char           sReserve[MAX_SMGP_RESERVE_LEN];            // 保留
unsigned char           sIsmgMsgID[12];                // 短消息标识  
*/


public class TSMGP_DELIVER{
	public String         sMsgID;           // 短消息标识  
	public byte           cIsReport;        // 是否状态报告(0：不是 1：是)
	public byte           ucMsgFormat;      // 短消息格式(参见短消息格式表)
	public String         sRecvTime;        // 短消息接收时间(格式：yyyymmddhhmmss)
	public String         sSrcTermID;       // 短消息发送号码
	public String         sDestTermID;      // 短消息接收号码
	public byte           ucMsgLength;      // 短消息长度
	public String         sMsgContent;      // 短消息内容
	public String         sReserve;         // 保留
	public String         sIsmgMsgID;       // 短消息标识  
	
	
	public TSMGP_TLV      tsmgpTlv=new TSMGP_TLV();;  
	
	/*
	private int            length = FinalDef.MAX_SMGP_MSGID_LEN
	                              + FinalDef.MAX_SMGP_RECVTIME_LEN
	                              + FinalDef.MAX_SMGP_TERMINALID_LEN*2
	                              + FinalDef.MAX_SMGP_MSGCONTENT_LEN
	                              + FinalDef.MAX_SMGP_RESERVE_LEN
	                              + 14;
	*/
	private static final Log log = LogFactory.getLog(TSMGP_DELIVER.class);
	
	
	public void parsePacketBody(byte[] packet) 
	{
	      
	      int cur=0;
	     		
	      byte[] msgid = new byte[FinalDef.MAX_SMGP_MSGID_LEN];      
	      System.arraycopy(packet, cur, msgid, 0, FinalDef.MAX_SMGP_MSGID_LEN);
	      
	      this.sMsgID = Common.getHexStr(msgid);
	      
	      cur+=FinalDef.MAX_SMGP_MSGID_LEN;
	      
	      this.cIsReport=packet[cur];
	      cur+=1;
	      
	      this.ucMsgFormat=packet[cur];
	      cur+=1;
	      
	      byte[] recvtime = new byte[FinalDef.MAX_SMGP_RECVTIME_LEN];
	      
	      System.arraycopy(packet, cur, recvtime, 0, FinalDef.MAX_SMGP_RECVTIME_LEN);
	      this.sRecvTime = new String(recvtime);
	      cur+=FinalDef.MAX_SMGP_RECVTIME_LEN;
	      
	      
	      byte[] srcterid = new byte[FinalDef.MAX_SMGP_TERMINALID_LEN];
	      System.arraycopy(packet, cur, srcterid, 0, FinalDef.MAX_SMGP_TERMINALID_LEN);
	      this.sSrcTermID = new String(srcterid);
	      cur+=FinalDef.MAX_SMGP_TERMINALID_LEN;
	      
	      
	      byte[] disttermid = new byte[FinalDef.MAX_SMGP_TERMINALID_LEN];	      
	      System.arraycopy(packet, cur, disttermid, 0, FinalDef.MAX_SMGP_TERMINALID_LEN);
	      this.sDestTermID = new String(disttermid);
	      cur+=FinalDef.MAX_SMGP_TERMINALID_LEN;
	      
	      
	      this.ucMsgLength=packet[cur];
	      cur+=1;
          
	      int msglen = this.ucMsgLength >= 0 ? this.ucMsgLength : 256 + this.ucMsgLength; 
	      log.debug("this.ucMsgLength==="+this.ucMsgLength);

	      byte[] msgcontent = new byte[msglen];	      
	      System.arraycopy(packet, cur, msgcontent, 0, msglen);
	      this.sMsgContent = new String(msgcontent);
	      cur+=msglen;
	      
	      
	      
	      byte[] reserve = new byte[FinalDef.MAX_SMGP_RESERVE_LEN];
	      
	      System.arraycopy(packet, cur, reserve, 0, FinalDef.MAX_SMGP_RESERVE_LEN);
	      this.sReserve = new String(reserve);
	      cur+=FinalDef.MAX_SMGP_RESERVE_LEN;
	      
	      /*
	      byte[] ismgmsg = new byte[12];	      
	      System.arraycopy(packet, cur, ismgmsg, 0, 12);
	      this.sIsmgMsgID = Common.getHexStr(ismgmsg);
	      cur+=12;
	      */
	      
	      if (packet.length-cur>0)
	      {
	    	  byte[] tlv=new byte[packet.length-cur];
	    	  System.arraycopy(packet, cur, tlv, 0, packet.length-cur);
	    	  this.tsmgpTlv.parseBytes(tlv);
	      }

		
	}
}
