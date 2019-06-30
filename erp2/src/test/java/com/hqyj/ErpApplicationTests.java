package com.hqyj;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.controller.ExportExcelController;
import com.hqyj.mapper.DepMapper;
import com.hqyj.mapper.MenuMapper;
import com.hqyj.mapper.ReportMapper;
import com.hqyj.pojo.Dep;
import com.hqyj.pojo.DepExample;
import com.hqyj.pojo.Menu;
import com.hqyj.pojo.Report;
import com.hqyj.util.RedisUtil;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErpApplicationTests {
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private ReportMapper reportMapper;
	
	@Test
	public void testReportMapper() throws Exception{
		Report report = new Report();
		report.setEndTime("2018-09-26");
		
		List<Report> list = reportMapper.getReportList(report);
		
		System.out.println(list);
	}
	
	@Test
	public void contextLoads() {
		Menu menu = menuMapper.selectByPrimaryKey("0");
		
		System.out.println(menu);
	}
	@Autowired
	private JavaMailSender javaMailSender;//自动注入
	
	@Value("${spring.mail.username}")
	private String srcMail;
	
	@Test
	public void testSendMail() {
		SimpleMailMessage smm = new SimpleMailMessage();
		//发件人
		smm.setFrom(srcMail);
		//收件人
		smm.setTo("2965277177@qq.com");
		//邮件标题
		smm.setSubject("测试邮件");
		//邮件类容
		smm.setText("SpringBoot的测试邮件！");
		
		System.out.println(smm);
		//发送
		javaMailSender.send(smm);
	}
	@Test
	public void redisTest1() {
		Jedis jedis=new Jedis("127.0.0.1", 6379);
		jedis.set("名字", "hhh");
		jedis.close();
	}
	@Test
	public void redisTest2() {
		Jedis jedis=new Jedis("127.0.0.1", 6379);
		jedis.get("名字");
		jedis.close();
	}
	@Autowired
	private RedisUtil redisutil;
	@Autowired
	private DepMapper depMapper;
	@Test
	public void spring_redisTest() throws Exception {
		/*if(!redisutil.hasCache("depall")) {
			DepExample depExample=new DepExample();
			List<Dep> list=depMapper.selectByExample(depExample);
			ObjectMapper om=new ObjectMapper();
			String depstr=om.writeValueAsString(list);
			redisutil.setCache("depall", depstr);
		}*/
		if(redisutil.hasCache("depall")) {
			redisutil.removeCache("depall");
		}
	}
}
