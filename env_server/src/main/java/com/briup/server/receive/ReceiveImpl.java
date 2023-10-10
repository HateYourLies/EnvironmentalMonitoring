package com.briup.server.receive;


import com.briup.common.Configuration;
import com.briup.common.ConfigurationAware;
import com.briup.common.PropertiesAware;
import com.briup.common.entity.Environment;
import com.briup.common.joint.DBStore;
import com.briup.common.joint.Receive;
import com.briup.server.ConfigurationImpl;
import com.briup.server.dbstore.DBStoreImpl;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Auther: vanse(lc)
 * @Date: 2023/9/21-09-21-14:05
 * @Description：com.briup.server
 */
@Log4j
public class ReceiveImpl implements Receive, PropertiesAware,ConfigurationAware{
    //    Logger logger = Logger.getLogger(ReceiveImpl.class);
    int port;
    Configuration con;

    public void receive() {
//        int port = pro.get("receive-port");
        ServerSocket serverSocket = null;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        try {
            serverSocket = new ServerSocket(port);
            log.info("服务器 " + port + " 启动");
            while (true) {
                Socket socket = serverSocket.accept();
                threadPoolExecutor.submit(() -> {
                    ObjectInputStream ois = null;
                    try {
                        ois = new ObjectInputStream(socket.getInputStream());
                        List<Environment> list = (List<Environment>) ois.readObject();
                        log.debug("服务端接收数据" + list.size());
                        DBStore dbStore = con.getDbStore();
                        log.info(dbStore);
                        dbStore.insertData(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (ois != null) {
                                ois.close();
                            }
                            if (socket != null) {
                                socket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
                if (threadPoolExecutor != null) {
                    threadPoolExecutor.shutdown();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void init(Properties properties) throws Exception {
        log.info("注入属性 " + properties);
        // Object -> String
        port = Integer.parseInt(properties.getProperty("reciver-server-port"));
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        log.info("注入配置对象： " + configuration);
        con = configuration;
    }
}

// 每个线程拥有1个socket
//@Log4j
//class ReceiveThread implements Runnable, ConfigurationAware {
//    private Socket socket;
//    Configuration con;
//    //    Logger logger = Logger.getLogger(ReceiveThread.class);
//    public ReceiveThread(Socket socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//
//
//    }
//
//
//
//    @Override
//    public void setConfiguration(Configuration configuration) throws Exception {
//        log.debug(configuration);
//        con = configuration;
//    }
//}
