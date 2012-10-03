package com.jy.torder.ui.tablemodel;

import java.util.List;

import com.jy.torder.model.CustomerObj;
import com.jy.torder.ui.util.BaseObjTableModel;

public class CustomerTableModel extends BaseObjTableModel<CustomerObj> {

    private static final long serialVersionUID = 3397571215863248671L;

    public CustomerTableModel(List<CustomerObj> objList) {
        super(objList);
    }

    public String getColumnName(int column) {
        String colName = "未知";
        switch (column) {
        case 0:
            colName = "客户姓名";
            break;
        case 1:
            colName = "联系电话";
            break;
        case 2:
            colName = "收货地址";
            break;
        }
        return colName;
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(CustomerObj obj, int columnIndex) {
        Object colValue = "未知";
        switch (columnIndex) {
        case 0:
            colValue = obj.getName();
            break;
        case 1:
            colValue = obj.getMobile();
            break;
        case 2:
            colValue = obj.getAddr();
            break;
        }
        return colValue;
    }

}
