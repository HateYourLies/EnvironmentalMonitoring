package com.briup.jdk;

import com.briup.entity.Environment;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



/**
 * 需求1： 查找数据，并按年月日划分组2022-1-1 list 2022-1-2 遍历
 * 需求2： 环境对象 前100个环境
 * 			timestamp -> 时间戳
 *
 * 需求3： 只查询温湿度数据
 *
 * 需求4： 温湿度各按小时平均值统计
 * 		1  2  3  4  5   6  7
 */
public class Test8 {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/env?useSeverPrepStmts=true";
    String username = "env";
    String password = "env";
    @Test
    public void test() throws Exception {
        Class.forName(driver);
        // 2.获取连接
        Connection conn = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = conn.prepareStatement("select * from environment");
        ResultSet rs = ps.executeQuery();
        List<Environment> environmentList = new ArrayList<>();
        while(rs.next()){
            Environment environment = new Environment();
            environment.setName(rs.getString("name"));
            environment.setSrcId(rs.getString("src_id"));
            environment.setDesId(rs.getString("des_id"));
            environment.setDevId(rs.getString("dev_id"));
            environment.setSensorAddress(rs.getString("sensor_address"));
            environment.setCount(rs.getInt("count"));
            environment.setCmd(rs.getString("cmd"));
            environment.setStatus(rs.getInt("status"));
            environment.setData(rs.getFloat("data"));
            environment.setGatherDate((rs.getTimestamp("gather_date")));
            environmentList.add(environment);
        }
        System.out.println("environmentList = " + environmentList);
    }
}
