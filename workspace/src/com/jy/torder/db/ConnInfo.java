package com.jy.torder.db;

public class ConnInfo {
    private String host;
    private String port;
    private String dbname;
    private String username;
    private String password;
    
    public ConnInfo(){
        this.host = "localhost";
        this.port = "3306";
        this.dbname = "xpaper";
        this.username = "root";
        this.password = "123456";
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
