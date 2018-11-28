package com.message.smgp3;

import com.message.smgp3.packet.SMGPRequestBody;
import com.message.util.Common;

public class TSMGP_DELIVER_RESP implements SMGPRequestBody{
	 public String   sMsgID;  //短消息标识
	 public int      lStatus = 0 ; // 返回结果(参见错误代码)
	
        	
	 public byte[] getRequestBody() 
	    {
		    byte[] body = new byte[FinalDef.MAX_SMGP_MSGID_LEN+4];
		    int pos=0;
		    

		    byte[] temp = Common.getByte(sMsgID);
		    System.arraycopy(temp, 0, body, pos, temp.length);
		    
		    pos+=FinalDef.MAX_SMGP_MSGID_LEN;
		    
		    temp = Common.intToBytes4(lStatus);
		    System.arraycopy(temp, 0, body, pos, temp.length);

		    return body;
	    }
}
