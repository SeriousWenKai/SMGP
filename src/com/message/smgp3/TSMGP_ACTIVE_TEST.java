package com.message.smgp3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.smgp3.packet.SMGPRequestBody;

/**
 * 
 * @author YC
 * @version 1.0
 */
public class TSMGP_ACTIVE_TEST implements SMGPRequestBody {

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(TSMGP_ACTIVE_TEST.class);

	public TSMGP_ACTIVE_TEST() 
	{			 
	}
	public byte[] getRequestBody() {
		return new byte[0];
	}

}