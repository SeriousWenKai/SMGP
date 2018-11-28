/*
 * NAME: com.message.cmpp.TestMO.java Company:SXIT
 */

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.message.smgp3.SMGP3;
import com.message.smgp3.TSMGP_DELIVER;
import com.message.smgp3.TSMGP_STATUS;
import com.message.util.Common;

/**
 * 处理从网关返回来的消息
 * 
 * @author YC
 * @version 1.0 (2005-3-31 13:48:40)
 */
public class TestMO {
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(TestMO.class);


    private static int delayTime = 10;

    //以上参数也可以从配置文件处取得

    public static void main(String args[]) throws IOException {
        System.out.println(" MO-TEST Start ");
        SMGP3 smgp=new SMGP3();
        int socketID=smgp.SMGP_Connect("",8890, "test", "test",(byte)1);
        
        log.info("连接ISMG返回值："+socketID);
        int count = 0;
        long beginTime = System.currentTimeMillis();
        while (true) {
            synchronized (smgp.socket) {//同步socket连接

                // 有数据就读出来，没有数据的话，等待50毫秒
                long now = System.currentTimeMillis();
                try {
                    int available = smgp.socket.getInputStream().available();

                    if (available > 0) {
                    	TSMGP_DELIVER deliver = new TSMGP_DELIVER();
                    	TSMGP_STATUS  status =new TSMGP_STATUS();
                    	
                    	int mo_ret=smgp.SGIP_Get_MO(socketID, deliver, status);
                    	
                    	log.info("MO返回：mo_ret="+mo_ret);
                    	
                    	if (mo_ret==0)
                    	{                  		
                    		log.info("MSGID："+deliver.sMsgID+" 短信 内容："+deliver.sMsgContent);
                    		
                    		//收到mo消息 给网关发送响应包
                    		smgp.SMGPDeliverResp(deliver.sMsgID, smgp.lSerial_ID, 0);
                    		
                    		
                            if (deliver.tsmgpTlv.b_cDestMaskFlag){
                                log.info("tlv.cDestMaskFlag="+deliver.tsmgpTlv.cDestMaskFlag);}
                            
                                if (deliver.tsmgpTlv.b_cFeeFlag){
                                log.info("tlv.cFeeFlag="+deliver.tsmgpTlv.cFeeFlag);}
                                
                                if (deliver.tsmgpTlv.b_cFeeMaskFlag){
                                log.info("tlv.cFeeMaskFlag="+deliver.tsmgpTlv.cFeeMaskFlag);}
                                
                             
                                if (deliver.tsmgpTlv.b_cMServiceID){
                                log.info("tlv.cMServiceID="+deliver.tsmgpTlv.cMServiceID);}
                                
                                if (deliver.tsmgpTlv.b_cMsgType){
                                log.info("tlv.cMsgType="+deliver.tsmgpTlv.cMsgType);}
                                
                                if (deliver.tsmgpTlv.b_cNodesCount){
                                log.info("tlv.cNodesCount="+deliver.tsmgpTlv.cNodesCount);}
                                
                                
                                if (deliver.tsmgpTlv.b_cPid){
                                log.info("tlv.cPid="+deliver.tsmgpTlv.cPid);}
                                
                                if (deliver.tsmgpTlv.b_cPkNumber){
                                log.info("tlv.cPkNumber="+deliver.tsmgpTlv.cPkNumber);}
                                
                                if (deliver.tsmgpTlv.b_cPkTotal){
                                log.info("tlv.cPkTotal="+deliver.tsmgpTlv.cPkTotal);}
                                
                                if (deliver.tsmgpTlv.b_cSpDealResult){
                                log.info("tlv.cSpDealResult="+deliver.tsmgpTlv.cSpDealResult);}
                                
                                if (deliver.tsmgpTlv.b_cSpMaskFlag){
                                log.info("tlv.cSpMaskFlag="+deliver.tsmgpTlv.cSpMaskFlag);}
                                
                                if (deliver.tsmgpTlv.b_cSrcMaskFlag){
                                log.info("tlv.cSrcMaskFlag="+deliver.tsmgpTlv.cSrcMaskFlag);}
                                
                                if (deliver.tsmgpTlv.b_cUdhi){
                                log.info("tlv.cUdhi="+deliver.tsmgpTlv.cUdhi);}
                                
                                if (deliver.tsmgpTlv.b_strDestNumberMask){
                                log.info("tlv.strDestNumberMask="+deliver.tsmgpTlv.strDestNumberMask);}
                                
                                if (deliver.tsmgpTlv.b_strMsgSrc){
                                	log.info("tlv.strMsgSrc="+deliver.tsmgpTlv.strMsgSrc);}
                                
                                if (deliver.tsmgpTlv.b_strFeeNumberMask){
                                	log.info("tlv.strFeeNumberMask="+deliver.tsmgpTlv.strFeeNumberMask);}
                                
                                if (deliver.tsmgpTlv.b_strLinkId){
                                	log.info("tlv.strLinkId="+deliver.tsmgpTlv.strLinkId);}
                                
                                if (deliver.tsmgpTlv.b_strSrcNumberMask){
                                	log.info("tlv.strSrcNumberMask="+deliver.tsmgpTlv.strSrcNumberMask);}
                    		
                    	}
                    	if (mo_ret==1)
                    	{
                    		log.info("MSGID："+status.sMsgID+" 短信 状态："+status.sStat);
                    	}
                    	if (mo_ret==3)
                    	{
                    		log.info(" 链路测试包 ");
                    	}
                    	if (mo_ret==2)
                    	{
                    		log.info(" 无消息包 ");
                    	}
                    	
                    }
                    //如果1分钟之内没有输入流到达，则发送active包
                    else if ( now - beginTime >= 10 * 1000) {
                        beginTime = now;
                        log.info("10秒钟都没有收到包，发送链路检测包!");
                        int test_ret=smgp.SMGPActiveTest(socketID);
                        log.info("测试链路返回值："+test_ret);
                       
                    }
                    else {
                        //休眠50秒，等待下一个deliver消息的到来
                        try {
                            Thread.sleep(50);
                        }
                        catch (InterruptedException e) {
                            log.error(e.toString());
                        }
                    }
                }
                //如果异常的引起原因是连接关掉了等，则重新建立起连接
                catch (IOException e) {
                    log.error(e.toString());
                    //                    log.error("socket.isClosed()=" + socket.isClosed());
                    //                    log.error("socket.isConnected()=" + socket.isConnected());
                    //                    if (socket.isClosed() || !socket.isConnected()) {//如果关闭了或者连接掉了
                    try {
                        smgp.SMGP_Disconnect(socketID);
                    }
                    catch (Exception ee) {
                        log.error(ee.toString());
                    }
                    //                    }
                }
            }
        }

    }


    private static String bytes2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            sb.append(Common.byte2hex(b[i]) + " ");
        return sb.toString();
    }

}