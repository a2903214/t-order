package com.jy.torder.ui;

import javax.swing.JDialog;

import com.jy.torder.model.*;
import com.jy.torder.ui.util.ObjFullPanel;
import com.jy.torder.util.BaseException;

public class GiftTicketFullPanel extends ObjFullPanel<GiftTicketObj> {

    private static final long serialVersionUID = -90699697327479135L;

    public GiftTicketFullPanel() throws BaseException {
        super();
    }
    
    public GiftTicketFullPanel(boolean isInDialog, JDialog dialog) throws BaseException {
        super(isInDialog, dialog);
    }

}
