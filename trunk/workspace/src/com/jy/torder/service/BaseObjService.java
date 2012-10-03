package com.jy.torder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jy.torder.db.ConnInfo;
import com.jy.torder.util.BaseException;

public abstract class BaseObjService<T> extends BaseService {

    protected BaseObjService(ConnInfo connInfo) throws BaseException {
        super(connInfo);
    }

    public abstract void add(final T obj) throws BaseException;
    public abstract void delete(T obj) throws BaseException;
    public abstract void modify(T obj) throws BaseException;

    public List<T> findAll(T obj) throws BaseException {
        return findAll(obj, new HashMap<String, Object>(), null, null);
    }

    public abstract List<T> findAll(T obj, Map<String, Object> queryCondition, Integer pageNum, Integer pageSize) throws BaseException;
    

}
