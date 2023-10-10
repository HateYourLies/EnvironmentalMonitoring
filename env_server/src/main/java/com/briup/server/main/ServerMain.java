package com.briup.server.main;

import com.briup.common.Configuration;
import com.briup.common.entity.Environment;
import com.briup.common.joint.Receive;
import com.briup.server.ConfigurationImpl;
import com.briup.server.dbstore.DBStoreImpl;
import com.briup.server.receive.ReceiveImpl;
import lombok.extern.log4j.Log4j;

import java.io.InputStream;
import java.util.List;


@Log4j
public class ServerMain {
    public static void main(String[] args) throws Exception {
//        ConfigurationImpl configuration = new ConfigurationImpl();
//        Receive server1 = configuration.getServer();
//        Receive server = new ReceiveImpl();
        ConfigurationImpl configuration = ConfigurationImpl.getConfiguration();
        Receive server = configuration.getServer();
//        log.debug(server);
        server.receive();

    }
}
