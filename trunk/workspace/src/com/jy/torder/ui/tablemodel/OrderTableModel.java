package com.jy.torder.ui.tablemodel;

import java.util.List;

import com.jy.torder.model.OrderObj;
import com.jy.torder.ui.util.BaseObjTableModel;

public class OrderTableModel extends BaseObjTableModel<OrderObj> {
    
    private static final long serialVersionUID = 3397571215863248671L;
    
    public OrderTableModel(List<OrderObj> objList){
        super(objList);
    }

    public String getColumnName(int column) {
        String colName = "未知";
        switch(column){
        case 0:
            colName = "券号";
            break;
        case 1:
            colName = "密码";
            break;
            
        case 2:
            colName = "规格";
            break;
        case 3:
            colName = "客户姓名";
            break;
        case 4:
            colName = "联系电话";
            break;
        case 5:
            colName = "收货地址";
            break;
        case 6:
            colName = "配送时间";
            break;
        case 7:
            colName = "发货状态";
            break;
        case 8:
            colName = "备注";
            break;
        }
        return colName;
    }

    public int getColumnCount() {
        return 9;
    }

    public Object getValueAt(OrderObj obj, int columnIndex) {
        Object colValue = "未知";
        switch(columnIndex){
        case 0:
            colValue = obj.getNumber();
            break;
        case 1:
            colValue = obj.getPasswd();
            break;
            
        case 2:
            colValue = obj.getSpec();
            break;
        case 3:
            colValue = obj.getName();
            break;
        case 4:
            colValue = obj.getMobile();
            break;
        case 5:
            colValue = obj.getAddr();
            break;
        case 6:
            colValue = obj.getDeliveryTime();
            break;
        case 7:
            colValue = obj.getStatusDesc();
            break;
        case 8:
            colValue = obj.getDesc();
            break;
        }
        return colValue;
    }

}
