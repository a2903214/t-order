package com.jy.torder.action;

import com.jy.torder.db.ConnInfo;
import com.jy.torder.util.BaseException;

public interface ConnAction extends BaseAction{
    
    void connectDB(ConnInfo connInfo) throws BaseException;
    
    void disconnectDB() throws BaseException;
    
}
