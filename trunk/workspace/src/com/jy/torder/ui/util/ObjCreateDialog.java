package com.jy.torder.ui.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jy.torder.service.BaseObjService;
import com.jy.torder.service.ServiceEnv;
import com.jy.torder.util.BaseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ObjCreateDialog<T> extends JDialog {

    private static final long serialVersionUID = 2712153314379962505L;
    
    private final JPanel contentPanel = new JPanel();
    private BaseObjDetailPanel<T> objPanel;
    private Class<T> objClass;
    private BaseObjService<T> service;
    private boolean successful;

    /**
     * Create the dialog.
     */
    public ObjCreateDialog(JDialog owner, Class<T> clazz, boolean modal) {
        super(owner, modal);
        setTitle("新建");

        successful = false;
        objClass = clazz;
        try {
            service = ServiceEnv.getService(objClass);
        } catch (BaseException ex) {
            JOptionPane.showMessageDialog(ObjCreateDialog.this, ex.getLocalizedMessage(), "初始化新建对象界面失败", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            return;
        }
        setBounds(100, 100, 453, 477);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        

        contentPanel.setLayout(new BorderLayout(0, 0));
        
        try {
            objPanel = UIFactory.createObjDetailPanel(null, BaseObjDetailPanel.TYPE_NEW, objClass);
            contentPanel.add(objPanel, BorderLayout.CENTER);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(ObjCreateDialog.this, e.getLocalizedMessage(), "初始化新建对象界面失败", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            return;
        }
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        {
            JPanel buttonPane = new JPanel();
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            {
                JButton okButton = new JButton("确定");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            service.add(objPanel.getObj());
                            successful = true;
                            ObjCreateDialog.this.setVisible(false);
                        } catch (BaseException ex) {
                            JOptionPane.showMessageDialog(ObjCreateDialog.this, ex.getLocalizedMessage(), "创建对象失败",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("取消");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ObjCreateDialog.this.setVisible(false);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        this.pack();
        this.setLocationRelativeTo(owner);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public T getObj() {
        return objPanel.getObj();
    }

}
