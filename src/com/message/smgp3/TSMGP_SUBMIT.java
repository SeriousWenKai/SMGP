package com.message.smgp3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.smgp3.packet.SMGPRequestBody;
import com.message.util.Common;

/**
	unsigned char           cMsgType;                                  // 1.3: 短消息子类型（0＝取消订阅，1＝订阅请求，2＝点播，3＝订阅）  1
                                                                       // 2.0: 短消息类型（1=取消订阅，2=订阅请求，3=点播，4=订阅，5=交互式操作，6=查询，7=点对点消息，8=电子商务，9=代码方式）
	                                                                // 3.0: 短消息类型（0=MO消息，6=MT消息，7=点对点消息） 2
	unsigned char           cNeedReport;                               // 是否要求返回状态报告
																// 0：不要求  1：要求
	unsigned char           cPriority;                                 // 发送优先级（ 1.3: 0-9； 2.0: 0-3； 3.0: 0-3）  3
	unsigned char           sServiceID[MAX_SMGP_SERVICEID_LEN];        // 服务类型  13
	unsigned char           sFeeType[MAX_SMGP_FEETYPE_LEN];            // 资费类型  15
																			// 1.3: （00：免费，01：按条收费，02：包月，03：封顶）
																			// 2.0: （0：包月，1：按条，2：流量）
	                                                                        // 3.0: （00：免费，01：按条收费，02：包月，03：封顶）

	unsigned char           sFeeCode[MAX_SMGP_FEECODE_LEN];            // 资费代码(分)  21
	unsigned char           sFixedFee[MAX_SMGP_FIXEDFEE_LEN];          // 包月费(封顶费)071051(单位为分)	  27


	unsigned char           ucMsgFormat;                                // 短消息格式(参见短消息格式表) 28
	unsigned char           sValidTime[MAX_SMGP_TIME_LEN];             // 有效时间，遵循SMPP3.3 45
	unsigned char           sAtTime[MAX_SMGP_TIME_LEN];                // 定时发送时间，遵循SMPP3.3  62
	unsigned char           sSrcTermID[MAX_SMGP_TERMINALID_LEN];       // 短消息发送用户号码 83
	unsigned char           sChargeTermID[MAX_SMGP_TERMINALID_LEN];    // 计费用户号码 104
	unsigned char           cDestTermIDCount;                          // 短消息接收号码总数(小于100) 105
	unsigned char           sDestTermID[MAX_SMGP_TERMINALID_LEN*MAX_USER_NUM];      // 短消息接收号码(连续存储DestTermIDCoutn个)
	unsigned char           ucMsgLength;                                // 短消息长度
	unsigned char           sMsgContent[MAX_SMGP_MSGCONTENT_LEN];      // 短消息内容
	unsigned char           sReserve[MAX_SMGP_RESERVE_LEN];            // 保留  
*/
	
	
public class TSMGP_SUBMIT implements SMGPRequestBody {

	public static final Log log = LogFactory.getLog(TSMGP_SUBMIT.class);
	public byte    cMsgType;        // 1.3: 短消息子类型（0＝取消订阅，1＝订阅请求，2＝点播，3＝订阅）  1
                                     // 2.0: 短消息类型（1=取消订阅，2=订阅请求，3=点播，4=订阅，5=交互式操作，6=查询，7=点对点消息，8=电子商务，9=代码方式）
                                     // 3.0: 短消息类型（0=MO消息，6=MT消息，7=点对点消息） 2
	public byte    cNeedReport;     // 是否要求返回状态报告
                                     // 0：不要求  1：要求
	public byte    cPriority;       // 发送优先级（ 1.3: 0-9； 2.0: 0-3； 3.0: 0-3）  3
	public String  sServiceID;      // 服务类型  13
	public String  sFeeType;        // 资费类型  15
			                         // 1.3: （00：免费，01：按条收费，02：包月，03：封顶）
			                         // 2.0: （0：包月，1：按条，2：流量）
                                     // 3.0: （00：免费，01：按条收费，02：包月，03：封顶）

	public String  sFeeCode;        // 资费代码(分)  21
	public String  sFixedFee;       // 包月费(封顶费)071051(单位为分)	  27

	public byte    ucMsgFormat;     // 短消息格式(参见短消息格式表) 28
	public String  sValidTime;      // 有效时间，遵循SMPP3.3 45
	public String  sAtTime;         // 定时发送时间，遵循SMPP3.3  62
    public String  sSrcTermID;      // 短消息发送用户号码 83
    public String  sChargeTermID;   // 计费用户号码 104
    public byte    cDestTermIDCount;// 短消息接收号码总数(小于100) 105
    public String  sDestTermID;     // 短消息接收号码(连续存储DestTermIDCoutn个)
    public byte    ucMsgLength;     // 短消息长度
    public String  sMsgContent;     // 短消息内容
    public String  sReserve;        // 保留  
    public TSMGP_TLV   tsmgpTlv=new TSMGP_TLV();    


    
    
    public byte[] getRequestBody() 
    {
	    byte[] b=sMsgContent.getBytes();
	    ucMsgLength=int2byte(b.length);
        int length =FinalDef.MAX_SMGP_RESERVE_LEN
        +b.length                     
        +FinalDef.MAX_SMGP_TERMINALID_LEN*2
        +FinalDef.MAX_SMGP_TIME_LEN*2
        +FinalDef.MAX_SMGP_FIXEDFEE_LEN
        +FinalDef.MAX_SMGP_FEECODE_LEN
        +FinalDef.MAX_SMGP_FEETYPE_LEN
        +FinalDef.MAX_SMGP_SERVICEID_LEN
        +6
        +tsmgpTlv.getBytes().length;
        
        
    	String[] tempStrs=sDestTermID.split(",");
    	
    	this.cDestTermIDCount=int2byte(tempStrs.length);
    	
    	int n=length+cDestTermIDCount*FinalDef.MAX_SMGP_TERMINALID_LEN;
    	
    	
    	byte[] body = new byte[n];
    	int cur = 0;

   
    	log.info("接收方:" + sDestTermID + ",业务代码:" + sServiceID);
    	
		body[cur] = cMsgType;
		cur += 1;
		
		body[cur] = cNeedReport;
		cur += 1;		
    	
		body[cur] = cPriority;
		cur += 1;    	
    			
		byte[] serviceid = Common.getText(FinalDef.MAX_SMGP_SERVICEID_LEN, sServiceID);
		System.arraycopy(serviceid, 0, body, cur, FinalDef.MAX_SMGP_SERVICEID_LEN);
		cur += FinalDef.MAX_SMGP_SERVICEID_LEN;
    	
		
		byte[] feetype = Common.getText(FinalDef.MAX_SMGP_FEETYPE_LEN, sFeeType);
		System.arraycopy(feetype, 0, body, cur, FinalDef.MAX_SMGP_FEETYPE_LEN);
		cur += FinalDef.MAX_SMGP_FEETYPE_LEN;

		byte[] feecode = Common.getText(FinalDef.MAX_SMGP_FEECODE_LEN, sFeeCode);
		System.arraycopy(feecode, 0, body, cur, FinalDef.MAX_SMGP_FEECODE_LEN);
		cur += FinalDef.MAX_SMGP_FEECODE_LEN;		

		byte[] fixedfee = Common.getText(FinalDef.MAX_SMGP_FIXEDFEE_LEN, sFixedFee);
		System.arraycopy(fixedfee, 0, body, cur, FinalDef.MAX_SMGP_FIXEDFEE_LEN);
		cur += FinalDef.MAX_SMGP_FIXEDFEE_LEN;		
		
		body[cur] = ucMsgFormat;
		cur += 1;   		

		byte[] validtime = Common.getText(FinalDef.MAX_SMGP_TIME_LEN, sValidTime);
		System.arraycopy(validtime, 0, body, cur, FinalDef.MAX_SMGP_TIME_LEN);
		cur += FinalDef.MAX_SMGP_TIME_LEN;	
		
		byte[] attime = Common.getText(FinalDef.MAX_SMGP_TIME_LEN, sAtTime);
		System.arraycopy(attime, 0, body, cur, FinalDef.MAX_SMGP_TIME_LEN);
		cur += FinalDef.MAX_SMGP_TIME_LEN;	
		
		byte[] srctermid = Common.getText(FinalDef.MAX_SMGP_TERMINALID_LEN, sSrcTermID);
		System.arraycopy(srctermid, 0, body, cur, FinalDef.MAX_SMGP_TERMINALID_LEN);
		cur += FinalDef.MAX_SMGP_TERMINALID_LEN;
		
		byte[] chargetermid = Common.getText(FinalDef.MAX_SMGP_TERMINALID_LEN, sChargeTermID);
		System.arraycopy(chargetermid, 0, body, cur, FinalDef.MAX_SMGP_TERMINALID_LEN);
		cur += FinalDef.MAX_SMGP_TERMINALID_LEN;	
		
		
		body[cur] = cDestTermIDCount;
		cur += 1;

	    
	    for (int i=0;i<tempStrs.length;i++)
	    {
			byte[] desttermid = Common.getText(FinalDef.MAX_SMGP_TERMINALID_LEN, tempStrs[i]);
			System.arraycopy(desttermid, 0, body, cur, FinalDef.MAX_SMGP_TERMINALID_LEN);	
			cur += FinalDef.MAX_SMGP_TERMINALID_LEN;    	
	    	
	    }
			

		body[cur] = ucMsgLength;
		cur += 1;
		
		byte[] msgcontent = Common.getText(b.length, sMsgContent);
		System.arraycopy(msgcontent, 0, body, cur, b.length);
		cur += b.length;
		
		byte[] reserve = Common.getText(FinalDef.MAX_SMGP_RESERVE_LEN, sReserve);
		System.arraycopy(reserve, 0, body, cur, FinalDef.MAX_SMGP_RESERVE_LEN);
		cur += FinalDef.MAX_SMGP_RESERVE_LEN;

		byte[] tlv = tsmgpTlv.getBytes();
		System.arraycopy(tlv, 0, body, cur, tlv.length);
		log.info("tlv.length包长度："+tlv.length);
		cur += tlv.length;
		
		log.info(" 组装包长度："+cur);
		
    	return body;
    }
    
    
    
    private static byte int2byte(int n) {
        byte[] b=Common.intToBytes4(n); 
        return b[3];
    }
}
