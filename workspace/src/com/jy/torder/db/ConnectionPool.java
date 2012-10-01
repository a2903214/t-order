package com.jy.torder.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jy.torder.util.BaseException;

public class ConnectionPool {
    private static HashMap<ConnInfo, ThreadLocal<TxnConnection>> txnConLocalMap = new HashMap<ConnInfo, ThreadLocal<TxnConnection>>();
    private static HashMap<ConnInfo, List<TxnConnection>> txnConListMap = new HashMap<ConnInfo, List<TxnConnection>>();

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void testConnect(ConnInfo connInfo) throws BaseException {
        ThreadLocal<TxnConnection> tmp = txnConLocalMap.get(connInfo);
        if (tmp == null) {
            tmp = new ThreadLocal<TxnConnection>();
            txnConLocalMap.put(connInfo, tmp);
            txnConListMap.put(connInfo, new ArrayList<TxnConnection>());
        }
        TxnConnection conn = tmp.get();
        if (conn == null) {
            conn = createNewConn(connInfo);
            tmp.set(conn);
        }
    }

    public static TxnConnection getConnection(ConnInfo connInfo) throws BaseException {
        ThreadLocal<TxnConnection> tmp = txnConLocalMap.get(connInfo);
        TxnConnection conn = null;
        if (tmp != null) {
            conn = tmp.get();
            if (conn == null) {
                conn = createNewConn(connInfo);
                tmp.set(conn);
            }
        }
        return conn;
    }

    public static TxnConnection createNewConn(ConnInfo connInfo) throws BaseException {
        TxnConnection txnConnection = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + connInfo.getHost()
                                                                  + ":" + connInfo.getPort() + "/"
                                                                  + connInfo.getDbname(),
                                                          connInfo.getUsername(),
                                                          connInfo.getPassword());
            txnConnection = new TxnConnection(connInfo, conn);
            List<TxnConnection> txnConnList = txnConListMap.get(connInfo);
            if (txnConnList == null) {
                txnConnList = new ArrayList<TxnConnection>();
                txnConListMap.put(connInfo, txnConnList);
            }
            txnConnList.add(txnConnection);
        } catch (SQLException e) {
            throw new BaseException("创建数据库连接失败", e);
        }

        return txnConnection;
    }

    public static synchronized void freeConnPool(ConnInfo connInfo) {
        if (connInfo != null) {
            List<TxnConnection> tmp = txnConListMap.get(connInfo);
            for (TxnConnection conn : tmp) {
                try {
                    conn.close();
                } catch (Throwable tr) {
                    tr.printStackTrace();
                }
            }
            txnConListMap.remove(connInfo);
            txnConLocalMap.remove(connInfo);
        }
    }

    public static synchronized void freeAllConnPool() {
        for (ConnInfo connInfo : txnConListMap.keySet()) {
            freeConnPool(connInfo);
        }
    }

}
