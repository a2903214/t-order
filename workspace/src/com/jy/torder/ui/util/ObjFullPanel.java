package com.jy.torder.ui.util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jy.torder.service.BaseObjService;
import com.jy.torder.service.ServiceEnv;
import com.jy.torder.util.BaseException;

public class ObjFullPanel<ObjType> extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -794237606218758163L;
    protected JPanel contentPane;
    protected JTable table;

    protected final Class<ObjType> objClass;

    protected BaseObjTableModel<ObjType> tableModel;
    protected BaseObjService<ObjType> service;
    protected BaseObjDetailPanel<ObjType> conditionPanel;
    protected ObjType selectObj;
    protected boolean selected;
    protected JDialog dialog;
    private Integer currentPage = 1;
    private int pageSize = 20;
    private JButton preButton;
    private JButton nextButton;
    private JTextField pageNumTextField;
    private JFileChooser fileChooser;

    /**
     * Create the frame.
     * 
     * @throws BaseException
     */
    public ObjFullPanel() throws BaseException {
        this(false, null);
    }

    @SuppressWarnings("unchecked")
    public ObjFullPanel(boolean isInDialog, JDialog dialog) throws BaseException {

        objClass = (Class<ObjType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        service = ServiceEnv.getService(objClass);

        this.dialog = dialog;

        setBounds(100, 100, 600, 409);
        setLayout(new BorderLayout(0, 0));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));

        conditionPanel = UIFactory.createObjDetailPanel(null, BaseObjDetailPanel.TYPE_QUERY, objClass);

        conditionPanel.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
                "\u67E5\u8BE2\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(5, 5, 5, 5)));
        panel.add(conditionPanel, BorderLayout.CENTER);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new EmptyBorder(20, 5, 20, 5));
        panel.add(panel_3, BorderLayout.EAST);
        panel_3.setLayout(new GridLayout(0, 1, 0, 0));

        JButton queryButton = new JButton("查询");
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                query();
            }
        });
        panel_3.add(queryButton);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        tableModel = UIFactory.createObjTableModel(new ArrayList<ObjType>(), objClass);
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (1 < e.getClickCount()) {
                    int rowIndex = table.getSelectedRow();
                    if (-1 != rowIndex) {
                        selectObj = tableModel.getObj(rowIndex);
                        try {
                            ObjDetailDialog<ObjType> detailDialog = new ObjDetailDialog<ObjType>(SwingUtilities
                                    .windowForComponent(ObjFullPanel.this), selectObj, objClass);
                            detailDialog.setLocationRelativeTo(ObjFullPanel.this);
                            detailDialog.setVisible(true);
                            if (detailDialog.isSuccessful()) {
                                tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
                            } else {
                                query();
                            }
                        } catch (BaseException e1) {
                            JOptionPane.showMessageDialog(ObjFullPanel.this, "读取所选行数据出错", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        table.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_1.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel_4 = new JPanel();
        panel_1.add(panel_4, BorderLayout.SOUTH);

        JButton firstButton = new JButton("首页");
        firstButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentPage = 1;
                query();
            }
        });
        panel_4.add(firstButton);

        preButton = new JButton("上一页");
        preButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage--;
                }
                query();
            }
        });
        preButton.setEnabled(false);
        panel_4.add(preButton);

        JLabel label = new JLabel("第");
        panel_4.add(label);

        pageNumTextField = new JTextField();
        pageNumTextField.setEditable(false);
        pageNumTextField.setText("1");
        panel_4.add(pageNumTextField);
        pageNumTextField.setColumns(4);

        JLabel label_1 = new JLabel("页");
        panel_4.add(label_1);

        nextButton = new JButton("下一页");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentPage++;
                preButton.setEnabled(true);
                if (!query()) {
                    currentPage--;
                    refreshPageNum();
                }
            }
        });
        panel_4.add(nextButton);

        JButton expButton = new JButton("导出");
        expButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(ObjFullPanel.this)) {
                    File file = fileChooser.getSelectedFile();
                    String filepath = file.getAbsolutePath();
                    if(!filepath.endsWith("csv")){
                        filepath = filepath + ".csv";
                    }
                    file = new File(filepath);
                    try {
                        export(file);
                    } catch (BaseException e1) {
                        JOptionPane.showMessageDialog(ObjFullPanel.this, e1.getLocalizedMessage(), "数据导出错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel_4.add(expButton);

        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.SOUTH);

        if (isInDialog) {
            JButton selectButton = new JButton("选择");
            selectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int rowIndex = table.getSelectedRow();
                    if (-1 != rowIndex) {
                        selectObj = tableModel.getObj(rowIndex);
                        selected = true;
                        ObjFullPanel.this.dialog.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(ObjFullPanel.this, "尚未选择任何数据", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panel_2.add(selectButton);
        }

        JButton button = new JButton("新建");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ObjCreateDialog<ObjType> dialog = new ObjCreateDialog<ObjType>(ObjFullPanel.this.dialog, objClass, true);
                dialog.setVisible(true);
                if (dialog.isSuccessful()) {
                    ObjFullPanel.this.tableModel.addObj(dialog.getObj());
                }
            }
        });
        panel_2.add(button);

        JButton delButton = new JButton("删除");
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] rowIndexs = table.getSelectedRows();
                if (null != rowIndexs) {
                    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(ObjFullPanel.this, "是否要删除该行数据！", "请确认",
                            JOptionPane.YES_NO_OPTION)) {
                        try {
                            int delCount = 0;
                            for (int rowIndex : rowIndexs) {
                                ObjType delObj = tableModel.getObj(rowIndex - delCount);
                                service.delete(delObj);
                                tableModel.delObj(rowIndex - delCount);
                                delCount++;
                            }
                        } catch (BaseException ex) {
                            JOptionPane.showMessageDialog(ObjFullPanel.this, ex.getLocalizedMessage(), "删除该行数据失败",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        panel_2.add(delButton);

        if (isInDialog) {
            JButton cancelButton = new JButton("取消");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ObjFullPanel.this.dialog.setVisible(false);
                }
            });
            panel_2.add(cancelButton);
        }
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("逗号分隔符文件", "csv"));
    }

    public boolean isSelected() {
        return selected;
    }

    public ObjType getSelectObj() {
        return this.selectObj;
    }

    private boolean query() {
        boolean haveResult = false;
        try {
            List<ObjType> objList = service.findAll(conditionPanel.getObj(), conditionPanel.getCondition(), currentPage, pageSize);
            haveResult = (!objList.isEmpty());
            if (currentPage == 1 || haveResult) {
                tableModel.setObjList(objList);
            }
        } catch (BaseException e1) {
            JOptionPane.showMessageDialog(ObjFullPanel.this, e1.getLocalizedMessage(), "查询数据出错", JOptionPane.ERROR_MESSAGE);
        } finally {
            refreshPageNum();
        }
        return haveResult;
    }

    private void export(File file) throws BaseException {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "GBK");
            List<ObjType> objList = service.findAll(conditionPanel.getObj(), conditionPanel.getCondition(), null, null);
            writer.write(tableModel.printColumnDef());
            writer.write(System.lineSeparator());
            for(ObjType obj : objList){
                writer.write(tableModel.printObj(obj));
                writer.write(System.lineSeparator());
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new BaseException(e);
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    private void refreshPageNum() {
        pageNumTextField.setText(currentPage.toString());
        if (currentPage == 1) {
            preButton.setEnabled(false);
        } else {
            preButton.setEnabled(true);
        }
    }
}
