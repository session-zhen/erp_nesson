package com.hqyj.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Orderdetail {
	
	public final static String STATE_ONT_IN = "0";//采购、未入库
	public final static String STATE_IN = "1";//采购、已入库
	
	public final static String STATE_NOT_OUT = "0";//销售、未出库
	public final static String STATE_OUT = "1";//销售、已出库
	
    private Integer id;

    private Integer goodsid;

    private String goodsname;

    private Double price;

    private Integer num;

    private Double money;

    private Date endtime;

    private Integer ender;

    private Integer storeid;

    private String state;

    private Integer ordersid;
    
    @JsonIgnore(value=true)
    private Orders orders;

    public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getEnder() {
        return ender;
    }

    public void setEnder(Integer ender) {
        this.ender = ender;
    }

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getOrdersid() {
        return ordersid;
    }

    public void setOrdersid(Integer ordersid) {
        this.ordersid = ordersid;
    }
}