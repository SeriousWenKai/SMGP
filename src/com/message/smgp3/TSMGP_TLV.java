package com.message.smgp3;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.util.Common;

/**
 * 
 	UINT8   cPid;                               //PID
	bool    b_cPid;                           //cPid是否使用
	UINT8   cUdhi;                              //UDHI
	bool    b_cUdhi;                              //cUdhi是否使用
	INT8    strLinkId[LINKID_LENGTH];           //DSMP的LinkId
	bool    b_strLinkId;                         //
	UINT8   cFeeFlag;                           //计费方式
	bool    b_cFeeFlag;
	//0：对目的终端MSISDN计费
	//1：对源终端MSISDN计费
	//2：对SP计费
	//3：表示本字段无效，对谁计费参见被计费用户的号码
	UINT8   cFeeMaskFlag;                          //号码类型
	bool    b_cFeeMaskFlag;                        
	//计费用户的号码类型。
	// 0＝真实号码；
	// 1＝伪码；
	// 其它保留
	UINT8   strFeeNumberMask[MASK_LENGTH];      //计费号码伪码
	bool    b_strFeeNumberMask;              
	UINT8   cDestMaskFlag;                          //号码类型
	bool    b_cDestMaskFlag;
	//目的用户的号码类型。
	// 0＝真实号码；
	// 1＝伪码；
	// 其它保留
	UINT8   strDestNumberMask[MASK_LENGTH];      //目的号码伪码
	bool    b_strDestNumberMask; 
	UINT8   cPkTotal;                           //SP协议版本
	bool    b_cPkTotal;
	UINT8   cPkNumber;                          //SP协议版本
	bool    b_cPkNumber;
	UINT8	cMsgType;
	bool    b_cMsgType;
	UINT8	cSpDealResult;
	bool    b_cSpDealResult;
	UINT8   cSrcMaskFlag;                          //号码类型
	bool    b_cSrcMaskFlag;
	//源用户的号码类型。
	// 0＝真实号码；
	// 1＝伪码；
	// 其它保留
	UINT8   strSrcNumberMask[MASK_LENGTH];      //源号码伪码
	bool    b_strSrcNumberMask;
	UINT8	cNodesCount;
	bool    b_cNodesCount;
	INT8    strMsgSrc[MSG_SRC_LENGTH];          //信息来源164
	bool    b_strMsgSrc;
	UINT8	cSpMaskFlag;
	bool    b_cSpMaskFlag;
	UINT8	cMServiceID;
	bool    b_cMServiceID;
 *
 */
public class TSMGP_TLV {
	public byte        cPid;                               //PID
	public boolean     b_cPid=false;                          //cPid是否使用
	public byte        cUdhi;                              //UDHI
	public boolean     b_cUdhi=false;                             //cUdhi是否使用
	public String      strLinkId;           //DSMP的LinkId
	public boolean     b_strLinkId=false;                        //
	public byte        cFeeFlag;                           //计费方式
	public boolean     b_cFeeFlag=false;
	//0：对目的终端MSISDN计费
	//1：对源终端MSISDN计费
	//2：对SP计费
	//3：表示本字段无效，对谁计费参见被计费用户的号码
	public byte        cFeeMaskFlag;                          //号码类型
	public boolean     b_cFeeMaskFlag=false;                     
	//计费用户的号码类型。
	// 0＝真实号码；
	// 1＝伪码；
	// 其它保留
	public String      strFeeNumberMask;      //计费号码伪码
	public boolean     b_strFeeNumberMask=false;             
	public byte        cDestMaskFlag;                          //号码类型
	public boolean     b_cDestMaskFlag=false;
	//目的用户的号码类型。
	// 0＝真实号码；
	// 1＝伪码；
	// 其它保留
	public String      strDestNumberMask;      //目的号码伪码
	public boolean     b_strDestNumberMask=false;
	public byte        cPkTotal;                           //SP协议版本
	public boolean     b_cPkTotal=false;
	public byte        cPkNumber;                          //SP协议版本
	public boolean     b_cPkNumber=false;
	public byte	       cMsgType;
	public boolean     b_cMsgType=false;
	public byte	       cSpDealResult;
	public boolean     b_cSpDealResult=false;
	public byte        cSrcMaskFlag;                          //号码类型
	public boolean     b_cSrcMaskFlag=false;
	//源用户的号码类型。
	// 0＝真实号码；
	// 1＝伪码；
	// 其它保留
	public String      strSrcNumberMask;      //源号码伪码
	public boolean     b_strSrcNumberMask=false;
	public byte	       cNodesCount;
	public boolean     b_cNodesCount=false;
	public String      strMsgSrc;          //信息来源164
	public boolean     b_strMsgSrc=false;
	public byte	       cSpMaskFlag;
	public boolean     b_cSpMaskFlag=false;
	public byte	       cMServiceID;
	public boolean     b_cMServiceID=false;
	
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(TSMGP_TLV.class);
    
    
	public byte[] getBytes()
	{
		
		ArrayList alist=new ArrayList();
		int pos=0;
		
		
		if (b_cPid)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x001;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cPid;
			
			alist.add(tempBuf);
		}
		
		
		if (b_cUdhi)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x002;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cUdhi;	
			alist.add(tempBuf);
		}		
		

		if (b_strLinkId)
		{
			byte[] tempBuf=new byte[4+FinalDef.LINKID_LENGTH];
			short  tag=0x003;
			short  length=FinalDef.LINKID_LENGTH;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			System.arraycopy(strLinkId.getBytes(), 0, tempBuf, 4, strLinkId.getBytes().length);	
			alist.add(tempBuf);
		}
		
		if (b_cFeeFlag)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x004;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cFeeFlag;	
			alist.add(tempBuf);
		}			
		
		
		if (b_cFeeMaskFlag)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x005;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cFeeMaskFlag;	
			alist.add(tempBuf);
		}			
		
		if (b_strFeeNumberMask)
		{
			byte[] tempBuf=new byte[4+FinalDef.MASK_LENGTH];
			short  tag=0x006;
			short  length=FinalDef.MASK_LENGTH;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			System.arraycopy(strFeeNumberMask.getBytes(), 0, tempBuf, 4, strFeeNumberMask.getBytes().length);	
			alist.add(tempBuf);
		}	
		
		if (b_cDestMaskFlag)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x007;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cDestMaskFlag;	
			alist.add(tempBuf);
		}	
		
		if (b_strDestNumberMask)
		{
			byte[] tempBuf=new byte[4+FinalDef.MASK_LENGTH];
			short  tag=0x008;
			short  length=FinalDef.MASK_LENGTH;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			System.arraycopy(strDestNumberMask.getBytes(), 0, tempBuf, 4, strDestNumberMask.getBytes().length);	
			alist.add(tempBuf);
		}
		
		
		if (b_cPkTotal)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x009;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cPkTotal;	
			alist.add(tempBuf);
		}			
				

		if (b_cPkNumber)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x00a;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cPkNumber;	
			alist.add(tempBuf);
		}			
			
		
		if (b_cMsgType)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x00b;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cMsgType;	
			alist.add(tempBuf);
		}			
			
		
		if (b_cSpDealResult)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x00c;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cSpDealResult;	
			alist.add(tempBuf);
		}			
			
		if (b_cSrcMaskFlag)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x00d;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cSrcMaskFlag;	
			alist.add(tempBuf);
		}		

		
		
		if (b_strSrcNumberMask)
		{
			byte[] tempBuf=new byte[4+FinalDef.MASK_LENGTH];
			short  tag=0x00e;
			short  length=FinalDef.MASK_LENGTH;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			System.arraycopy(strSrcNumberMask.getBytes(), 0, tempBuf, 4, strSrcNumberMask.getBytes().length);	
			alist.add(tempBuf);
		}		
		
		
		if (b_cNodesCount)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x00f;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cNodesCount;	
			alist.add(tempBuf);
		}			
			
		
		if (b_strMsgSrc)
		{
			byte[] tempBuf=new byte[4+FinalDef.MSG_SRC_LENGTH];
			short  tag=0x010;
			short  length=FinalDef.MSG_SRC_LENGTH;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			System.arraycopy(strMsgSrc.getBytes(), 0, tempBuf, 4, strMsgSrc.getBytes().length);	
			alist.add(tempBuf);
		}		
		
		
		if (b_cSpMaskFlag)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x011;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cSpMaskFlag;	
			alist.add(tempBuf);
		}	
		
		
		if (b_cMServiceID)
		{
			byte[] tempBuf=new byte[5];
			short  tag=0x012;
			short  length=1;
			System.arraycopy(Common.short2Byte(tag), 0, tempBuf, 0, 2);
			System.arraycopy(Common.short2Byte(length), 0, tempBuf, 2, 2);
			tempBuf[4]=	cMServiceID;	
			alist.add(tempBuf);
		}	

		
		
		int bytesLen=0;
		for(int i=0;i<alist.size();i++)
		{
			byte[] bs=(byte[])alist.get(i);
			bytesLen+=bs.length;
		}
		
		
		log.debug("TLV 包长度="+bytesLen);
		
		
		byte[] ret=new byte[bytesLen];
		pos=0;
		for(int i=0;i<alist.size();i++)
		{
			byte[] bs=(byte[])alist.get(i);
			
			System.arraycopy(bs,0, ret, pos,bs.length);
			
			pos+=bs.length;
		}		
		return ret;
		
	}
	
	
	
	
	public void parseBytes(byte[] packet)
	{
		int pos=0;
		
		while (pos<packet.length)
		{
			byte[] tagBytes=new byte[2];
			System.arraycopy(packet,pos, tagBytes, 0,2);
			pos+=2;
			byte[] lenBytes=new byte[2];
			System.arraycopy(packet,pos, lenBytes, 0,2);
			pos+=2;
			short tag=Common.bytes2Short(tagBytes);
			short len=Common.bytes2Short(lenBytes);
			
			switch(tag)
			{
			   case 0x001:
			   {			   
				   cPid=packet[pos];
				   pos+=len;
				   b_cPid=true;
			   }break;
			   case 0x002:
			   {
				   cUdhi=packet[pos];
				   pos+=len;		
				   b_cUdhi=true;
			   }break;
			   case 0x003:
			   {
				   byte[] tempbytes=new byte[len];
				   System.arraycopy(packet,pos, tempbytes, 0,len);
				   pos+=len;
				   strLinkId=Common.getStr(tempbytes);
				   b_strLinkId=true;
			   }break;
			   case 0x004:
			   {
				   cFeeFlag=packet[pos];
				   pos+=len;				   
				   b_cFeeFlag=true;
			   }break;
			   case 0x005:
			   {
				   cFeeMaskFlag=packet[pos];
				   pos+=len;	
				   b_cFeeMaskFlag=true;
			   }break;
			   case 0x006:
			   {
				   byte[] tempbytes=new byte[len];
				   System.arraycopy(packet,pos, tempbytes, 0,len);
				   pos+=len;
				   strFeeNumberMask=Common.getStr(tempbytes);	
				   b_strFeeNumberMask=true;
			   }break;
			   case 0x007:
			   {
				   cDestMaskFlag=packet[pos];
				   pos+=len;	
				   b_cDestMaskFlag=true;
			   }break;
			   case 0x008:
			   {
				   byte[] tempbytes=new byte[len];
				   System.arraycopy(packet,pos, tempbytes, 0,len);
				   pos+=len;
				   strDestNumberMask=Common.getStr(tempbytes);	
				   b_strDestNumberMask=true;
			   }break;
			   case 0x009:
			   {
				   cPkTotal=packet[pos];
				   pos+=len;	
				   b_cPkTotal=true;
			   }break;
			   case 0x00a:
			   {
				   cPkNumber=packet[pos];				   
				   pos+=len;
				   b_cPkNumber=true;			   
			   }break;
			   case 0x00b:
			   {
				   cMsgType=packet[pos];
				   pos+=len;		
				   b_cMsgType=true;
			   }break;
			   case 0x00c:
			   {
				   cSpDealResult=packet[pos];
				   pos+=len;				   
				   b_cSpDealResult=true;
			   }break;
			   case 0x00d:
			   {
				   cSrcMaskFlag=packet[pos];
				   pos+=len;				   
				   b_cSrcMaskFlag=true;
			   }break;
			   case 0x00e:
			   {
				   byte[] tempbytes=new byte[len];
				   System.arraycopy(packet,pos, tempbytes, 0,len);
				   pos+=len;
				   strSrcNumberMask=Common.getStr(tempbytes);	
				   b_strSrcNumberMask=true;
			   }break;
			   case 0x00f:
			   {
				   cNodesCount=packet[pos];
				   pos+=len;				   
				   b_cNodesCount=true;
			   }break;
			   case 0x010:
			   {
				   byte[] tempbytes=new byte[len];
				   System.arraycopy(packet,pos, tempbytes, 0,len);
				   pos+=len;
				   strMsgSrc=Common.getStr(tempbytes);	
				   b_strMsgSrc=true;
			   }break;
			   case 0x011:
			   {
				   cSpMaskFlag=packet[pos];
				   pos+=len;				   
				   b_cSpMaskFlag=true;
			   }break;
			   case 0x012:
			   {
				   cMServiceID=packet[pos];
				   pos+=len;	
				   b_cMServiceID=true;
			   }break;
			   default:
			   {
				   log.warn("无效TLV标记:"+tag);
				   pos+=len;
			   }break;
	   
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
