package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.StoreAlert;

public interface StoreAlertService {
	public List<StoreAlert> getStoreAlerts();
	public void sendStoreAlertMail();
}
