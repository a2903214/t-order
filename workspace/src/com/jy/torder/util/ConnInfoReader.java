package com.jy.torder.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.jy.torder.db.ConnInfo;

public class ConnInfoReader {
    public final static String DEFAULT_CONF_FILE_PATH = "./config/conn.conf";
    
    public static ConnInfo readConnInfo(String filepath) {
        ConnInfo connInfo = new ConnInfo();
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connInfo.setHost(properties.getProperty("host", "127.0.0.1"));
        connInfo.setDbname(properties.getProperty("dbname", "torder"));
        connInfo.setPort(properties.getProperty("port", "3306"));
        connInfo.setUsername(properties.getProperty("username", "root"));
        connInfo.setPassword(properties.getProperty("password", "123456"));
        return connInfo;
    }

}
