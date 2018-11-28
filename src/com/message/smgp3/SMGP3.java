package com.message.smgp3;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.smgp3.packet.SMGPRequestPacket;
import com.message.smgp3.socket.SMGPSocket;
import com.message.util.Common;


/**
 * SMGP协议api,通过调用此api实现SMGP协议的各个消息 <br>
 * 在调用此类之前,必须先调用SMGPSocket类的initialSock方法,实现和网关socket连接的初始化
 *
 * @author YC
 * @version 1.0 (2005-3-29 8:59:06)
 */
public class SMGP3 {
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(SMGP3.class);

    /**
     * smgp的socket连接
     */
    public SMGPSocket socket;

    /**
     * smgp请求包体
     */

    public SMGPRequestPacket packet;

    /**
     * response消息的最大返回时间，单位为秒
     */
    public int delayTime = 10;

    /**
     * 服务器分配的SOCKET号  由SMGP_Connect得到
     */
    public int socketId;


    public int lSerial_ID; //当前处理包序列号

    /**
     * 构造函数
     *
     * @param socket    发送和接收smgp消息的时候，使用的socket连接
     * @param delayTime 接收response消息的最大返回时间
     */
    public SMGP3(SMGPSocket socket, int delayTime) {
        this.socket = socket;
        this.packet = new SMGPRequestPacket();
        this.delayTime = delayTime;

    }

    /**
     * 构造函数
     *
     * @param socket 发送和接收cmpp消息的时候，使用的socket连接
     */
    public SMGP3(SMGPSocket socket) {
        this.socket = socket;
        this.packet = new SMGPRequestPacket();
        this.delayTime = 10;//默认时间为10秒
    }


    public SMGP3() {
    }


    /**
     * 返回值：
     * <p>
     * ERROR_SOCKET_CREATE 	socket创建失败
     * ERROR_CONNECT		socket连接失败
     * ERROR_SOCKET_WRITE   socket写失败
     * ERROR_SOCKET_READ	socket读失败
     * ERROR_RSP_TIMEOUT	网关超时应答
     * ERROR_CONNECT_MOD	链接登陆方式错
     * 0                    socket操作成功的socket号
     * <p>
     * 函数参数：
     * param1:  网关IP
     * param2:  网关端口
     * param3:  SP的用户名（非SP_ID）
     * param4:  SP的密码
     * param5:  连接方式  0：发送短消息（send mode）；
     * 1：接收短消息（receive mode）；
     * 2：收发短消息（transmit mode）；
     */
    public int SMGP_Connect(String host, int port, String spid, String password, byte longinMode) throws IOException {
        this.socket = new SMGPSocket(host, port);
        switch (this.socket.initialSock()) {
            case -1:
                return FinalDef.ERROR_SOCKET_CREATE;
            case -2:
                return FinalDef.ERROR_CONNECT;
            case -3:
                return FinalDef.ERROR_SOCKET_CREATE;
        }
        this.packet = new SMGPRequestPacket();
        log.debug("FinalDef.CMD_SMGP_LOGIN==" + FinalDef.CMD_SMGP_LOGIN);
        packet.setLCommand_ID(FinalDef.CMD_SMGP_LOGIN);
        packet.setLSerial_ID(getSequence());
        TSMGP_LOGIN login = new TSMGP_LOGIN(spid, password);
        login.setCLoginMode(longinMode);
        packet.setRequestBody(login);

        try {
            synchronized (socket) {
                socket.write(packet);
                // long beginTime = System.currentTimeMillis();
                TSMGP_LOGIN_RESP resp = new TSMGP_LOGIN_RESP();
                long begin = System.currentTimeMillis();
                // 循环等待10秒,超过10秒,认为这条短信发送失败
                while (true) {
                    try {
                        long now = System.currentTimeMillis();

                        if (socket.getInputStream().available() >= 33) {
                            if (log.isDebugEnabled())
                                log.debug("输入流长度为:" + socket.getInputStream().available());
                            byte[] packetbytes = new byte[33];
                            java.io.InputStream out = socket.getInputStream();
                            out.read(packetbytes);

                            resp.parseResponseBody(packetbytes);
                            if (resp.lStatus == 0) {
                                return getSocketID();
                            }
                            return resp.lStatus;
                        } else if (now - begin > delayTime * 1000) {
                            log.warn("读取connectresp消息时阻塞,返回");
                            return FinalDef.ERROR_RSP_TIMEOUT;
                        }
                    } catch (IOException e) {
                        log.error("connectresp io error:" + e.toString());
                        return FinalDef.ERROR_SOCKET_READ;
                    }
                }
            }
        } catch (IOException e) {
            log.error("connectresp io error:" + e.toString());
            return FinalDef.ERROR_SOCKET_WRITE;
        }

    }


    /**
     * 返回值：
     * ERROR_SOCKET_WRITE		socket写失败
     * ERROR_SOCKET_READ		socket读失败
     * 0       						得到MO消息
     * 1       						得到状态报告
     * 2								无消息返回
     * 3       						返回非DELIVER消息
     * <p>
     * 函数参数：
     * param1: 	socket号，由SGIP_Connect函数返回.
     * param2:		返回DELIVER消息包。
     */


    public int SGIP_Get_MO(int socketid, TSMGP_DELIVER smgp_deliver, TSMGP_STATUS smgp_status) {
        int ret = -1;
        byte[] resppacket = null;
        long now = System.currentTimeMillis();
        int commandID;
        int packetLen;

        try {
            synchronized (socket) {
                int available = socket.getInputStream().available();
                java.io.InputStream out = socket.getInputStream();
                if (available >= 12) {
                    byte[] headpacket = new byte[12];
                    out.read(headpacket); //读取包头
            
            /*解析包长度*/
                    byte[] packetlen = new byte[4];
                    System.arraycopy(headpacket, 0, packetlen, 0, 4);
                    packetLen = Common.bytes4ToInt(packetlen);
                    if (log.isDebugEnabled()) {
                        log.debug("返回包的长度为：" + packetlen);
                    }

                    byte[] commandid = new byte[4];
                    System.arraycopy(headpacket, 4, commandid, 0, 4);
                    commandID = Common.bytes4ToInt(commandid);
                    byte[] serialid = new byte[4];
                    System.arraycopy(headpacket, 8, serialid, 0, 4);
                    this.lSerial_ID = Common.bytes4ToInt(serialid);


                    switch (commandID) {
                        case FinalDef.CMD_SMGP_DELIVER: {
                            log.info("收到deliver消息");


                            byte[] deliver_packet = new byte[packetLen - 12];

                            out.read(deliver_packet);

                            smgp_deliver.parsePacketBody(deliver_packet);
                            if (smgp_deliver.cIsReport == 1) {

                                log.debug("smgp_deliver.sMsgContent.length()=" + smgp_deliver.ucMsgLength);
                                smgp_status.parseResponseBody(Common.getText(smgp_deliver.ucMsgLength, smgp_deliver.sMsgContent));
                                ret = 1;
                            } else {
                                ret = 0;
                            }

                        }
                        break;
                        case FinalDef.CMD_SMGP_ACTIVE_TEST: {
                            log.info("收到active消息");
                            ret = 3;
                            packet.setLCommand_ID(FinalDef.CMD_SMGP_ACTIVE_TEST_RESP);
                            packet.setLSerial_ID(getSequence());
                            packet.setRequestBody(new TSMGP_ACTIVE_TEST());
                            try {
                                socket.write(packet);
                            } catch (IOException e) {
                                log.error(e.toString());
                                ret = FinalDef.ERROR_SOCKET_WRITE;
                            }

                        }
                        break;
                        case FinalDef.CMD_SMGP_ACTIVE_TEST_RESP:

                            log.info("收到active resp消息");
                            ret = 3;
                            break;
                        case FinalDef.CMD_SMGP_DELIVER_RESP:
                            log.info("不可能收到这样的信息");
                            ret = 3;
                            break;
                        case FinalDef.CMD_SMGP_SUBMIT_RESP:
                            log.info("收到submitresp消息，开辟线程来处理和submit消息的对应关系");
                            ret = 3;
                            break;
                        default:
                            log.error("错误的命令字ID:" + commandID);
                    }
                } else {
                    ret = 2;
                }

            }
        } catch (IOException e) {
            log.error(e.toString());
            ret = FinalDef.ERROR_SOCKET_READ;
        }
        return ret;
    }


    /**
     * 发送链路检测包
     * 返回值：
     * 0	    			成功
     * 小于0				失败，失败后会主动关闭Socket套接字。
     * <p>
     * 函数参数
     * param1:  socket号，由SMGP_connect函数返回.
     */
    public int SMGPActiveTest(int socketId) throws IOException {

        packet.setLCommand_ID(FinalDef.CMD_SMGP_ACTIVE_TEST);
        packet.setLSerial_ID(getSequence());
        packet.setRequestBody(new TSMGP_ACTIVE_TEST());
        try {
            synchronized (socket) {
                socket.write(packet);
//            long beginTime = System.currentTimeMillis();
                TSMGP_ACTIVE_TEST_RESP resp = new TSMGP_ACTIVE_TEST_RESP();
                long begin = System.currentTimeMillis();
                // 循环等待10秒,超过10秒,认为这条短信发送失败
                while (true) {
                    try {
                        long now = System.currentTimeMillis();
                        if (socket.getInputStream().available() >= 12) {
                            if (log.isDebugEnabled())
                                log.debug("读activeresp时输入流可读长度为:" + socket.getInputStream().available());
                            byte[] packetbytes = new byte[12];
                            java.io.InputStream out = socket.getInputStream();
                            out.read(packetbytes);
                            resp.parseResponseBody(packetbytes);
                            return 0;
                        } else if (now - begin > delayTime * 1000) {
                            log.warn("读取输入流时租塞,返回");
                            return FinalDef.ERROR_RSP_TIMEOUT;
                        }
                    } catch (IOException e) {
                        log.error("active消息IO错误:" + e.toString());
                        return FinalDef.ERROR_SOCKET_READ;
                    }
                }
            }
        } catch (IOException e) {
            log.error("active消息IO错误:" + e.toString());
            return FinalDef.ERROR_SOCKET_WRITE;
        }
    }


    /**
     * 发送DELIVER响应包
     * 返回值：
     * 0	    			成功
     * 小于0				失败
     * <p>
     * 函数参数
     * param1： 收到mo对应的MSGID.
     * param2:  lSerial_ID，由接收到的DELIVER对应的序列号.
     * param3:  请求返回结果
     */
    public int SMGPDeliverResp(String MsgId, int lSerial_ID, int status) throws IOException {

        packet.setLCommand_ID(FinalDef.CMD_SMGP_DELIVER_RESP);
        packet.setLSerial_ID(lSerial_ID);
        TSMGP_DELIVER_RESP tdr = new TSMGP_DELIVER_RESP();
        tdr.sMsgID = MsgId;
        tdr.lStatus = status;
        packet.setRequestBody(tdr);
        try {
            synchronized (socket) {
                socket.write(packet);
                return 0;
            }
        } catch (IOException e) {
            log.error("发送DELIVER响应包消息IO错误:" + e.toString());
            return FinalDef.ERROR_SOCKET_WRITE;
        }
    }

    /**
     * 返回值：
     * ERROR_SOCKET_WRITE		socket写失败
     * ERROR_SOCKET_READ		socket读失败
     * ERROR_RSP_TIMEOUT		网关超时应答
     * ERROR_OK					socket success
     * <p>
     * 函数参数：
     * param1:  socket号，是SMGP_connect函数的返回值；
     * param2:  用户填写需要发送TSMGP_SUBMIT包；（具体格式参考头文件）
     * param3:  查看网关的应答结果；
     */


    public int SMGP_Submit(int socketId, TSMGP_SUBMIT smgpsubmit, TSMGP_RESP resp) {
        packet.setLCommand_ID(FinalDef.CMD_SMGP_SUBMIT);
        packet.setLSerial_ID(getSequence());
        packet.setRequestBody(smgpsubmit);
        try {
            synchronized (socket) {
                socket.write(packet);
//            long beginTime = System.currentTimeMillis();
                long begin = System.currentTimeMillis();
                // 循环等待10秒,超过10秒,认为这条短信发送失败
                while (true) {
                    try {
                        long now = System.currentTimeMillis();
                        if (socket.getInputStream().available() >= 26) {
                            if (log.isDebugEnabled())
                                log.debug("输入流可读长度为:" + socket.getInputStream().available());
                            java.io.InputStream out = socket.getInputStream();
                            byte[] packetbytes = new byte[26];
                            out.read(packetbytes);
                            resp.parseResponseBody(packetbytes);
                            return resp.lStatus;
                        } else if (now - begin > delayTime * 1000) {
                            log.warn("读取输入流时租塞");
                            return FinalDef.ERROR_RSP_TIMEOUT;
                        }
                    } catch (IOException e) {
                        log.error("submit消息IO错误:" + e.toString());
                        // throw new CMPPException("submitresp io error:" + e.toString());
                        return FinalDef.ERROR_SOCKET_READ;
                    }
                }
            }
        } catch (IOException e) {
            log.error("submit消息IO错误:" + e.toString());
            // throw new CMPPException("submitresp io error:" + e.toString());
            return FinalDef.ERROR_SOCKET_WRITE;
        }

    }

    /**
     * 返回值:
     * 0	断开MO或MT连接成功
     *
     * @param socketid socket号由SMGP_Connect函数返回
     * @return
     */

    public int SMGP_Disconnect(int socketid) {
        int ret = -1;
        try {
            socket.closeSock();
            ret = 0;
        } catch (IOException e) {
            log.error("关闭socket错误:" + e.toString());
            ret = -1;
        }
        return ret;
    }


    private int sequence = 0;

    /**
     * 取得每次操作的序列号,步长为1,重复使用
     *
     * @return int
     */
    private int getSequence() {
        sequence++;
        if (sequence > 0x1fffffff)
            sequence = 2;
        return sequence;
    }


    private int socketID = 0;

    /**
     * 取得每次操作的序列号,步长为1,重复使用
     *
     * @return int
     */
    private int getSocketID() {
        socketID++;
        if (socketID > 0xffffffff)
            socketID = 2;
        return socketID;
    }

}

