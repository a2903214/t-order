package com.jy.torder.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jy.torder.db.ConnInfo;
import com.jy.torder.model.OrderObj;
import com.jy.torder.util.BaseException;

public class OrderService extends BaseObjService<OrderObj> {

    public static final String COND_START_DELIVERYTIME = "COND_START_DELIVERYTIME";
    public static final String COND_END_DELIVERYTIME = "COND_END_DELIVERYTIME";
    public static final String COND_START_CREATETIME = "COND_START_CREATETIME";
    public static final String COND_END_CREATETIME = "COND_END_CREATETIME";

    CustomerService customerService;
    GTService gtService;

    public OrderService(ConnInfo connInfo) throws BaseException {
        super(connInfo);
        customerService = new CustomerService(connInfo);
        gtService = new GTService(connInfo);
    }

    public void add(final OrderObj orderObj) throws BaseException {
        try {
            this.begin();
            customerService.begin();

            if (null == orderObj.getCustomerId()) {
                customerService.add(orderObj.getCustomer());
            }
            orderObj.setCreateTime(new Timestamp(System.currentTimeMillis()));

            this.executeSQL(
                    "insert into t_order(c_custom_id, c_gt_id, c_delivery_time, c_status, c_desc, c_create_time) values (?,?,?,?,?,?)",
                    new String[] { "c_id" }, new DBUpdateProcessAdapter() {

                        public void addParameters(PreparedStatement ps) throws SQLException {
                            int i = 0;
                            ps.setLong(++i, orderObj.getCustomerId());
                            ps.setLong(++i, orderObj.getGTId());
                            try {
                                ps.setTimestamp(++i, orderObj.getDeliveryTimeDb());
                            } catch (ParseException e) {
                                throw new SQLException("非法的时间格式");
                            }
                            ps.setInt(++i, orderObj.getStatus().status);
                            ps.setString(++i, orderObj.getDesc());
                            ps.setTimestamp(++i, orderObj.getCreateTime());
                        }

                        public void processGeneratedKeys(ResultSet result) throws SQLException {
                            if (result.next()) {
                                orderObj.setId(result.getLong(1));
                            }
                        }

                    });
            customerService.commit();
            this.commit();
        } catch (BaseException ex) {
            customerService.rollback();
            this.rollback();
            throw ex;
        }
    }

    public List<OrderObj> findAll(OrderObj orderObj, final Map<String, Object> queryCondition, Integer pageNum, Integer pageSize)
            throws BaseException {
        final List<OrderObj> orderObjList = new ArrayList<OrderObj>();
        StringBuilder sql = new StringBuilder("select o.c_id, o.c_delivery_time, o.c_status, o.c_desc, o.c_create_time, "
                + " o.c_custom_id, c.c_name, c.c_mobile, c.c_addr, o.c_gt_id, g.c_number, g.c_passwd, g.c_spec from "
                + "t_order o, t_gift_ticket g, t_customer c where o.c_custom_id = c.c_id and o.c_gt_id = g.c_id ");
        if (null != orderObj.getId()) {
            sql.append(" and o.c_id = ").append(orderObj.getId());
        } else {

            if (null != queryCondition) {
                Timestamp startDeliveryTime = (Timestamp) queryCondition.get(COND_START_DELIVERYTIME);
                if (null != startDeliveryTime) {
                    sql.append(" and o.c_delivery_time >= ?");
                }

                Timestamp endDeliveryTime = (Timestamp) queryCondition.get(COND_END_DELIVERYTIME);
                if (null != endDeliveryTime) {
                    sql.append(" and o.c_delivery_time <= ?");
                }
                Timestamp startCreateTime = (Timestamp) queryCondition.get(COND_START_CREATETIME);
                if (null != startCreateTime) {
                    sql.append(" and o.c_create_time >= ?");
                }

                Timestamp endCreateTime = (Timestamp) queryCondition.get(COND_END_CREATETIME);
                if (null != endCreateTime) {
                    sql.append(" and o.c_create_time <= ?");
                }
            }

            if (null != orderObj.getStatus()) {
                sql.append(" and o.c_status = ").append(orderObj.getStatusNumber());
            }

            if (null != orderObj.getDesc() && !orderObj.getDesc().isEmpty()) {
                sql.append(" and o.c_desc like  '%").append(orderObj.getDesc().replaceAll(" ", "%")).append("%'");
            }

            if (null != orderObj.getNumber() && !orderObj.getNumber().isEmpty()) {
                sql.append(" and g.c_number = '").append(orderObj.getNumber()).append("'");
            }

            if (null != orderObj.getPasswd() && !orderObj.getPasswd().isEmpty()) {
                sql.append(" and g.c_passwd = '").append(orderObj.getPasswd()).append("'");
            }

            if (null != orderObj.getSpec() && !orderObj.getSpec().isEmpty()) {
                sql.append(" and g.c_spec like  '%").append(orderObj.getSpec().replaceAll(" ", "%")).append("%'");
            }

            if (null != orderObj.getCustomerId()) {
                sql.append(" and c.c_id = ").append(orderObj.getCustomerId());
            } else {

                if (null != orderObj.getName() && !orderObj.getName().isEmpty()) {
                    sql.append(" and c.c_name = '").append(orderObj.getName()).append("'");
                }

                if (null != orderObj.getMobile() && !orderObj.getMobile().isEmpty()) {
                    sql.append(" and c.c_mobile = ? '").append(orderObj.getMobile()).append("'");
                }

                if (null != orderObj.getAddr() && !orderObj.getAddr().isEmpty()) {
                    sql.append(" and c.c_addr like  '%").append(orderObj.getAddr().replaceAll(" ", "%")).append("%'");
                }
            }

        }

        if (null != pageNum && null != pageSize) {
            sql.append(" limit ").append(pageSize).append(" offset ").append((pageNum - 1) * pageSize);
        }

        this.executeSQL(sql.toString(), new DBQueryProcessAdapter() {

            public void resultProcess(ResultSet result) throws SQLException {
                while (result.next()) {
                    OrderObj tmpOrderObj = new OrderObj();
                    int i = 0;
                    tmpOrderObj.setId(result.getLong(++i));
                    tmpOrderObj.setDeliveryTimeDb(result.getTimestamp(++i));
                    tmpOrderObj.setStatusNumber(result.getInt(++i));
                    tmpOrderObj.setDesc(result.getString(++i));
                    tmpOrderObj.setCreateTime(result.getTimestamp(++i));
                    tmpOrderObj.setCustomerId(result.getLong(++i));
                    tmpOrderObj.setName(result.getString(++i));
                    tmpOrderObj.setMobile(result.getString(++i));
                    tmpOrderObj.setAddr(result.getString(++i));
                    tmpOrderObj.setGTId(result.getLong(++i));
                    tmpOrderObj.setNumber(result.getString(++i));
                    tmpOrderObj.setPasswd(result.getString(++i));
                    tmpOrderObj.setSpec(result.getString(++i));
                    orderObjList.add(tmpOrderObj);
                }
            }

            public void addParameters(PreparedStatement ps) throws SQLException {
                int i = 0;
                if (null != queryCondition) {
                    Timestamp startDeliveryTime = (Timestamp) queryCondition.get(COND_START_DELIVERYTIME);
                    if (null != startDeliveryTime) {
                        ps.setTimestamp(++i, startDeliveryTime);
                    }

                    Timestamp endDeliveryTime = (Timestamp) queryCondition.get(COND_END_DELIVERYTIME);
                    if (null != endDeliveryTime) {
                        ps.setTimestamp(++i, endDeliveryTime);
                    }
                    Timestamp startCreateTime = (Timestamp) queryCondition.get(COND_START_CREATETIME);
                    if (null != startCreateTime) {
                        ps.setTimestamp(++i, startCreateTime);
                    }

                    Timestamp endCreateTime = (Timestamp) queryCondition.get(COND_END_CREATETIME);
                    if (null != endCreateTime) {
                        ps.setTimestamp(++i, endCreateTime);
                    }
                }

            }
        });

        return orderObjList;

    }

    @Override
    public void delete(OrderObj obj) throws BaseException {
        if (null != obj.getId()) {
            StringBuilder sql = new StringBuilder("delete from t_order where c_id = ");
            sql.append(obj.getId());
            this.executeSQL(sql.toString());
        }
    }

    @Override
    public void modify(final OrderObj obj) throws BaseException {
        if (null != obj.getId()) {
            try {
                this.begin();
                customerService.begin();
                gtService.begin();

                gtService.modify(obj.getGiftTick());
                customerService.modify(obj.getCustomer());

                StringBuilder sql = new StringBuilder(
                        "update t_order set c_custom_id = ?, c_gt_id = ?, c_delivery_time = ?, c_status = ?, c_desc = ?  where c_id = ?");
                this.executeSQL(sql.toString(), new DBUpdateNoKeyProcessAdapter() {

                    public void addParameters(PreparedStatement ps) throws SQLException {
                        int i = 0;
                        ps.setLong(++i, obj.getCustomerId());
                        ps.setLong(++i, obj.getGTId());
                        try {
                            ps.setTimestamp(++i, obj.getDeliveryTimeDb());
                        } catch (ParseException e) {
                            throw new SQLException("不合法的时间格式", e);
                        }
                        ps.setLong(++i, obj.getStatusNumber());
                        ps.setString(++i, obj.getDesc());
                        ps.setLong(++i, obj.getId());
                    }

                });

                gtService.commit();
                customerService.commit();
                this.commit();
            } catch (BaseException ex) {
                gtService.rollback();
                customerService.rollback();
                this.rollback();
                throw ex;
            }
        }

    }
}
