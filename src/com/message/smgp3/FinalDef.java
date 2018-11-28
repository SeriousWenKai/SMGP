package com.message.smgp3;

public class FinalDef {
	
	/* ----------------------------------------------------------------------------
	  SMGP协议(3.0)基础定义

	  Written     : YC  
	  Revision    : 1.0

	 -----------------------------------------------------------------------------*/
	public static final int   MAXPACKETLEN  = 8192;  

	public static final byte   SMGP_VERSION  =  3;
	
	
	
	public static final short MT		    = 0;
	public static final short MO		    = 1;
	public static final short MTSTATUS	    = 2;
	public static final short MOSTATUS	    = 3;
	public static final short STATUS		= 4;
	public static final short FWDSEND		= 5;
	public static final short FWDRECV		= 6;
	public static final short MTRSP		    = 7;
	public static final short MORSP		    = 8;
	public static final short MTSTATUSRSP	= 9;
	public static final short MOSTATUSRSP	= 10;

	/************************************************************************/
	/*                  与Center之间通讯的错误定义						    */
	/************************************************************************/


	public static final int E_Rsp_OK      =	0;        //对方返回的状态为"DELIVED"
	public static final int E_Rsp_Class	  =	12000;

	/************************************************************************/
	/*                  与SP之间通讯的错误定义						        */
	/************************************************************************/ 	

			

	//cngp defined error code
	public static final int Error_Successed			= 0;	/*消息成功						*/
	public static final int Error_SysBusy			= 1;	/*系统忙						*/
	public static final int Error_Flowover			= 2;	/*超过最大连接数				*/

	public static final int Error_SMsgSTruct		= 10;	/*消息结构错误					*/
	public static final int Error_Command			= 11;	/*命令字错误					*/
	public static final int Error_Serial			= 12;	/*消息序列号重复				*/

	public static final int Error_IpAddr			= 20;	/*IP地址错误					*/
	public static final int Error_Authenticate		= 21;	/*SP认证错误					*/
	public static final int Error_Version			= 22;	/*版本太高						*/
	public static final int Error_FeeId			    = 23;	/*非法FeeUserType				*/
	public static final int Error_Sub			    = 24;	/*非法SubType					*/
	public static final int Error_NodesCount		= 25;	/*NodesCount 超过阈值			*/
	public static final int Error_MsgId			    = 26;	/*非法MsgID						*/

	public static final int Error_SMType			= 30;	/*非法消息类型					*/
	public static final int Error_Priority			= 31;	/*非法优先级					*/
	public static final int Error_FeeType			= 32;	/*非法资费类型					*/
	public static final int Error_FeeCode			= 33;   /*非法资费代码					*/
	public static final int Error_MsgFormat			= 34;	/*非法短消息格式				*/
	public static final int Error_TimeFormat		= 35;	/*非法时间格式					*/
	public static final int Error_MsgLength			= 36;	/*非法短消息长度				*/
	public static final int	Error_OutDate			= 37;	/*有效期已过					*/
	public static final int Error_QueryType			= 38;	/*非法查询类别					*/
	public static final int Error_Route			    = 39;	/*路由错误						*/
	public static final int	Error_FixedFee			= 40;	/*非法包月费/封顶费			*/
	public static final int Error_UpdataType		= 41;	/*非法更新类型					*/
	public static final int Error_RouteId			= 42;	/*非法路由编号					*/
	public static final int Error_ServiceCode		= 43;	/*非法服务代码					*/
	public static final int Error_ValidTime			= 44;   /*非法有效期                 */
	public static final int Error_AtTime			= 45;   /*非法定时发送时间           */
	public static final int Error_SrcTermId			= 46;   /*非法发送用户号码           */
	public static final int Error_DestTermId		= 47;   /*非法接收用户号码			*/
	public static final int Error_ChargeTermId		= 48;   /*非法计费用户号码           */
	public static final int Error_SpCode			= 49;   /*非法SP服务代码				*/
	public static final int Error_SrcGatewayID		= 56;   /*非法源网关代码				*/
	public static final int Error_QueryTermID		= 57;   /*非法查询号码					*/
	public static final int Error_MatchRout			= 58;   /*没有匹配路由					*/
	public static final int Error_SpType			= 59;   /*非法SP类型					*/
	public static final int Error_LastRouteID		= 60;   /*非法上一条路由编号			*/
	public static final int Error_RouteType			= 61;   /*非法路由类型					*/
	public static final int Error_DestGatewayID		= 62;	/*非法目标网关代码			*/
	public static final int Error_DestGateWayIP		= 63;   /*非法目标网关IP				*/
	public static final int Error_DestGatewayPort	= 64;	/*非法目标网关端口			*/
	public static final int Error_TermRangeID		= 65;	/*非法路由号码段				*/
	public static final int Error_ProvinceCode		= 66;	/*非法终端所属省代码			*/
	public static final int Error_UserType			= 67;	/*非法用户类型					*/
	public static final int Error_UpdateRoute		= 68;	/*本节点不支持路由更新		*/
	public static final int Error_SpNumber			= 69;	/*非法SP企业代码				*/
	public static final int	Error_SPAccessType		= 70;	/*非法SP接入类型				*/
	public static final int	Error_UpRouteFale		= 71;	/*路由信息更新失败			*/
	public static final int Error_Time			    = 72;	/*非法时间戳					*/
	public static final int Error_MServiceID		= 73;	/*非法业务代码					*/
	
	
	public static final int ERROR_SOCKET_CREATE     = -74;   /*socket创建失败   */
	public static final int ERROR_CONNECT		    = -75;   /*socket连接失败	   */
	public static final int ERROR_SOCKET_WRITE      = -76;   /*socket写失败         */
	public static final int ERROR_SOCKET_READ       = -77;   /*socket读失败         */
	public static final int ERROR_RSP_TIMEOUT	    = -78;   /*网关超时应答   */
	public static final int ERROR_CONNECT_MOD	    = -79;   /*链接登陆方式错   */
	
	
	/*128-255	厂家定义*/
	public static final int Error_LoginMode		    = 128;	/*非法登录类型					*/
	public static final int Error_MtUserCount		= 129;  /*非法MT短信下发用户数			*/
	public static final int Error_ServiceId			= 130;  /*非法业务类型					*/
	public static final int Error_TerminalId		= 131;  /*非法终端ID					*/
	public static final int Error_SpId			    = 132;  /*非法SPID					*/
	public static final int Error_IsmgId			= 133;  /*非法网关ID					*/
	public static final int Error_OverFlow			= 134;  /*超过设定流量					*/
	public static final int Error_UnKnowCmdId		= 135;  /*非法命令字					*/
	public static final int Error_LoginName         = 136;  /*非法用户名                    */
	public static final int Error_TerminalId1		= 137;  /*非法终端ID					*/
	public static final int Error_TerminalId2		= 138;  /*非法终端ID					*/
	public static final int Error_TerminalId3		= 139;  /*非法终端ID					*/
	public static final int Error_TerminalId4		= 140;  /*非法终端ID					*/

	/*****************************************************************************************************************************
	                                 操作码
	******************************************************************************************************************************/
	public static final int CMD_SMGP_LOGIN	                  = 0x00000001;    // 客户端登录请求
	public static final int CMD_SMGP_LOGIN_RESP	              = 0x80000001;    // 请求连接应答
	public static final int CMD_SMGP_SUBMIT                   = 0x00000002;    // SP发送短信请求
	public static final int CMD_SMGP_SUBMIT_RESP              = 0x80000002;    // SP发送短信应答
	public static final int CMD_SMGP_DELIVER                  = 0x00000003;    // SMGW向SP发送短信请求
	public static final int CMD_SMGP_DELIVER_RESP             = 0x80000003;    // SMGW向SP发送短信应答
	public static final int CMD_SMGP_ACTIVE_TEST              = 0x00000004;    // 测试链路
	public static final int CMD_SMGP_ACTIVE_TEST_RESP         = 0x80000004;    // 测试链路应答
	public static final int CMD_SMGP_FORWARD                  = 0x00000005;    // SMGW转发短信请求
	public static final int CMD_SMGP_FORWARD_RESP             = 0x80000005;    // SMGW转发短信应答
	public static final int CMD_SMGP_EXIT		              = 0x00000006;    // 退出请求
	public static final int CMD_SMGP_EXIT_RESP	              = 0x80000006;    // 退出应答
	public static final int CMD_SMGP_QUERY		              = 0x00000007;    // sp统计查询请求
	public static final int CMD_SMGP_QUERY_RESP	              = 0x80000007;	   // sp统计查询应答
	public static final int CMD_SMGP_MT_ROUTE_UPDATE          = 0x00000008;	   // MT路由更新请求
	public static final int CMD_SMGP_MT_ROUTE_UPDATE_RESP     = 0x80000008;	   // MT路由更新应答
	public static final int CMD_SMGP_MO_ROUTE_UPDATE	      = 0x00000009;    // MO路由更新请求
	public static final int CMD_SMGP_MO_ROUTE_UPDATE_RESP     = 0x80000009;	   // MO路由更新应答

	/*****************************************************************************************************************************
	                                  状态错误码
	******************************************************************************************************************************/
	//非法短消息长度（MsgLength）
	public static final int ERR_SMGP_STATUS_MSGLEN           = 36;



	/*****************************************************************************************************************************
	                                 长度定义
	******************************************************************************************************************************/
	public static final int  MAX_SMGP_MSGID_LEN              = 10;
	public static final int  MAX_SMGP_CLIENTID_LEN           = 8;
	public static final int  MAX_SMGP_PASSWORD_LEN           = 15;
	public static final int  MAX_SMGP_AUTH_LEN               = 16;
	public static final int  MAX_SMGP_MSGCONTENT_LEN         = 252;
	public static final int  MAX_SMGP_ISMGCODE_LEN           = 6;
	public static final int  MAX_SMGP_SERVICEID_LEN          = 10;
	public static final int  MAX_SMGP_FEETYPE_LEN            = 2;
	public static final int  MAX_SMGP_FEECODE_LEN            = 6;
	public static final int  MAX_SMGP_FIXEDFEE_LEN           = 6;
	public static final int  MAX_SMGP_TIME_LEN               = 17;
	public static final int  MAX_SMGP_TERMINALID_LEN         = 21;
	public static final int  MAX_SMGP_RESERVE_LEN            = 8;
	public static final int  MAX_SMGP_RECVTIME_LEN           = 14;
	public static final int  SMGP_HEAD_LEN			         = 12;    /*smgp协议包头长度						*/
	public static final int  MAX_AUTHENTICATOR_LEN		     = 16;	  /*MD5加密字段长度						*/
	public static final int  MAX_USER_NUM		             = 100;	  /* 最大下发用户数                       */
	public static final int  MAX_SUMBIT_LEN		             = 2466;  /* 最大SUBMIT包长度						*/
	
	
	public static final int  LINKID_LENGTH                   = 20;
	public static final int  MASK_LENGTH                     = 32;
	public static final int  MSG_SRC_LENGTH                  = 21;
	/*****************************************************************************************************************************
	                                  状态报告长度定义
	******************************************************************************************************************************/
	public static final int  SEQ_LEN                         = 10;
	public static final int  STAT_LEN                        = 7;
	public static final int  DATE_LEN                        = 10;
	public static final int  TEXT_LEN                        = 20;
	public static final int  SUB_LEN                         = 3;
	public static final int  DLVRD_LEN                       = 3;
	public static final int  ERR_LEN                         = 3;


	public static final int  MAX_SMGP_DESTTERM_NUM           = 100;          // 最大接收号码数量


	/*****************************************************************************************************************************
	                                  Msg_id的生成算法
	******************************************************************************************************************************/
	// Msg_id由三部分组成: 
	// SMGW代码:3字节(BCD码);
	// 时间:4字节(BCD码)，格式为MMDDHHMI（月日时分）
	// 序列号:3字节(BCD码)，取值范围为：000000-999999
}
