package com.message.smgp3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.smgp3.packet.SMGPRequestBody;
import com.message.util.Common;

/**
 * 
 * @author YC
 * @version 1.0
 */
public class TSMGP_LOGIN implements SMGPRequestBody {

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(TSMGP_LOGIN.class);
	
	/**
	 * 企业代码  SP编号或者SMGW编号
	 */
	private String sClientID;

	/**
	 * 密码    用于鉴别客户端的密码
	 */
	private String password;
	
	/**
	 * 登陆类型  
	 *  0：发送短消息
	 *  1：接收短消息
	 *	2：转发短消息
	 */
	private byte cLoginMode=2; 
	

	/**
	 * 时间戳   时间戳的明文MMDDHHMMSS
	 */
	private String lTimeStamp;
	
	
	
	/**
	 * 版本    客户端支持的版本号
	 */
	private byte cVersion;
	
	private int length = FinalDef.MAX_SMGP_CLIENTID_LEN+FinalDef.MAX_SMGP_AUTH_LEN+6;
	

	/**
	 * 
	 * @param spid
	 *            
	 * @param password
	 */
	public TSMGP_LOGIN(String sClientID, String password) {
		this.sClientID = sClientID;
		this.password = password;
		this.cVersion = FinalDef.SMGP_VERSION;
		SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
		Date currentTime = new Date();
		this.lTimeStamp = formatter.format(currentTime);
		 
	}

	public byte[] getRequestBody() {
		byte[] body = new byte[length];

		int cur = 0;
		
		byte[] sp = Common.getText(FinalDef.MAX_SMGP_CLIENTID_LEN, sClientID);
		if (log.isDebugEnabled())
			log.debug("企业代码：" + sClientID + ",密码：" + password+",SP:"+Common.bytes2hex(sp));
		System.arraycopy(sp, 0, body, cur, sp.length);
		cur += sp.length;

//		byte[] authen = md5(sp);
		byte[] authen=getMd5();
		System.arraycopy(authen, 0, body, cur, authen.length);
		cur += authen.length;

		// byte[] ver = { (byte) version };
		// System.arraycopy(ver, 0, body, cur, ver.length);
		
		body[cur] =  cLoginMode;
		cur += 1;		
		
		
		byte btimestamp[] = Common.intToBytes4(Integer.parseInt(lTimeStamp));
		System.arraycopy(btimestamp, 0, body, cur, btimestamp.length);
		cur+=btimestamp.length;
		
		
		body[cur] =  cVersion;
		cur += 1;



		return body;
	}

	/**
	 * 得到md5后的16位byte型数组
	 * 
	 * @param sp
	 * @return
	 */

	private byte[] getMd5() {
		byte sp[]=sClientID.getBytes();
		byte bzero[] = new byte[7];
		byte[] bSPpassword = password.getBytes();
//		System.out.println("============================================================");
		byte btimestamp[] = (lTimeStamp).getBytes();
		byte bmd5[] = new byte[sp.length + 7 + bSPpassword.length + btimestamp.length];
		int cur = 0;
		System.arraycopy(sp, 0, bmd5, cur, sp.length);
		cur += sp.length;
		System.arraycopy(bzero, 0, bmd5, cur, 7);
		cur += bzero.length;
		System.arraycopy(bSPpassword, 0, bmd5, cur, bSPpassword.length);
		cur += bSPpassword.length;
		System.arraycopy(btimestamp, 0, bmd5, cur, btimestamp.length);
		byte[] result = new byte[16];
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(bmd5);
			result = md.digest();
			if(log.isDebugEnabled()){
				log.debug("md5散列码："+Common.bytes2hex(result));
			}
		}
		catch (NoSuchAlgorithmException e) {
			log.error(e.toString());
		}
		return result;

	}

	public byte getCLoginMode() {
		return cLoginMode;
	}

	public void setCLoginMode(byte loginMode) {
		cLoginMode = loginMode;
	}
}