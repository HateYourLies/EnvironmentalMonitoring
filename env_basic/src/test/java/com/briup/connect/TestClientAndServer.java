package com.briup.connect;

import com.briup.client.ClientImpl;
import com.briup.dbstore.DBStoreImpl;
import com.briup.entity.Environment;
import com.briup.gather.GatherImpl;
import com.briup.server.ServerImpl;
import org.junit.Test;

import java.util.List;


public class TestClientAndServer {
    @Test
    public void testRecive(){
        ServerImpl server = new ServerImpl();
        List<Environment> list = server.receive();
        DBStoreImpl dbStore = new DBStoreImpl();
        dbStore.insertData(list);
    }
    @Test
    public void testSend(){
        ClientImpl client = new ClientImpl();
        GatherImpl gather = new GatherImpl();
        client.send(gather.gatherData());
    }
}
