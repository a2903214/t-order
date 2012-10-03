package com.jy.torder.ui;

import javax.swing.JDialog;

import com.jy.torder.model.OrderObj;
import com.jy.torder.ui.util.ObjFullPanel;
import com.jy.torder.util.BaseException;

public class OrderFullPanel extends ObjFullPanel<OrderObj> {

    private static final long serialVersionUID = -90699697327479135L;

    public OrderFullPanel() throws BaseException {
        super();
    }
    
    public OrderFullPanel(boolean isInDialog, JDialog dialog) throws BaseException {
        super(isInDialog, dialog);
    }

}
