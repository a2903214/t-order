package com.jy.torder.ui.objdetail;

import java.util.HashMap;
import java.util.Map;

import com.jy.torder.model.CustomerObj;
import com.jy.torder.ui.util.BaseObjDetailPanel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import java.awt.Dimension;

public class CustomerDetailPanel extends BaseObjDetailPanel<CustomerObj> {

    public CustomerDetailPanel(CustomerObj obj, int type) {
        super(obj, type);
        setPreferredSize(new Dimension(400, 120));
        setMinimumSize(new Dimension(400, 120));
        setSize(new Dimension(400, 120));
        setLayout(new BorderLayout(0, 0));
        
        JPanel rootPanel = new JPanel();
        rootPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(rootPanel, BorderLayout.CENTER);
        GridBagLayout gbl_rootPanel = new GridBagLayout();
        gbl_rootPanel.columnWidths = new int[]{0, 0, 0};
        gbl_rootPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_rootPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_rootPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        rootPanel.setLayout(gbl_rootPanel);
        
        JLabel label = new JLabel("客户姓名：");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.anchor = GridBagConstraints.SOUTHEAST;
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        rootPanel.add(label, gbc_label);
        
        nameTextField = new JTextField();
        GridBagConstraints gbc_nameTextField = new GridBagConstraints();
        gbc_nameTextField.anchor = GridBagConstraints.SOUTH;
        gbc_nameTextField.weighty = 1.0;
        gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
        gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 0;
        rootPanel.add(nameTextField, gbc_nameTextField);
        nameTextField.setColumns(10);
        
        JLabel label_1 = new JLabel("联系电话：");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.EAST;
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 1;
        rootPanel.add(label_1, gbc_label_1);
        
        mobileTextField = new JTextField();
        GridBagConstraints gbc_mobileTextField = new GridBagConstraints();
        gbc_mobileTextField.insets = new Insets(0, 0, 5, 0);
        gbc_mobileTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_mobileTextField.gridx = 1;
        gbc_mobileTextField.gridy = 1;
        rootPanel.add(mobileTextField, gbc_mobileTextField);
        mobileTextField.setColumns(10);
        
        JLabel label_2 = new JLabel("发货地址：");
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.anchor = GridBagConstraints.NORTHEAST;
        gbc_label_2.insets = new Insets(0, 0, 0, 5);
        gbc_label_2.gridx = 0;
        gbc_label_2.gridy = 2;
        rootPanel.add(label_2, gbc_label_2);
        
        addrTextField = new JTextField();
        GridBagConstraints gbc_addrTextField = new GridBagConstraints();
        gbc_addrTextField.weighty = 1.0;
        gbc_addrTextField.anchor = GridBagConstraints.NORTH;
        gbc_addrTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_addrTextField.gridx = 1;
        gbc_addrTextField.gridy = 2;
        rootPanel.add(addrTextField, gbc_addrTextField);
        addrTextField.setColumns(10);
        initDataBindings();
    }

    private static final long serialVersionUID = -6701347160386204673L;
    private JTextField nameTextField;
    private JTextField mobileTextField;
    private JTextField addrTextField;

    @Override
    public Map<String, Object> getCondition() {
        return new HashMap<String, Object>();
    }

    @Override
    protected CustomerObj createEmptyObj() {
        return new CustomerObj();
    }
    protected void initDataBindings() {
        BeanProperty<CustomerObj, String> customerObjBeanProperty = BeanProperty.create("name");
        BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
        AutoBinding<CustomerObj, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj, customerObjBeanProperty, nameTextField, jTextFieldBeanProperty);
        autoBinding.bind();
        //
        BeanProperty<CustomerObj, String> customerObjBeanProperty_1 = BeanProperty.create("mobile");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
        AutoBinding<CustomerObj, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj, customerObjBeanProperty_1, mobileTextField, jTextFieldBeanProperty_1);
        autoBinding_1.bind();
        //
        BeanProperty<CustomerObj, String> customerObjBeanProperty_2 = BeanProperty.create("addr");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
        AutoBinding<CustomerObj, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj, customerObjBeanProperty_2, addrTextField, jTextFieldBeanProperty_2);
        autoBinding_2.bind();
    }
}
