package com.jy.torder.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jy.torder.service.ServiceEnv;
import com.jy.torder.util.BaseException;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class TOrderApp {

    private JFrame frame;
    
    private GiftTicketFullPanel giftManagePanel;
    private OrderFullPanel orderManagePanel;
    private CustomerFullPanel customerManagePanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        try {
            ServiceEnv.initServiceEnv();
        } catch (BaseException ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "初始化数据库连接失败", JOptionPane.ERROR_MESSAGE);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    TOrderApp window = new TOrderApp();
                    window.frame.setExtendedState(Frame.MAXIMIZED_BOTH);  
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TOrderApp() {
        try {
            initialize();
        } catch (BaseException ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "启动过程中遇到错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initialize the contents of the frame.
     * @throws BaseException 
     */
    private void initialize() throws BaseException {
        frame = new JFrame();
        
        frame.setTitle("礼品券订单管理工具");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
                TOrderApp.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
        frame.setBounds(100, 100, 638, 463);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        JPanel giftTicketPanel = new JPanel();
        tabbedPane.addTab("礼品券管理", null, giftTicketPanel, null);
        giftTicketPanel.setLayout(new BorderLayout(0, 0));
        giftManagePanel = new GiftTicketFullPanel();
        giftTicketPanel.add(giftManagePanel, BorderLayout.CENTER);
        
        JPanel orderPanel = new JPanel();
        tabbedPane.addTab("订单管理", null, orderPanel, null);
        orderPanel.setLayout(new BorderLayout(0, 0));
        orderManagePanel = new OrderFullPanel();
        orderPanel.add(orderManagePanel, BorderLayout.CENTER);
        
        JPanel customerPanel = new JPanel();
        tabbedPane.addTab("客户管理", null, customerPanel, null);
        customerPanel.setLayout(new BorderLayout(0, 0));
        customerManagePanel = new CustomerFullPanel();
        customerPanel.add(customerManagePanel, BorderLayout.CENTER);
        
        
        frame.setLocationRelativeTo(null);
    }

}
