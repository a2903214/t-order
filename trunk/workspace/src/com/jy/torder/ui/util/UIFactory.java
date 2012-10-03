package com.jy.torder.ui.util;

import java.util.List;

import javax.swing.JDialog;

import com.jy.torder.model.*;
import com.jy.torder.ui.CustomerFullPanel;
import com.jy.torder.ui.GiftTicketFullPanel;
import com.jy.torder.ui.OrderFullPanel;
import com.jy.torder.ui.objdetail.CustomerDetailPanel;
import com.jy.torder.ui.objdetail.GiftTicketDetailPanel;
import com.jy.torder.ui.objdetail.OrderDetailPanel;
import com.jy.torder.ui.tablemodel.CustomerTableModel;
import com.jy.torder.ui.tablemodel.GiftTicketTableModel;
import com.jy.torder.ui.tablemodel.OrderTableModel;
import com.jy.torder.util.BaseException;

public class UIFactory {

    @SuppressWarnings("unchecked")
    public static <T> BaseObjTableModel<T> createObjTableModel(List<T> objList, Class<T> clazz) throws BaseException{
        if (CustomerObj.class.equals(clazz)) {
            return (BaseObjTableModel<T>) new CustomerTableModel((List<CustomerObj>) objList);
        } else if (GiftTicketObj.class.equals(clazz)) {
            return (BaseObjTableModel<T>) new GiftTicketTableModel((List<GiftTicketObj>) objList);
        } else if (OrderObj.class.equals(clazz)) {
            return (BaseObjTableModel<T>) new OrderTableModel((List<OrderObj>) objList);
        }
        throw new BaseException("未知的对象类型");
    }
    
    @SuppressWarnings("unchecked")
    public static <T> BaseObjDetailPanel<T> createObjDetailPanel(T obj, int type, Class<T> clazz) throws BaseException{
        if (CustomerObj.class.equals(clazz)) {
            return (BaseObjDetailPanel<T>) new CustomerDetailPanel((CustomerObj) obj, type);
        } else if (GiftTicketObj.class.equals(clazz)) {
            return (BaseObjDetailPanel<T>) new GiftTicketDetailPanel((GiftTicketObj) obj, type);
        } else if (OrderObj.class.equals(clazz)) {
            return (BaseObjDetailPanel<T>) new OrderDetailPanel((OrderObj) obj, type);
        }
        throw new BaseException("未知的对象类型");
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ObjFullPanel<T> createObjFullPanel(JDialog dialog, Class<T> clazz) throws BaseException{
        if (CustomerObj.class.equals(clazz)) {
            return (ObjFullPanel<T>) new CustomerFullPanel(true, dialog);
        } else if (GiftTicketObj.class.equals(clazz)) {
            return (ObjFullPanel<T>) new GiftTicketFullPanel(true, dialog);
        } else if (OrderObj.class.equals(clazz)) {
            return (ObjFullPanel<T>) new OrderFullPanel(true, dialog);
        }
        throw new BaseException("未知的对象类型");
    }
    
}
