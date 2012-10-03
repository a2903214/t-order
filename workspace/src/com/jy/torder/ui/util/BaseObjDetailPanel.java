package com.jy.torder.ui.util;

import java.util.Map;

import javax.swing.JPanel;

import com.jy.torder.util.BaseException;

public abstract class BaseObjDetailPanel<T> extends JPanel {
    private static final long serialVersionUID = -3062510639253640963L;

    public static final int TYPE_NEW = 1;
    public static final int TYPE_DETAIL = 2;
    public static final int TYPE_QUERY = 3;

    protected T obj;

    public BaseObjDetailPanel(T obj, int type) {
        if (null == obj) {
            this.obj = createEmptyObj();
        } else {
            this.obj = obj;
        }
    }

    public T getObj() {
        return obj;
    }

    protected abstract T createEmptyObj();

    public abstract Map<String, Object> getCondition() throws BaseException;
}
