package com.briup.main;

import com.briup.client.ClientImpl;
import com.briup.gather.GatherImpl;


public class ClientMain {
    public static void main(String[] args) {
        ClientImpl client = new ClientImpl();
        GatherImpl gather = new GatherImpl();
        client.send(gather.gatherData());
    }
}
