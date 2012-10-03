package com.jy.torder.ui.util;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jy.torder.util.BaseException;

public class ObjSelectDialog<ObjType> extends JDialog {
    public interface SelectCbInterface{
        
    }
    
    private static final long serialVersionUID = 8780985663276582024L;

    private final JPanel contentPanel = new JPanel();

    ObjFullPanel<ObjType> objFullPanel;

    Class<ObjType> objClass;
    
    Window owner;

    /**
     * @wbp.parser.constructor
     */
    public ObjSelectDialog(Window owner, Class<ObjType> objClass) throws BaseException {
        super(owner);
        this.setModalityType(ModalityType.TOOLKIT_MODAL);
        this.owner = owner;
        init(objClass);
    }

    /**
     * Create the dialog.
     * 
     * @throws BaseException
     */
    public void init(Class<ObjType> objClass) throws BaseException {
        this.objClass = objClass;
        setTitle("请选择");
        setBounds(100, 100, 450, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        objFullPanel = UIFactory.createObjFullPanel(this, objClass);
        contentPanel.add(objFullPanel, BorderLayout.CENTER);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(owner);
    }

    public boolean isSelected() {
        return objFullPanel.isSelected();
    }

    public ObjType getSelectObj() {
        return objFullPanel.getSelectObj();
    }
    
}
