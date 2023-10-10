package com.briup.client.gather;


import com.briup.client.back.BackupImpl;
import com.briup.common.PropertiesAware;
import com.briup.common.entity.Environment;
import com.briup.common.joint.Backup;
import com.briup.common.joint.Gather;
import com.briup.common.utils.CopyUtil;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@Log4j
public class GatherImpl implements Gather, PropertiesAware {
    String backupPath;
    String gatherPath;

//    Logger logger = Logger.getLogger(GatherImpl.class);

    /**
     * 100|101|2|16|1|3|5d606f7802|1|1516323596029
     * 把所有数据行放到存储环境对象的容器Collection-> List中
     *  一行 一个环境对象
     *     9列    10个属性
     */
    public List<Environment> gatherData(){
        Backup backup = new BackupImpl();
        BufferedReader br = null;
        try {
            // 1.读取文件 文件字符输入流  |  类加载器
            // io 输入输出 字节字符
            // 基本流 文件流 内存流
            // 包装流 缓冲 转换流 标准流 任意流 对象流
//            FileReader fileReader = new FileReader("env_client/src/main/resources/data-file-simple");
            InputStream in = GatherImpl.class.getClassLoader().getResourceAsStream(gatherPath);
//            File file = new File(gatherPath);
            // 字节流 -> 字符流
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            br = new BufferedReader(inputStreamReader);
            // 文件长度
            long newLength = in.available();
            // 是否为第一次 有没有备份过
            Object load = backup.load(backupPath, Backup.LOAD_UNREMOVE);
            if(load != null){
                // 说明有备份 取出备份的数据 跳过 100kb  long
                br.skip((Long) load);   // br.skip(500)
            } // load是空  说明第一次采集 不用跳过

            // 2.循环文件中的每一行 将每一行封装成环境对象
            String line;
            List<Environment> environmentList = new ArrayList<>();
            while( (line =  br.readLine()) != null ){
                 // br.readLine(); // 第二次读取
//                System.out.println("line = " + line);
                // 100|101|2|16|1|3|5d606f7802|1|1516323596029  -> Environment对象
                // 一行的每列数据
                if("".equals(line.trim())){
                    continue;  // 空字符数据
                }
                String[] lineArr = line.split("\\|");
                System.out.println(Arrays.toString(lineArr));
                Environment environment = new Environment(lineArr[0],lineArr[1],lineArr[2],lineArr[3],Integer.parseInt(lineArr[4]),lineArr[5],Integer.parseInt(lineArr[7]),new Timestamp(Long.parseLong(lineArr[8])));
                String originData = lineArr[6];
                switch (environment.getSensorAddress()){ // if else
                    // 3.将所有的环境对象用容器装取
                    case "16" :
                        environment.setName("温度"); // 传感器种类 date  |
                        environment.setData((Integer.parseInt(originData.substring(0, 4), 16)*(0.00268127F))-46.85F); // 环境数值

//                        Environment environment2 = (Environment) environment.clone(); // 湿度对象
                        Environment environment2 = CopyUtil.copy(environment);
                        environment2.setName("湿度");
                        // 6f78 -> 10进制整数 -> 计算公式
                        environment2.setData((Integer.parseInt(originData.substring(4, 8), 16)*(0.00190735F))-6); // 环境数值
                        environmentList.add(environment2);
                        break;
                    case "256" :
                        environment.setName("光照"); // 传感器种类
                        // 6f78 -> 10进制整数
                        environment.setData(Integer.parseInt(originData.substring(0,4),16)); // 环境数值
                        break;
                    case "1280" :
                        environment.setName("二氧化碳"); // 传感器种类
                        // 6f78 -> 10进制整数
                        environment.setData(Integer.parseInt(originData.substring(0,4),16)); // 环境数值
                        break;
                }
                environmentList.add(environment);
                // 采集过后备份新的文件长度
                // 502行对应的文件字节
                backup.store(backupPath,newLength,Backup.STORE_OVERRIDE);

            }
            return environmentList;
        } catch (Exception e) {
            log.error("采集失败");
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    @Override
    public void init(Properties properties) throws Exception {
        log.info(properties);
        gatherPath = properties.getProperty("gather-file-path");
        backupPath = properties.getProperty("backup-file-path");
    }
}
