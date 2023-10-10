package com.briup.server;

import com.briup.common.Configuration;
import com.briup.common.ConfigurationAware;
import com.briup.common.PropertiesAware;
import com.briup.common.joint.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class ConfigurationImpl implements Configuration {
    // 文件只会加载一次 ConfigurationImpl只有一个 对象应该是单例
    /**
     * 单例模式
     *  1.构造私有
     *  2.本类提供一个private 对象（提前创建）
     *  3.公有静态方法返回该对象
     */
    private static ConfigurationImpl configuration = new ConfigurationImpl();
    private ConfigurationImpl(){}
    // new ConfigurationImpl();
    public static ConfigurationImpl getConfiguration(){
        return configuration;
    }
    // 名字 -> 对象
    static Map<String,Object> map = new HashMap<>();
    static Properties properties = new Properties();
    /**
     * 解析server下的config.xml文件 把对象获取
     * @return
     * @throws Exception
     */

    static{
        try {
            // 1.加载config文件
            SAXReader saxReader = new SAXReader();
            InputStream in = ConfigurationImpl.class.getClassLoader().getResourceAsStream("server-config.xml");
//            Document document = saxReader.read("env_server/src/main/resources/server-config.xml");
            Document document = saxReader.read(in);
            // 1.1 根节点
            Element rootElement = document.getRootElement();
            // 1.2 其他节点
            List<Element> firsetElements = rootElement.elements();
            firsetElements.forEach(firstElement -> {
                String name = firstElement.getName();
                String aClass = firstElement.attribute("class").getValue();
                try {
                    // 2.把每个节点的名字和class属性
                    map.put(name,Class.forName(aClass).newInstance());
                    // 3.子节点
                    List<Element> secondElements = firstElement.elements();
                    secondElements.forEach(second -> {
                        String name1 = second.getName();
                        String value1 = second.getStringValue();
                        properties.setProperty(name1,value1);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            map.values().forEach(obj -> {
                try {
                    if(obj instanceof ConfigurationAware){
                        ConfigurationAware configurationAware = (ConfigurationAware) obj;
                        configurationAware.setConfiguration(configuration);
                    }
                    if(obj instanceof PropertiesAware){
                        PropertiesAware propertiesAware = (PropertiesAware) obj;
                        propertiesAware.init(properties);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Receive getServer() throws Exception {
        return (Receive) map.get("server");
    }

    @Override
    public Send getClient() throws Exception {
        return null;
    }

    @Override
    public DBStore getDbStore() throws Exception {
        return (DBStore) map.get("dbStore");
    }

    @Override
    public Gather getGather() throws Exception {
        return null;
    }

    @Override
    public Backup getBackup() throws Exception {
        return null;
    }
}
