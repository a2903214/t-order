package com.jy.torder.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jy.torder.db.ConnInfo;
import com.jy.torder.model.GiftTicketObj;
import com.jy.torder.util.BaseException;

public class GTService extends BaseObjService<GiftTicketObj> {

    public GTService(ConnInfo connInfo) throws BaseException {
        super(connInfo);
    }

    public void add(final GiftTicketObj gtObj) throws BaseException {
        try {
            this.begin();
            this.executeSQL("insert into t_gift_ticket(c_number, c_passwd, c_spec) values (?,?,?)", new String[] { "c_id" },
                    new DBUpdateProcessAdapter() {

                        public void addParameters(PreparedStatement ps) throws SQLException {
                            int i = 0;
                            ps.setString(++i, gtObj.getNumber());
                            ps.setString(++i, gtObj.getPasswd());
                            ps.setString(++i, gtObj.getSpec());

                        }

                        public void processGeneratedKeys(ResultSet result) throws SQLException {
                            if (result.next()) {
                                gtObj.setId(result.getLong(1));
                            }
                        }

                    });
            this.commit();
        } catch (BaseException ex) {
            this.rollback();
            throw ex;
        }

    }

    public List<GiftTicketObj> findAll(GiftTicketObj gtObj, Map<String, Object> condition, Integer pageNum, Integer pageSize)
            throws BaseException {
        final List<GiftTicketObj> gtObjList = new ArrayList<GiftTicketObj>();
        StringBuilder sql = new StringBuilder("select c_id, c_number, c_passwd, c_spec from t_gift_ticket where 1=1 ");
        if (null != gtObj.getId()) {
            sql.append(" and c_id = ").append(gtObj.getId());
        }

        if (null != gtObj.getNumber() && !gtObj.getNumber().isEmpty()) {
            sql.append(" and c_number = '").append(gtObj.getNumber()).append("'");
        }

        if (null != gtObj.getPasswd() && !gtObj.getPasswd().isEmpty()) {
            sql.append(" and c_passwd = '").append(gtObj.getPasswd()).append("'");
        }

        if (null != gtObj.getSpec() && !gtObj.getSpec().isEmpty()) {
            sql.append(" and c_spec like  '%").append(gtObj.getSpec().replaceAll(" ", "%")).append("%'");
        }

        if (null != pageNum && null != pageSize) {
            sql.append(" limit ").append(pageSize).append(" offset ").append((pageNum - 1) * pageSize);
        }

        this.executeSQL(sql.toString(), new DBQueryProcessNoParamAdapter() {

            public void resultProcess(ResultSet result) throws SQLException {
                while (result.next()) {
                    GiftTicketObj tmpGTObj = new GiftTicketObj();
                    int i = 0;
                    tmpGTObj.setId(result.getLong(++i));
                    tmpGTObj.setNumber(result.getString(++i));
                    tmpGTObj.setPasswd(result.getString(++i));
                    tmpGTObj.setSpec(result.getString(++i));
                    gtObjList.add(tmpGTObj);
                }
            }
        });

        return gtObjList;

    }

    @Override
    public void delete(GiftTicketObj obj) throws BaseException {
        if (null != obj.getId()) {
            StringBuilder sql = new StringBuilder("delete from t_gift_ticket where c_id = ");
            sql.append(obj.getId());
            this.executeSQL(sql.toString());
        }
    }

    @Override
    public void modify(final GiftTicketObj obj) throws BaseException {
        if (null != obj.getId()) {
            StringBuilder sql = new StringBuilder("update t_gift_ticket set c_number = ?, c_passwd = ?, c_spec = ? where c_id = ?");
            this.executeSQL(sql.toString(), new DBUpdateNoKeyProcessAdapter() {

                public void addParameters(PreparedStatement ps) throws SQLException {
                    int i = 0;
                    ps.setString(++i, obj.getNumber());
                    ps.setString(++i, obj.getPasswd());
                    ps.setString(++i, obj.getSpec());
                    ps.setLong(++i, obj.getId());
                }

            });
        }
    }

}
