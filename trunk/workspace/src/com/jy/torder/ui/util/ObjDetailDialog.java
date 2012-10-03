package com.jy.torder.ui.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jy.torder.service.BaseObjService;
import com.jy.torder.service.ServiceEnv;
import com.jy.torder.util.BaseException;

public class ObjDetailDialog<T> extends JDialog {

    private static final long serialVersionUID = -2386786691523635497L;
    private final JPanel contentPanel = new JPanel();
    private Class<T> objClass;
    private BaseObjService<T> service;
    private boolean successful;

    /**
     * Create the dialog.
     * 
     * @throws BaseException
     */
    public ObjDetailDialog(Window owner, T obj, Class<T> clazz) throws BaseException {
        super(owner);
        setTitle("更新");
        successful = false;
        this.setModalityType(ModalityType.TOOLKIT_MODAL);
        objClass = clazz;
        service = ServiceEnv.getService(objClass);
        setBounds(100, 100, 443, 458);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new BorderLayout(0, 0));

        final BaseObjDetailPanel<T> detailPanel = UIFactory.createObjDetailPanel(obj, BaseObjDetailPanel.TYPE_DETAIL, objClass);
        contentPanel.add(detailPanel, BorderLayout.CENTER);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        {
            JPanel buttonPane = new JPanel();
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton cancelButton = new JButton("取消");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ObjDetailDialog.this.setVisible(false);
                    }
                });
                buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                {
                    JButton modifyButton = new JButton("修改");
                    modifyButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try {
                                service.modify(detailPanel.getObj());
                                successful = true;
                                ObjDetailDialog.this.setVisible(false);
                            } catch (BaseException ex) {
                                JOptionPane.showMessageDialog(ObjDetailDialog.this, ex.getLocalizedMessage(), "修改对象失败",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                            
                        }
                    });
                    buttonPane.add(modifyButton);
                }
                cancelButton.setActionCommand("OK");
                buttonPane.add(cancelButton);
                getRootPane().setDefaultButton(cancelButton);
            }
        }
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
    }

    public boolean isSuccessful() {
        return successful;
    }

}
