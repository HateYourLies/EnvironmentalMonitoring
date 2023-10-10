package com.briup.client.main;

import com.briup.client.ConfigurationImpl;
import com.briup.client.gather.GatherImpl;
import com.briup.client.send.SendImpl;
import com.briup.common.entity.Environment;
import com.briup.common.joint.Gather;
import com.briup.common.joint.Send;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.util.List;


@Log4j
public class ClientMain {
    public static void main(String[] args) throws Exception {
//        SendImpl client = new SendImpl();
//        GatherImpl gather = new GatherImpl();
        ConfigurationImpl configuration = ConfigurationImpl.getConfiguration();
        Gather gather = configuration.getGather();
        Send client = configuration.getClient();
        List<Environment> environments = gather.gatherData();
        if(environments != null && environments.size() > 0){
            client.send(environments);
        }else{
            log.info("未采集到数据");
        }
    }
}
    