package com.hqyj.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Orders {
	
	public final static String STATE_CREATE = "0";//采购、未审核
	public final static String STATE_CHECK = "1";//采购、已审核
	public final static String STATE_START = "2";//采购、已确认
	public final static String STATE_END = "3";//采购、已入库
	
	public final static String STATE_NOT_OUT = "0";//销售、未出库
	public final static String STATE_OUT = "1";//销售、已出库
	
	public final static String TYPE_IN = "1";//采购订单
	public final static String TYPE_OUT = "2";//销售订单
	
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createtime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date checktime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date starttime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endtime;

    private String type;

    private Integer creater;
    private String createrName;//下单员

    private Integer checker;
    private String checkerName;//审核员

    private Integer starter;
    private String starterName;//采购员

    private Integer ender;
    private String enderName;//库管员

    private Integer supplierid;
    private String supplierName;//供应商

    private Double totalmoney;

    private String state;

    private Integer waybillsn;
    
    private List<Orderdetail> orderdetails;

	public List<Orderdetail> getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(List<Orderdetail> orderdetails) {
		this.orderdetails = orderdetails;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    public Integer getStarter() {
        return starter;
    }

    public void setStarter(Integer starter) {
        this.starter = starter;
    }

    public Integer getEnder() {
        return ender;
    }

    public void setEnder(Integer ender) {
        this.ender = ender;
    }

    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }

    public Double getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(Double totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getWaybillsn() {
        return waybillsn;
    }

    public void setWaybillsn(Integer waybillsn) {
        this.waybillsn = waybillsn;
    }

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public String getStarterName() {
		return starterName;
	}

	public void setStarterName(String starterName) {
		this.starterName = starterName;
	}

	public String getEnderName() {
		return enderName;
	}

	public void setEnderName(String enderName) {
		this.enderName = enderName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}