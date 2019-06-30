package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqyj.pojo.StoreAlert;
import com.hqyj.service.StoreAlertService;

@RestController
public class StoreAlertController {
	@Autowired
	private StoreAlertService storeAlertService;
	@RequestMapping("/getStoreAlert_all")
	public List<StoreAlert> getStoreAlert_all(){
		return storeAlertService.getStoreAlerts();
	}
	@RequestMapping("/storealert_sendMail")
	public Map<String,Object> storealert_sendMail() {
		Map<String,Object> map = new HashMap<>();		
		try {
			storeAlertService.sendStoreAlertMail();
			map.put("success", true);
			map.put("msg", "发送预警邮件成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "发送预警邮件失败！");
			e.printStackTrace();
		}	
		return map;
	}
}
