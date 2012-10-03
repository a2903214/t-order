package com.jy.torder.ui;

import javax.swing.JDialog;

import com.jy.torder.model.*;
import com.jy.torder.ui.util.ObjFullPanel;
import com.jy.torder.util.BaseException;

public class CustomerFullPanel extends ObjFullPanel<CustomerObj> {

    private static final long serialVersionUID = -90699697327479135L;

    public CustomerFullPanel() throws BaseException {
        super();
    }
    
    public CustomerFullPanel(boolean isInDialog, JDialog dialog) throws BaseException {
        super(isInDialog, dialog);
    }
}
