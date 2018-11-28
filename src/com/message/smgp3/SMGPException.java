package com.message.smgp3;

import java.io.IOException;

/**
 * smgp建立和关闭连接时候封装IOException
 * @author YC
 * @version 1.0 (2005-3-29 15:20:45)
 */
public class SMGPException extends IOException {
    public SMGPException() {
        super();
    }

    public SMGPException(String msg) {
        super(msg);
    }
}