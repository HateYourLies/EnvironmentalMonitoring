package com.briup.main;

import com.briup.dbstore.DBStoreImpl;
import com.briup.entity.Environment;
import com.briup.server.ServerImpl;

import java.util.List;


public class ServerMain {
    public static void main(String[] args) {
        ServerImpl server = new ServerImpl();
        List<Environment> list = server.receive();
        DBStoreImpl dbStore = new DBStoreImpl();
        dbStore.insertData(list);
    }
}
