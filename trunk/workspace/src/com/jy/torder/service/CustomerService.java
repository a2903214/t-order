package com.jy.torder.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jy.torder.db.ConnInfo;
import com.jy.torder.model.CustomerObj;
import com.jy.torder.util.BaseException;

public class CustomerService extends BaseObjService<CustomerObj> {

    public CustomerService(ConnInfo connInfo) throws BaseException {
        super(connInfo);
    }

    @Override
    public void add(final CustomerObj customerObj) throws BaseException {

        this.executeSQL("insert into t_customer(c_name, c_mobile, c_addr) values (?,?,?)", new String[] { "c_id" },
                new DBUpdateProcessAdapter() {

                    public void addParameters(PreparedStatement ps) throws SQLException {
                        int i = 0;
                        ps.setString(++i, customerObj.getName());
                        ps.setString(++i, customerObj.getMobile());
                        ps.setString(++i, customerObj.getAddr());

                    }

                    public void processGeneratedKeys(ResultSet result) throws SQLException {
                        if (result.next()) {
                            customerObj.setId(result.getLong(1));
                        }
                    }

                });
    }

    @Override
    public List<CustomerObj> findAll(CustomerObj customerObj, Map<String, Object> queryCondition, Integer pageNum, Integer pageSize)
            throws BaseException {
        final List<CustomerObj> customerObjList = new ArrayList<CustomerObj>();
        StringBuilder sql = new StringBuilder("select c_id, c_name, c_mobile, c_addr from t_customer where 1=1 ");
        if (null != customerObj.getId()) {
            sql.append(" and c_id = ").append(customerObj.getId());
        } else {

            if (null != customerObj.getName() && !customerObj.getName().isEmpty()) {
                sql.append(" and c_name = '").append(customerObj.getName()).append("'");
            }

            if (null != customerObj.getMobile() && !customerObj.getMobile().isEmpty()) {
                sql.append(" and c_mobile = '").append(customerObj.getMobile()).append("'");
            }

            if (null != customerObj.getAddr() && !customerObj.getAddr().isEmpty()) {
                sql.append(" and c_addr like  '%").append(customerObj.getAddr().replaceAll(" ", "%")).append("%'");
            }

            if (null != pageNum && null != pageSize) {
                sql.append(" limit ").append(pageSize).append(" offset ").append((pageNum - 1) * pageSize);
            }
        }

        this.executeSQL(sql.toString(), new DBQueryProcessNoParamAdapter() {

            public void resultProcess(ResultSet result) throws SQLException {
                while (result.next()) {
                    CustomerObj tmpCustomerObj = new CustomerObj();
                    int i = 0;
                    tmpCustomerObj.setId(result.getLong(++i));
                    tmpCustomerObj.setName(result.getString(++i));
                    tmpCustomerObj.setMobile(result.getString(++i));
                    tmpCustomerObj.setAddr(result.getString(++i));
                    customerObjList.add(tmpCustomerObj);
                }
            }
        });

        return customerObjList;

    }

    @Override
    public void delete(CustomerObj obj) throws BaseException {
        if (null != obj.getId()) {
            StringBuilder sql = new StringBuilder("delete from t_customer where c_id = ");
            sql.append(obj.getId());
            this.executeSQL(sql.toString());
        }
    }

    @Override
    public void modify(final CustomerObj obj) throws BaseException {
        if (null != obj.getId()) {
            StringBuilder sql = new StringBuilder("update t_customer set c_name = ?, c_mobile = ?, c_addr = ? where c_id = ?");
            this.executeSQL(sql.toString(), new DBUpdateNoKeyProcessAdapter() {

                public void addParameters(PreparedStatement ps) throws SQLException {
                    int i = 0;
                    ps.setString(++i, obj.getName());
                    ps.setString(++i, obj.getMobile());
                    ps.setString(++i, obj.getAddr());
                    ps.setLong(++i, obj.getId());
                }

            });
        }
    }
}
