package com.jy.torder.model;

//CREATE TABLE `torder`.`t_gift_ticket` (
//        `c_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
//        `c_number` VARCHAR(300) NOT NULL,
//        `c_passwd` VARCHAR(300) NOT NULL,
//        `c_spec` VARCHAR(1024),
//        PRIMARY KEY (`c_id`),
//        INDEX `idx_gt_number`(`c_number`)
//      )
//      ENGINE = InnoDB;
public class GiftTicketObj {
    private Long id;
    private String number;
    private String passwd;
    private String spec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
    
}
