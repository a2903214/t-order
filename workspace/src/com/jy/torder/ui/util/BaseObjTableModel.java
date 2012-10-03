package com.jy.torder.ui.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class BaseObjTableModel<T> extends AbstractTableModel {

    protected List<T> objList;

    private static final long serialVersionUID = -8839560327883092154L;

    protected BaseObjTableModel(List<T> objList) {
        this.objList = objList;
        if (null == this.objList) {
            this.objList = new ArrayList<T>();
        }
    }

    public List<T> getObjList() {
        return objList;
    }

    public void setObjList(List<T> objList) {
        this.objList = objList;
        super.fireTableDataChanged();
    }

    public int getRowCount() {
        return this.objList.size();
    }

    public T getObj(int rowIndex) {
        if (rowIndex < objList.size()) {
            return this.objList.get(rowIndex);
        } else {
            return null;
        }
    }

    public void addObj(T obj) {
        if (null != obj) {
            objList.add(obj);
        }
        super.fireTableDataChanged();
    }
    
    public void delObj(int rowIndex){
        if(objList!=null){
            objList.remove(rowIndex);
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
    

    public Object getValueAt(int rowIndex, int columnIndex) {
        return getValueAt(objList.get(rowIndex), columnIndex);
    }
    
    public abstract Object getValueAt(T obj, int columnIndex);

    public String printColumnDef(){
        StringBuilder colNameDef = new StringBuilder();
        for( int i=0; i<this.getColumnCount(); i++ ){
            if(i!=0){
                colNameDef.append(",");
            }
            colNameDef.append(this.getColumnName(i));
        }
        return colNameDef.toString();
    }
    
    public String printObj(T obj){
        StringBuilder objValues = new StringBuilder();
        for( int i=0; i<this.getColumnCount(); i++ ){
            if(i!=0){
                objValues.append("\",");
            }
            objValues.append("\"").append(this.getValueAt(obj, i).toString().replaceAll("\"", "\"\""));
        }
        objValues.append("\"");
        return objValues.toString();
        
    }

}
