package com.jy.torder.ui.tablemodel;

import java.util.List;


import com.jy.torder.model.GiftTicketObj;
import com.jy.torder.ui.util.BaseObjTableModel;

public class GiftTicketTableModel extends BaseObjTableModel<GiftTicketObj> {
    
    private static final long serialVersionUID = -6827854241055098487L;

    public GiftTicketTableModel(List<GiftTicketObj> objList) {
        super(objList);
    }

    public String getColumnName(int column) {
        String colName = "未知";
        switch (column) {
        case 0:
            colName = "券号";
            break;
        case 1:
            colName = "密码";
            break;

        case 2:
            colName = "规格";
            break;
        }
        return colName;
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(GiftTicketObj obj, int columnIndex) {
        Object colValue = "";
        switch (columnIndex) {
        case 0:
            colValue = obj.getNumber();
            break;
        case 1:
            colValue = obj.getPasswd();
            break;

        case 2:
            colValue = obj.getSpec();
            break;
        }
        return colValue;
    }
}
