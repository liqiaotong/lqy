package com.example.system.oms.greendao.gen.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

@Entity(nameInDb = "order")
public class Order{
    @Id(autoincrement = true)
    private Long id;
    private String orderName;
    private String number;
    private long price ;
    private long totalPrice;
    private String customer;
    private Long customPhone;
    private String dataTime;
    @Keep
    public Order(Long id, String orderName, String number, long price, long totalPrice, String customer, Long customPhone, String dataTime) {
        this.id = id;
        this.orderName = orderName;
        this.number = number;
        this.price = price;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.customPhone = customPhone;
        this.dataTime = dataTime;
    }
    @Keep
    public Order() {
    }

    public Order(String name, String number, String price, String totalPrice, String customer, String customerPhone, String dataTime) {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderName() {
        return this.orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Long getPrice() {
        return this.price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public Long getTotalPrice() {
        return this.totalPrice;
    }
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getCustomer() {
        return this.customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public Long getCustomPhone() {
        return this.customPhone;
    }
    public void setCustomPhone(Long customPhone) {
        this.customPhone = customPhone;
    }
    public String getDateTime() {
        return this.dataTime;
    }
    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    public void setPrice(long price) {
        this.price = price;
    }
    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getDataTime() {
        return this.dataTime;
    }


}


