package com.briup.client.send;


import com.briup.client.main.ClientMain;
import com.briup.common.PropertiesAware;
import com.briup.common.entity.Environment;
import com.briup.common.joint.Send;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;


@Log4j
public class SendImpl implements Send, PropertiesAware {
//    Logger logger = Logger.getLogger(ClientMain.class);
    int port;
    String host;
    /**
     * 发送数据
     * @param list 采集的数据
     */
    public void send(List<Environment> list){
        Socket socket = null;
        ObjectOutputStream oos = null;
        try {
            socket = new Socket(host,port);
            log.info("客户端 "+port+" 启动");
            // 对象流输出流
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(list);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void init(Properties properties) throws Exception {
        port = Integer.parseInt(properties.getProperty("reciver-server-port"));
        host = properties.getProperty("host");
    }
}
