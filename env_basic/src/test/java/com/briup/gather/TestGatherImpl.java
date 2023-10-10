package com.briup.gather;

import com.briup.entity.Environment;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TestGatherImpl {
    @Test
    public void test(){
        GatherImpl gather = new GatherImpl();
        List<Environment> environments = gather.gatherData();
        Calendar instance = Calendar.getInstance();
        Map<Integer, List<Environment>> collect = environments.stream().collect(Collectors.groupingBy(e -> {
            Timestamp gatherDate = e.getGatherDate();
            instance.setTime(gatherDate);
            int day = instance.get(Calendar.DAY_OF_MONTH);
            return day;
        }));
        collect.forEach((k,v) -> System.out.println(k+"="+v.size()));
    }



    @Test
    public void test1(){
        long l = 1516323596029l;
        Timestamp timestamp = new Timestamp(l);
        System.out.println("timestamp = " + timestamp);
    }

    @Test
    public void test2(){
        String s = "5d606f7802";
//        String s1 = s.substring(0, 4);
//        System.out.println("s1 = " + s1);
//        int i = Integer.parseInt(s1, 16);
        System.out.println((Integer.parseInt(s.substring(0, 4), 16)*(0.00268127F))-46.85F);
        // (data*(0.00268127F))-46.85F
        String s2 = s.substring(4,8);
        // (data*0.00190735F)-6
        System.out.println("s2 = " + s2);
    }
}
