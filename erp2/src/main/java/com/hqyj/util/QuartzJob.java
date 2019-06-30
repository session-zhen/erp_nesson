package com.hqyj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hqyj.service.StoreAlertService;

@Component
@EnableScheduling
public class QuartzJob {
	
	@Autowired
	private StoreAlertService storeAlertService;

	@Scheduled(cron="0 * 12 * * ?")
	public void goSendMail() {
		storeAlertService.sendStoreAlertMail();
	}
}
