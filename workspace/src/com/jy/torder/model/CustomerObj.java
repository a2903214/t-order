package com.jy.torder.model;

//CREATE TABLE `torder`.`t_customer` (
//        `c_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
//        `c_name` VARCHAR(45) NOT NULL,
//        `c_mobile` VARCHAR(45) NOT NULL,
//        `c_addr` VARCHAR(1024) NOT NULL,
//        PRIMARY KEY (`c_id`),
//        INDEX `idx_cm_name`(`c_name`),
//        INDEX `idx_cm_mobile`(`c_mobile`)
//      )
//      ENGINE = InnoDB;
public class CustomerObj {
    private Long id;
    private String name;
    private String mobile;
    private String addr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    
}
