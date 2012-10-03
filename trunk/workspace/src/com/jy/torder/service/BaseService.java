package com.jy.torder.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jy.torder.db.ConnectionPool;
import com.jy.torder.db.TxnConnection;
import com.jy.torder.db.ConnInfo;
import com.jy.torder.util.BaseException;

public abstract class BaseService {
    private ConnInfo connInfo;
    
    private DBQueryProcessNoCallback queryProcessNoCallback = new DBQueryProcessNoCallback();

    public ConnInfo getConnInfo() {
        return connInfo;
    }
    
    protected BaseService(ConnInfo connInfo) throws BaseException{
        this.connInfo = connInfo;
        this.connectDB(connInfo);
    }

    private void connectDB(ConnInfo connInfo) throws BaseException {

        if (connInfo != null) {
            this.connInfo = connInfo;
            ConnectionPool.testConnect(connInfo);
        }
    }

    public void disconnDB() throws BaseException {
        ConnectionPool.freeConnPool(this.connInfo);
    }

    private TxnConnection getConnection() throws BaseException {
        return ConnectionPool.getConnection(connInfo);
    }

    protected void begin() throws BaseException {
        TxnConnection conn = this.getConnection();
        if (conn != null) {
            try {
                conn.begin();
            } catch (SQLException e) {
                throw new BaseException("数据库访问失败！", e);
            }
        } else {
            throw new BaseException("获取数据库访问连接失败！");
        }
    }

    protected void commit() throws BaseException {
        TxnConnection conn = this.getConnection();
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new BaseException("数据库访问失败！", e);
            }
        } else {
            throw new BaseException("获取数据库访问连接失败！");
        }
    }

    protected void rollback() throws BaseException {
        TxnConnection conn = this.getConnection();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new BaseException("数据库访问失败！", e);
            }
        } else {
            throw new BaseException("获取数据库访问连接失败！");
        }
    }

    public interface DBExecuteProcess {

        void addParameters(PreparedStatement ps) throws SQLException;

        void resultProcess(ResultSet result) throws SQLException;

        void processGeneratedKeys(ResultSet result) throws SQLException;

    }

    public abstract class DBUpdateProcessAdapter implements DBExecuteProcess {
        public void resultProcess(ResultSet result) throws SQLException {

        }
    }

    public abstract class DBUpdateNoKeyProcessAdapter implements DBExecuteProcess {
        public void resultProcess(ResultSet result) throws SQLException {

        }

        public void processGeneratedKeys(ResultSet result) throws SQLException {

        }
    }

    public abstract class DBQueryProcessAdapter implements DBExecuteProcess {
        public void processGeneratedKeys(ResultSet result) throws SQLException {

        }
    }
    
    public abstract class DBQueryProcessNoParamAdapter implements DBExecuteProcess {
        public void processGeneratedKeys(ResultSet result) throws SQLException {

        }
        public void addParameters(PreparedStatement ps) throws SQLException{
            
        }
    }
    
    public class DBQueryProcessNoCallback implements DBExecuteProcess {
        public void processGeneratedKeys(ResultSet result) throws SQLException {

        }
        public void addParameters(PreparedStatement ps) throws SQLException{
            
        }
        
        public void resultProcess(ResultSet result) throws SQLException {

        }
    }

    protected void executeSQL(String query, String[] columnNames, DBExecuteProcess process)
            throws BaseException {
        PreparedStatement ps = null;
        try {
            TxnConnection conn = this.getConnection();
            this.begin();
            ps = conn.prepareStatement(query, columnNames);
            this.execute(ps, process, true);
            this.commit();
        } catch (Throwable e) {
            this.rollback();
            throw new BaseException("执行SQL语句失败:" + query, e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void execute(PreparedStatement ps, DBExecuteProcess process, boolean processKey)
            throws BaseException {
        ResultSet queryResult = null;
        ResultSet keyResult = null;
        try {
            this.begin();
            if (process != null) {
                process.addParameters(ps);
            }
            ps.execute();
            if (process != null) {
                queryResult = ps.getResultSet();
                process.resultProcess(queryResult);
                if (processKey) {
                    keyResult = ps.getGeneratedKeys();
                    process.processGeneratedKeys(keyResult);
                }
            }
            this.commit();
        } catch (Throwable e) {
            this.rollback();
            throw new BaseException("执行查询失败！", e);
        } finally {
            if (queryResult != null) {
                try {
                    queryResult.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    protected void executeSQL(String query) throws BaseException{
        this.executeSQL(query, queryProcessNoCallback);
    }

    protected void executeSQL(String query, DBExecuteProcess process) throws BaseException {
        PreparedStatement ps = null;
        try {
            TxnConnection conn = this.getConnection();
            this.begin();
            ps = conn.prepareStatement(query);
            this.execute(ps, process, false);

            this.commit();
        } catch (Throwable e) {
            this.rollback();
            throw new BaseException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
