package com.hqyj.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqyj.exception.ErpException;
import com.hqyj.mapper.StoreAlertMapper;
import com.hqyj.pojo.StoreAlert;
import com.hqyj.service.StoreAlertService;
@Service
@Transactional
public class StoreAlertServiceImpl implements StoreAlertService {
	@Autowired
	private StoreAlertMapper storeAlertMapper;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String from;

	@Override
	public List<StoreAlert> getStoreAlerts() {
		// TODO Auto-generated method stub
		return storeAlertMapper.getStoreAlerts();
	}
	@Override
	public void sendStoreAlertMail() {
		// TODO Auto-generated method stub
		List<StoreAlert> list = storeAlertMapper.getStoreAlerts();
		
		if(list.size()>0) {
			
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//简单发送邮件
			SimpleMailMessage smm = new SimpleMailMessage();
			smm.setFrom(from);
			smm.setTo("2965277177@qq.com");
			smm.setSubject("库存预警时间：" + time);//标题
			smm.setText("有" + list.size() + "种商品已经库存不足，请登录ERP系统查看！");//邮件内容
			javaMailSender.send(smm);//发送
		} else {
			throw new ErpException("没有需要预警的商品！");
		}
	}

}
