package com.jy.torder.ui.objdetail;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import com.jy.torder.model.CustomerObj;
import com.jy.torder.model.GiftTicketObj;
import com.jy.torder.model.OrderObj;
import com.jy.torder.model.OrderObj.OrderStatus;
import com.jy.torder.service.OrderService;
import com.jy.torder.ui.util.BaseObjDetailPanel;
import com.jy.torder.ui.util.ObjSelectDialog;
import com.jy.torder.util.BaseException;
import java.awt.Dimension;

public class OrderDetailPanel extends BaseObjDetailPanel<OrderObj> {

    private static final long serialVersionUID = 2988449035325387444L;

    private JTextField numberTextField;
    private JTextField passwordTextField;
    private JTextField specTextField;
    private JTextField mobileTextField;
    private JTextField nameTextField;
    private JTextField DeliverTextField;
    private JTextArea addrTextArea;
    private JTextArea descTextArea;
    private JComboBox<OrderObj.OrderStatus> statusComboBox;
    private JTextField startTextField;
    private JTextField endTextField;

    public class Condition {

        private String startDeliverDate = OrderObj.DATE_FORMAT.format(new Date(System.currentTimeMillis()));

        private String endDeliverDate = OrderObj.DATE_FORMAT.format(new Date(System.currentTimeMillis() + 3600 * 24 * 1000));

        public String getStartDeliverDate() {
            return startDeliverDate;
        }

        public void setStartDeliverDate(String startDeliverDate) {
            this.startDeliverDate = startDeliverDate;
        }

        public String getEndDeliverDate() {
            return endDeliverDate;
        }

        public void setEndDeliverDate(String endDeliverDate) {
            this.endDeliverDate = endDeliverDate;
        }
    }
    
    private Condition condition = new Condition();

    public OrderDetailPanel(OrderObj obj, int type) throws BaseException {
        super(obj, type);
        if (type != BaseObjDetailPanel.TYPE_QUERY) {
            setPreferredSize(new Dimension(400, 400));
            setMinimumSize(new Dimension(400, 400));
            setSize(new Dimension(400, 400));
        } else {
            setPreferredSize(new Dimension(700, 200));
            setMinimumSize(new Dimension(700, 200));
            setSize(new Dimension(700, 200));
        }

        if (type == BaseObjDetailPanel.TYPE_NEW) {
            this.obj.setDeliveryTime(OrderObj.DATE_FORMAT.format(new Date(System.currentTimeMillis())));
        }
        setLayout(new BorderLayout(0, 0));

        JPanel rootPanel = new JPanel();
        rootPanel.setSize(new Dimension(600, 800));
        rootPanel.setMinimumSize(new Dimension(600, 800));
        add(rootPanel, BorderLayout.CENTER);
        GridBagLayout gbl_rootPanel = new GridBagLayout();
        gbl_rootPanel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_rootPanel.rowWeights = new double[] { 0.0, 0.0 };
        rootPanel.setLayout(gbl_rootPanel);

        JPanel gtPanel = new JPanel();
        gtPanel.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u793C\u5238\u4FE1\u606F",
                TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(2, 2, 2, 2)));
        GridBagConstraints gbc_gtPanel = new GridBagConstraints();
        gbc_gtPanel.weightx = 1.0;
        if (type != BaseObjDetailPanel.TYPE_QUERY) {
            gbc_gtPanel.gridwidth = 1;
        } else {
            gbc_gtPanel.gridwidth = 2;
        }
        gbc_gtPanel.fill = GridBagConstraints.BOTH;
        gbc_gtPanel.insets = new Insets(0, 0, 5, 0);
        gbc_gtPanel.gridx = 0;
        gbc_gtPanel.gridy = 0;
        rootPanel.add(gtPanel, gbc_gtPanel);
        gtPanel.setLayout(new BorderLayout(0, 0));

        JPanel infoPanel = new JPanel();
        gtPanel.add(infoPanel, BorderLayout.CENTER);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
        gbl_infoPanel.rowHeights = new int[] { 0, 0, 0 };
        gbl_infoPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
        gbl_infoPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        infoPanel.setLayout(gbl_infoPanel);

        JLabel label = new JLabel("券号：");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.anchor = GridBagConstraints.EAST;
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        infoPanel.add(label, gbc_label);

        numberTextField = new JTextField();
        GridBagConstraints gbc_numberTextField = new GridBagConstraints();
        gbc_numberTextField.weightx = 1.0;
        gbc_numberTextField.insets = new Insets(0, 0, 5, 5);
        gbc_numberTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_numberTextField.gridx = 1;
        gbc_numberTextField.gridy = 0;
        infoPanel.add(numberTextField, gbc_numberTextField);
        numberTextField.setColumns(10);

        JLabel label_1 = new JLabel("密码：");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.EAST;
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 2;
        gbc_label_1.gridy = 0;
        infoPanel.add(label_1, gbc_label_1);

        passwordTextField = new JTextField();
        GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
        gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
        gbc_passwordTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordTextField.gridx = 3;
        gbc_passwordTextField.gridy = 0;
        infoPanel.add(passwordTextField, gbc_passwordTextField);
        passwordTextField.setColumns(10);

        JLabel label_2 = new JLabel("规格：");
        label_2.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.anchor = GridBagConstraints.EAST;
        gbc_label_2.insets = new Insets(0, 0, 0, 5);
        gbc_label_2.gridx = 0;
        gbc_label_2.gridy = 1;
        infoPanel.add(label_2, gbc_label_2);

        specTextField = new JTextField();
        GridBagConstraints gbc_specTextField = new GridBagConstraints();
        gbc_specTextField.weighty = 1.0;
        gbc_specTextField.weightx = 1.0;
        gbc_specTextField.insets = new Insets(0, 0, 0, 5);
        gbc_specTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_specTextField.gridx = 1;
        gbc_specTextField.gridy = 1;
        gbc_specTextField.gridwidth = 3;
        infoPanel.add(specTextField, gbc_specTextField);
        specTextField.setColumns(10);

        JPanel customerPanel = new JPanel();
        customerPanel.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5BA2\u6237\u4FE1\u606F",
                TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(2, 2, 2, 2)));
        GridBagConstraints gbc_customerPanel = new GridBagConstraints();
        gbc_customerPanel.weightx = 1.0;
        gbc_customerPanel.weighty = 1.0;
        gbc_customerPanel.fill = GridBagConstraints.BOTH;
        gbc_customerPanel.insets = new Insets(0, 0, 0, 5);
        gbc_customerPanel.gridx = 0;
        gbc_customerPanel.gridy = 1;
        rootPanel.add(customerPanel, gbc_customerPanel);
        customerPanel.setLayout(new BorderLayout(0, 0));

        JPanel cInfopanel = new JPanel();
        customerPanel.add(cInfopanel, BorderLayout.CENTER);
        GridBagLayout gbl_cInfopanel = new GridBagLayout();
        gbl_cInfopanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0 };
        gbl_cInfopanel.rowWeights = new double[] { 0.0, 1.0 };
        cInfopanel.setLayout(gbl_cInfopanel);

        JLabel label_3 = new JLabel("姓名：");
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.anchor = GridBagConstraints.EAST;
        gbc_label_3.insets = new Insets(0, 0, 5, 5);
        gbc_label_3.gridx = 0;
        gbc_label_3.gridy = 0;
        cInfopanel.add(label_3, gbc_label_3);

        nameTextField = new JTextField();
        GridBagConstraints gbc_nameTextField = new GridBagConstraints();
        gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
        gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 0;
        cInfopanel.add(nameTextField, gbc_nameTextField);
        nameTextField.setColumns(10);

        JLabel label_4 = new JLabel("电话：");
        GridBagConstraints gbc_label_4 = new GridBagConstraints();
        gbc_label_4.anchor = GridBagConstraints.EAST;
        gbc_label_4.insets = new Insets(0, 0, 5, 5);
        gbc_label_4.gridx = 2;
        gbc_label_4.gridy = 0;
        cInfopanel.add(label_4, gbc_label_4);

        mobileTextField = new JTextField();
        GridBagConstraints gbc_mobileTextField = new GridBagConstraints();
        gbc_mobileTextField.insets = new Insets(0, 0, 5, 0);
        gbc_mobileTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_mobileTextField.gridx = 3;
        gbc_mobileTextField.gridy = 0;
        cInfopanel.add(mobileTextField, gbc_mobileTextField);
        mobileTextField.setColumns(10);

        JLabel label_5 = new JLabel("地址：");
        GridBagConstraints gbc_label_5 = new GridBagConstraints();
        gbc_label_5.insets = new Insets(0, 0, 0, 5);
        gbc_label_5.gridx = 0;
        gbc_label_5.gridy = 1;
        cInfopanel.add(label_5, gbc_label_5);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.weighty = 1.0;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 1;
        cInfopanel.add(scrollPane, gbc_scrollPane);

        addrTextArea = new JTextArea();
        addrTextArea.setColumns(10);
        scrollPane.setViewportView(addrTextArea);
        addrTextArea.setRows(2);

        JPanel cButtonPanel = new JPanel();
        customerPanel.add(cButtonPanel, BorderLayout.EAST);
        cButtonPanel.setLayout(new BorderLayout(0, 0));

        if (type == BaseObjDetailPanel.TYPE_NEW) {
            JButton cSelectButton = new JButton("选择.");
            cSelectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ObjSelectDialog<CustomerObj> customerListDialog;
                    try {
                        customerListDialog = new ObjSelectDialog<CustomerObj>(SwingUtilities.windowForComponent(OrderDetailPanel.this),
                                CustomerObj.class);
                        customerListDialog.setVisible(true);
                        if (customerListDialog.isSelected()) {
                            CustomerObj customerObj = customerListDialog.getSelectObj();
                            OrderDetailPanel.this.obj.setCustomer(customerObj);
                            OrderDetailPanel.this.nameTextField.setText(customerObj.getName());
                            OrderDetailPanel.this.mobileTextField.setText(customerObj.getMobile());
                            OrderDetailPanel.this.addrTextArea.setText(customerObj.getAddr());
                        }
                    } catch (BaseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            cButtonPanel.add(cSelectButton);
        }

        if (type == BaseObjDetailPanel.TYPE_NEW) {
            JPanel cNoticePanel = new JPanel();
            customerPanel.add(cNoticePanel, BorderLayout.SOUTH);

            JLabel label_6 = new JLabel("注：如果是新客户可以直接填写登记");
            cNoticePanel.add(label_6);
        }

        JPanel deliverPanel = new JPanel();
        deliverPanel.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u53D1\u8D27\u4FE1\u606F",
                TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(2, 2, 2, 2)));
        GridBagConstraints gbc_deliverPanel = new GridBagConstraints();
        gbc_deliverPanel.weightx = 1.0;
        gbc_deliverPanel.weighty = 1.0;
        gbc_deliverPanel.fill = GridBagConstraints.BOTH;
        if (type != BaseObjDetailPanel.TYPE_QUERY) {
            gbc_deliverPanel.gridx = 0;
            gbc_deliverPanel.gridy = 2;
        } else {
            gbc_deliverPanel.gridx = 1;
            gbc_deliverPanel.gridy = 1;
        }
        rootPanel.add(deliverPanel, gbc_deliverPanel);
        GridBagLayout gbl_deliverPanel = new GridBagLayout();
        deliverPanel.setLayout(gbl_deliverPanel);

        if (type != BaseObjDetailPanel.TYPE_QUERY) {
            JLabel label_7 = new JLabel("时间：");
            GridBagConstraints gbc_label_7 = new GridBagConstraints();
            gbc_label_7.anchor = GridBagConstraints.EAST;
            gbc_label_7.insets = new Insets(0, 0, 5, 5);
            gbc_label_7.gridx = 0;
            gbc_label_7.gridy = 0;
            deliverPanel.add(label_7, gbc_label_7);

            DeliverTextField = new JTextField();
            GridBagConstraints gbc_DeliverTextField = new GridBagConstraints();
            gbc_DeliverTextField.weightx = 1.0;
            gbc_DeliverTextField.gridwidth = 5;
            gbc_DeliverTextField.insets = new Insets(0, 0, 5, 5);
            gbc_DeliverTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_DeliverTextField.gridx = 1;
            gbc_DeliverTextField.gridy = 0;
            deliverPanel.add(DeliverTextField, gbc_DeliverTextField);
            DeliverTextField.setColumns(10);
        }

        JLabel label_8 = new JLabel("状态：");
        GridBagConstraints gbc_label_8 = new GridBagConstraints();
        gbc_label_8.insets = new Insets(0, 0, 5, 5);
        gbc_label_8.anchor = GridBagConstraints.EAST;
        gbc_label_8.gridx = 0;
        gbc_label_8.gridy = 1;
        deliverPanel.add(label_8, gbc_label_8);

        statusComboBox = new JComboBox<OrderObj.OrderStatus>();
        statusComboBox.setModel(new DefaultComboBoxModel<OrderObj.OrderStatus>(new OrderObj.OrderStatus[] { OrderObj.NOT_DELIVER_STATUS,
                OrderObj.HAVE_DELIVER_STATUS }));
        GridBagConstraints gbc_statusComboBox = new GridBagConstraints();
        gbc_statusComboBox.weightx = 1.0;
        gbc_statusComboBox.insets = new Insets(0, 0, 5, 0);
        gbc_statusComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_statusComboBox.gridx = 1;
        gbc_statusComboBox.gridy = 1;
        deliverPanel.add(statusComboBox, gbc_statusComboBox);

        if (type == BaseObjDetailPanel.TYPE_QUERY) {
            JLabel label_6 = new JLabel("开始：");
            GridBagConstraints gbc_label_6 = new GridBagConstraints();
            gbc_label_6.anchor = GridBagConstraints.EAST;
            gbc_label_6.insets = new Insets(0, 0, 5, 5);
            gbc_label_6.gridx = 2;
            gbc_label_6.gridy = 1;
            deliverPanel.add(label_6, gbc_label_6);

            startTextField = new JTextField();
            GridBagConstraints gbc_startTextField = new GridBagConstraints();
            gbc_startTextField.weightx = 1.0;
            gbc_startTextField.insets = new Insets(0, 0, 5, 5);
            gbc_startTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_startTextField.gridx = 3;
            gbc_startTextField.gridy = 1;
            deliverPanel.add(startTextField, gbc_startTextField);
            startTextField.setColumns(10);

            JLabel label_10 = new JLabel("截止：");
            GridBagConstraints gbc_label_10 = new GridBagConstraints();
            gbc_label_10.anchor = GridBagConstraints.EAST;
            gbc_label_10.insets = new Insets(0, 0, 5, 5);
            gbc_label_10.gridx = 4;
            gbc_label_10.gridy = 1;
            deliverPanel.add(label_10, gbc_label_10);

            endTextField = new JTextField();
            GridBagConstraints gbc_endTextField = new GridBagConstraints();
            gbc_endTextField.weightx = 1.0;
            gbc_endTextField.insets = new Insets(0, 0, 5, 5);
            gbc_endTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_endTextField.gridx = 5;
            gbc_endTextField.gridy = 1;
            deliverPanel.add(endTextField, gbc_endTextField);
            endTextField.setColumns(10);

        }
        JLabel label_9 = new JLabel("描述：");
        GridBagConstraints gbc_label_9 = new GridBagConstraints();
        gbc_label_9.insets = new Insets(0, 0, 5, 5);
        gbc_label_9.gridx = 0;
        gbc_label_9.gridy = 2;
        deliverPanel.add(label_9, gbc_label_9);

        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.weighty = 1.0;
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridwidth = 5;
        gbc_scrollPane_1.gridx = 1;
        gbc_scrollPane_1.gridy = 2;
        deliverPanel.add(scrollPane_1, gbc_scrollPane_1);

        descTextArea = new JTextArea();
        scrollPane_1.setViewportView(descTextArea);
        descTextArea.setRows(2);
        descTextArea.setColumns(10);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        gtPanel.add(panel, BorderLayout.EAST);
        panel.setLayout(new BorderLayout(0, 0));

        if (type == BaseObjDetailPanel.TYPE_NEW) {
            JButton btnNewButton = new JButton("选择.");
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        final ObjSelectDialog<GiftTicketObj> gtListDialog = new ObjSelectDialog<GiftTicketObj>(SwingUtilities
                                .windowForComponent(OrderDetailPanel.this), GiftTicketObj.class);
                        gtListDialog.setVisible(true);
                        if (gtListDialog.isSelected()) {
                            GiftTicketObj gtObj = gtListDialog.getSelectObj();
                            OrderDetailPanel.this.obj.setGiftTick(gtObj);
                            OrderDetailPanel.this.numberTextField.setText(gtObj.getNumber());
                            OrderDetailPanel.this.passwordTextField.setText(gtObj.getPasswd());
                            OrderDetailPanel.this.specTextField.setText(gtObj.getSpec());
                        }

                    } catch (BaseException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            panel.add(btnNewButton, BorderLayout.CENTER);
        }
        initDataBindings();
    }

    @Override
    public Map<String, Object> getCondition() throws BaseException {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            ret.put(OrderService.COND_START_DELIVERYTIME, new Timestamp(OrderObj.DATE_FORMAT.parse(condition.getStartDeliverDate()).getTime()));
            ret.put(OrderService.COND_END_DELIVERYTIME, new Timestamp(OrderObj.DATE_FORMAT.parse(condition.getEndDeliverDate()).getTime()));
        } catch (ParseException e) {
            throw new BaseException("时间格式非法", e);
        }
        return ret;
    }

    @Override
    protected OrderObj createEmptyObj() {
        return new OrderObj();
    }

    protected void initDataBindings() {

        BeanProperty<OrderObj, String> orderObjBeanProperty = BeanProperty.create("addr");
        BeanProperty<JTextArea, String> jTextAreaBeanProperty = BeanProperty.create("text");
        AutoBinding<OrderObj, String, JTextArea, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty, addrTextArea, jTextAreaBeanProperty);
        autoBinding.bind();
        //
        BeanProperty<OrderObj, String> orderObjBeanProperty_1 = BeanProperty.create("desc");
        BeanProperty<JTextArea, String> jTextAreaBeanProperty_1 = BeanProperty.create("text");
        AutoBinding<OrderObj, String, JTextArea, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty_1, descTextArea, jTextAreaBeanProperty_1);
        autoBinding_1.bind();
        //
        BeanProperty<OrderObj, OrderStatus> orderObjBeanProperty_2 = BeanProperty.create("status");
        BeanProperty<JComboBox<OrderStatus>, OrderStatus> jComboBoxBeanProperty = BeanProperty.create("selectedItem");
        AutoBinding<OrderObj, OrderStatus, JComboBox<OrderStatus>, OrderStatus> autoBinding_2 = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE, obj, orderObjBeanProperty_2, statusComboBox, jComboBoxBeanProperty);
        autoBinding_2.bind();
        //
        BeanProperty<OrderObj, Timestamp> orderObjBeanProperty_3 = BeanProperty.create("deliveryTime");
        BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
        AutoBinding<OrderObj, Timestamp, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty_3, DeliverTextField, jTextFieldBeanProperty);
        autoBinding_3.bind();
        //
        BeanProperty<OrderObj, String> orderObjBeanProperty_4 = BeanProperty.create("number");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
        AutoBinding<OrderObj, String, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty_4, numberTextField, jTextFieldBeanProperty_1);
        autoBinding_4.bind();
        //
        BeanProperty<OrderObj, String> orderObjBeanProperty_5 = BeanProperty.create("passwd");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
        AutoBinding<OrderObj, String, JTextField, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty_5, passwordTextField, jTextFieldBeanProperty_2);
        autoBinding_5.bind();
        //
        BeanProperty<OrderObj, String> orderObjBeanProperty_6 = BeanProperty.create("spec");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
        AutoBinding<OrderObj, String, JTextField, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty_6, specTextField, jTextFieldBeanProperty_3);
        autoBinding_6.bind();
        //
        BeanProperty<OrderObj, String> orderObjBeanProperty_7 = BeanProperty.create("name");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
        AutoBinding<OrderObj, String, JTextField, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty_7, nameTextField, jTextFieldBeanProperty_4);
        autoBinding_7.bind();
        //
        BeanProperty<OrderObj, String> orderObjBeanProperty_8 = BeanProperty.create("mobile");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_5 = BeanProperty.create("text");
        AutoBinding<OrderObj, String, JTextField, String> autoBinding_8 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj,
                orderObjBeanProperty_8, mobileTextField, jTextFieldBeanProperty_5);
        autoBinding_8.bind();

        BeanProperty<Condition, String> conditionBeanProperty_1 = BeanProperty.create("startDeliverDate");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_9 = BeanProperty.create("text");
        AutoBinding<Condition, String, JTextField, String> autoBinding_9 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, condition,
                conditionBeanProperty_1, startTextField, jTextFieldBeanProperty_9);
        autoBinding_9.bind();
        //
        BeanProperty<Condition, String> conditionBeanProperty_2 = BeanProperty.create("endDeliverDate");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_10 = BeanProperty.create("text");
        AutoBinding<Condition, String, JTextField, String> autoBinding_10 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, condition,
                conditionBeanProperty_2, endTextField, jTextFieldBeanProperty_10);
        autoBinding_10.bind();
    }
}
