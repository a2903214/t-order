package com.jy.torder.service;

import com.jy.torder.db.ConnInfo;
import com.jy.torder.model.CustomerObj;
import com.jy.torder.model.GiftTicketObj;
import com.jy.torder.model.OrderObj;
import com.jy.torder.util.BaseException;
import com.jy.torder.util.ConnInfoReader;

public class ServiceEnv {

    public static GTService gtService;
    public static OrderService orderService;
    public static CustomerService customerService;
    public static ConnInfo connInfo;

    public static void initServiceEnv() throws BaseException {
        connInfo = ConnInfoReader.readConnInfo(ConnInfoReader.DEFAULT_CONF_FILE_PATH);

        gtService = new GTService(connInfo);
        orderService = new OrderService(connInfo);
        customerService = new CustomerService(connInfo);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> BaseObjService<T> getService(Class<T> clazz) throws BaseException{
        if (CustomerObj.class.equals(clazz)) {
            return (BaseObjService<T>) customerService;
        } else if (GiftTicketObj.class.equals(clazz)) {
            return (BaseObjService<T>) gtService;
        } else if (OrderObj.class.equals(clazz)) {
            return (BaseObjService<T>) orderService;
        }
        throw new BaseException("未知的对象类型");
        
    }

}
