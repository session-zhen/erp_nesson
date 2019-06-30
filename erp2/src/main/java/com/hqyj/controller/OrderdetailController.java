package com.hqyj.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqyj.pojo.Emp;
import com.hqyj.service.OrderdetailService;

@RestController
public class OrderdetailController {

	@Autowired
	private OrderdetailService orderdetailService;
	
	@RequestMapping("/orderdetail_doInStore")
	public Map<String,Object> orderdetail_doInStore(Integer id, Integer storeid,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		
		try {
			Emp emp = (Emp) request.getSession().getAttribute("loginUser");
			Integer empid = emp.getId();
			orderdetailService.doInStore(id, storeid, empid);
			
			map.put("success", true);
			map.put("msg", "入库成功！");
		} catch(Exception e) {
			map.put("success", false);
			map.put("msg", "入库失败！" + e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
	
	@RequestMapping("/orderdetail_doOutStore")
	public Map<String,Object> orderdetail_doOutStore(Integer id, Integer storeid,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		
		try {
			Emp emp = (Emp) request.getSession().getAttribute("loginUser");
			Integer empid = emp.getId();
			orderdetailService.doOutStore(id, storeid, empid);
			
			map.put("success", true);
			map.put("msg", "出库成功！");
		} catch(Exception e) {
			map.put("success", false);
			map.put("msg", "出库失败！" + e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
}
