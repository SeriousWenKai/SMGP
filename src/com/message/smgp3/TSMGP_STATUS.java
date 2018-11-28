package com.message.smgp3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.smgp3.packet.SMGPResponsePacket;
import com.message.util.Common;

/**
 * 
 	unsigned char           sMsgID[SEQ_LEN];                           // 短消息标识
	unsigned char           sSub[SUB_LEN];                             // 取缺省值001
	unsigned char           sDlvrd[DLVRD_LEN];                         // 取缺省值001
	unsigned char           sSubmit_Date[DATE_LEN];                    // 短消息提交时间(格式：yymmddhhmm)
	unsigned char           sDone_Date[DATE_LEN];                      // 短消息下发时间(格式：yymmddhhmm)
	unsigned char           sStat[STAT_LEN];                           // 短消息状态(参见短消息状态表)
	unsigned char           sErr[ERR_LEN];                             // 具体值参见Mobile Application Part(MAP) specification
	unsigned char           sTxt[TEXT_LEN];                            // 前三个字节，表示短消息长度(ASC), 后17个字节表示短消息的前一段字符

 *
 */
public class TSMGP_STATUS extends SMGPResponsePacket{
	
 	public String           sMsgID;                        // 短消息标识
 	public String           sSub;                          // 取缺省值001
 	public String           sDlvrd;                        // 取缺省值001
 	public String           sSubmit_Date;                  // 短消息提交时间(格式：yymmddhhmm)
 	public String           sDone_Date;                    // 短消息下发时间(格式：yymmddhhmm)
 	public String           sStat;                         // 短消息状态(参见短消息状态表)
 	public String           sErr;                          // 具体值参见Mobile Application Part(MAP) specification
 	public String           sTxt;                          // 前三个字节，表示短消息长度(ASC), 后17个字节表示短消息的前一段字符
	
 	private static final Log log = LogFactory.getLog(TSMGP_STATUS.class);
 	
	public void parseResponseBody (byte[] packet) 
	{
		 
	      byte[] length = new byte[4];
	      int cur=0;
	      
	      cur+=3;    
	      byte[] msgid = new byte[FinalDef.SEQ_LEN];	      
	      System.arraycopy(packet, cur, msgid, 0, FinalDef.SEQ_LEN);
	      this.sMsgID = Common.getHexStr(msgid);	     
	      cur+=FinalDef.SEQ_LEN;
	      
	      
	      cur+=5;
	      byte[] sub = new byte[FinalDef.SUB_LEN];	      
	      System.arraycopy(packet, cur, sub, 0, FinalDef.SUB_LEN);
	      this.sSub = new String(sub);
	      cur+=FinalDef.SUB_LEN;
	      
	      cur+=7;	      
	      byte[] dlvrd = new byte[FinalDef.DLVRD_LEN];      
	      System.arraycopy(packet, cur, dlvrd, 0, FinalDef.DLVRD_LEN);
	      this.sDlvrd = new String(dlvrd);
	      cur+=FinalDef.DLVRD_LEN;
	      
	      cur+=13;
	      byte[] submit_date = new byte[FinalDef.DATE_LEN];	      
	      System.arraycopy(packet, cur, submit_date, 0, FinalDef.DATE_LEN);
	      this.sSubmit_Date = new String(submit_date);
	      cur+=FinalDef.DATE_LEN;
 
	      cur+=11;
	      byte[] done_date = new byte[FinalDef.DATE_LEN];
	      System.arraycopy(packet, cur, done_date, 0, FinalDef.DATE_LEN);
	      this.sDone_Date = new String(done_date);
	      cur+=FinalDef.DATE_LEN;
	      
	      cur+=6;
	      byte[] stat = new byte[FinalDef.STAT_LEN];	     
	      System.arraycopy(packet, cur, stat, 0, FinalDef.STAT_LEN);
	      this.sStat = new String(stat);
	      cur+=FinalDef.STAT_LEN;
	      
	      
	      cur+=5;
	      byte[] err = new byte[FinalDef.ERR_LEN];
	      System.arraycopy(packet, cur, err, 0, FinalDef.ERR_LEN);
	      this.sErr = new String(err);
	      cur+=FinalDef.ERR_LEN;
	      

	      cur+=6;
	      byte[] txt = new byte[FinalDef.TEXT_LEN];	      
	      System.arraycopy(packet, cur, txt, 0, FinalDef.TEXT_LEN);
	      this.sTxt = new String(txt);
	      cur+=FinalDef.TEXT_LEN;
	      
		
	}
 	

}
