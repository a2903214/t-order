package com.jy.torder.ui.objdetail;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import com.jy.torder.model.GiftTicketObj;
import com.jy.torder.ui.util.BaseObjDetailPanel;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import java.awt.Dimension;

public class GiftTicketDetailPanel extends BaseObjDetailPanel<GiftTicketObj>  {
    /**
     * 
     */
    private static final long serialVersionUID = 7423243082493935189L;
    private final JPanel contentPanel = new JPanel();
    private JTextField numberTextField;
    private JTextField passwdTextField;

    private JTextField specTextField;
    
    /**
     * Create the dialog.
     */
    public GiftTicketDetailPanel(GiftTicketObj obj, int type) {
        super(obj, type);
        setMinimumSize(new Dimension(400, 120));
        setPreferredSize(new Dimension(400, 120));
        setSize(new Dimension(400, 120));
        setBounds(100, 100, 401, 223);
        this.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
        gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel label = new JLabel("卡号：");
            GridBagConstraints gbc_label = new GridBagConstraints();
            gbc_label.weighty = 1.0;
            gbc_label.ipady = 1;
            gbc_label.anchor = GridBagConstraints.SOUTHEAST;
            gbc_label.insets = new Insets(0, 0, 5, 5);
            gbc_label.gridx = 0;
            gbc_label.gridy = 0;
            contentPanel.add(label, gbc_label);
        }
        {
            numberTextField = new JTextField();
            GridBagConstraints gbc_numberTextField = new GridBagConstraints();
            gbc_numberTextField.anchor = GridBagConstraints.SOUTH;
            gbc_numberTextField.ipadx = 1;
            gbc_numberTextField.weightx = 1.0;
            gbc_numberTextField.insets = new Insets(0, 0, 5, 0);
            gbc_numberTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberTextField.gridx = 1;
            gbc_numberTextField.gridy = 0;
            contentPanel.add(numberTextField, gbc_numberTextField);
            numberTextField.setColumns(10);
        }
        {
            JLabel label = new JLabel("密码：");
            GridBagConstraints gbc_label = new GridBagConstraints();
            gbc_label.anchor = GridBagConstraints.EAST;
            gbc_label.insets = new Insets(0, 0, 5, 5);
            gbc_label.gridx = 0;
            gbc_label.gridy = 1;
            contentPanel.add(label, gbc_label);
        }
        {
            passwdTextField = new JTextField();
            GridBagConstraints gbc_passwdTextField = new GridBagConstraints();
            gbc_passwdTextField.weightx = 1.0;
            gbc_passwdTextField.insets = new Insets(0, 0, 5, 0);
            gbc_passwdTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_passwdTextField.gridx = 1;
            gbc_passwdTextField.gridy = 1;
            contentPanel.add(passwdTextField, gbc_passwdTextField);
            passwdTextField.setColumns(10);
        }
        {
            JLabel label = new JLabel("规格：");
            GridBagConstraints gbc_label = new GridBagConstraints();
            gbc_label.anchor = GridBagConstraints.EAST;
            gbc_label.insets = new Insets(0, 0, 5, 5);
            gbc_label.gridx = 0;
            gbc_label.gridy = 2;
            contentPanel.add(label, gbc_label);
        }
        {
            specTextField = new JTextField();
            GridBagConstraints gbc_specTextField = new GridBagConstraints();
            gbc_specTextField.insets = new Insets(0, 0, 5, 0);
            gbc_specTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_specTextField.gridx = 1;
            gbc_specTextField.gridy = 2;
            contentPanel.add(specTextField, gbc_specTextField);
            specTextField.setColumns(10);
        }
        
        initDataBindings();
    }

    protected void initDataBindings() {
        BeanProperty<GiftTicketObj, String> giftTicketObjBeanProperty = BeanProperty.create("number");
        BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
        AutoBinding<GiftTicketObj, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj, giftTicketObjBeanProperty, numberTextField, jTextFieldBeanProperty);
        autoBinding.bind();
        //
        BeanProperty<GiftTicketObj, String> giftTicketObjBeanProperty_1 = BeanProperty.create("passwd");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
        AutoBinding<GiftTicketObj, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj, giftTicketObjBeanProperty_1, passwdTextField, jTextFieldBeanProperty_1);
        autoBinding_1.bind();
        //
        BeanProperty<GiftTicketObj, String> giftTicketObjBeanProperty_2 = BeanProperty.create("spec");
        BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
        AutoBinding<GiftTicketObj, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, obj, giftTicketObjBeanProperty_2, specTextField, jTextFieldBeanProperty_2);
        autoBinding_2.bind();
    }

    @Override
    public Map<String, Object> getCondition() {
        return new HashMap<String, Object>();
    }

    @Override
    protected GiftTicketObj createEmptyObj() {
        return new GiftTicketObj();
    }
}
