package com.hqyj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Report {

	private String name;
	private Double total;
	
	@JsonIgnore(value=true)
    private String startTime;//起始时间
    
	@JsonIgnore(value=true)
    private String endTime;//终止时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
