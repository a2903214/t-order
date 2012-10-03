package com.jy.torder.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//CREATE TABLE `torder`.`t_order` (
//        `c_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
//        `c_custom_id` INTEGER UNSIGNED NOT NULL,
//        `c_gt_id` INTEGER UNSIGNED NOT NULL,
//        `c_delivery_time` DATETIME,
//        `c_status` INTEGER UNSIGNED NOT NULL,
//        `c_desc` VARCHAR(1024),
//        PRIMARY KEY (`c_id`),
//        INDEX `idx_order_cid`(`c_custom_id`),
//        INDEX `idx_order_gt_id`(`c_gt_id`),
//        INDEX `idx_order_dtime`(`c_delivery_time`)
//      )
//      ENGINE = InnoDB;
public class OrderObj {
    public class OrderStatus {
        public int status;
        public String statusDesc;
        public static final int NOT_DELIVER_STATUS = 0;
        public static final int HAVE_DELIVER_STATUS = 1;

        public OrderStatus(int status) {
            this.status = status;
            String statusDesc = "未发货";
            switch (status) {
            case NOT_DELIVER_STATUS:
                statusDesc = "未发货";
                break;
            case HAVE_DELIVER_STATUS:
                statusDesc = "已发货";
                break;
            }
            this.statusDesc = statusDesc;
        }

        public String toString() {
            return statusDesc;
        }
    };

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");

    public static final OrderStatus NOT_DELIVER_STATUS = new OrderObj().new OrderStatus(OrderStatus.NOT_DELIVER_STATUS);
    public static final OrderStatus HAVE_DELIVER_STATUS = new OrderObj().new OrderStatus(OrderStatus.HAVE_DELIVER_STATUS);

    private GiftTicketObj giftTick = new GiftTicketObj();
    private CustomerObj customer = new CustomerObj();

    private Long id;
    private String deliveryTime;
    private Timestamp createTime;
    private OrderStatus status;
    private String desc;

    public Long getGTId() {
        return giftTick.getId();
    }

    public void setGTId(Long id) {
        giftTick.setId(id);
    }

    public String getNumber() {
        return giftTick.getNumber();
    }

    public void setNumber(String number) {
        giftTick.setNumber(number);
    }

    public String getPasswd() {
        return giftTick.getPasswd();
    }

    public void setPasswd(String passwd) {
        giftTick.setPasswd(passwd);
    }

    public String getSpec() {
        return giftTick.getSpec();
    }

    public void setSpec(String spec) {
        giftTick.setSpec(spec);
    }

    public Long getCustomerId() {
        return customer.getId();
    }

    public void setCustomerId(Long id) {
        customer.setId(id);
    }

    public String getName() {
        return customer.getName();
    }

    public void setName(String name) {
        customer.setName(name);
    }

    public String getMobile() {
        return customer.getMobile();
    }

    public void setMobile(String mobile) {
        customer.setMobile(mobile);
    }

    public String getAddr() {
        return customer.getAddr();
    }

    public void setAddr(String addr) {
        customer.setAddr(addr);
    }

    public GiftTicketObj getGiftTick() {
        return giftTick;
    }

    public void setGiftTick(GiftTicketObj giftTick) {
        this.giftTick = giftTick;
    }

    public CustomerObj getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerObj customer) {
        this.customer = customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getStatusNumber() {
        return status.status;
    }

    public void setStatusNumber(int statusNumber) {
        switch (statusNumber) {
        case OrderStatus.NOT_DELIVER_STATUS:
            status = NOT_DELIVER_STATUS;
            break;
        case OrderStatus.HAVE_DELIVER_STATUS:
            status = HAVE_DELIVER_STATUS;
            break;
        }
    }

    public String getStatusDesc() {

        return status.statusDesc;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryTimeDb(Timestamp deliveryTimeDb) {
        deliveryTime = DATE_FORMAT.format(new Date(deliveryTimeDb.getTime()));
    }

    public Timestamp getDeliveryTimeDb() throws ParseException {
        return new Timestamp(DATE_FORMAT.parse(this.deliveryTime).getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}
