package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Emp;
import com.hqyj.pojo.Orderdetail;
import com.hqyj.pojo.Orders;
import com.hqyj.service.OrdersService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	// 确认订单
	@RequestMapping("/orders_doStart")
	public Map<String, Object> orders_doStart(Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		try {
			Emp emp = (Emp) request.getSession().getAttribute("loginUser");
			ordersService.doStart(id, emp.getId());
			map.put("success", true);
			map.put("msg", "订单确认成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	// 审核订单
	@RequestMapping("/orders_doCheck")
	public Map<String, Object> orders_doCheck(Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		try {
			Emp emp = (Emp) request.getSession().getAttribute("loginUser");
			ordersService.doCheck(id, emp.getId());
			map.put("success", true);
			map.put("msg", "订单审核成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	// 分页查询
	@RequestMapping("/orders_page")
	public EasyUIDatagrid orders_page(Integer page, Integer rows, Orders orders) {
		return ordersService.getByPage(page, rows, orders);
	}

	// 查询自己的订单
	@RequestMapping("/orders_myPage")
	public EasyUIDatagrid orders_myPage(Integer page, Integer rows, Orders orders, HttpServletRequest request) {
		Emp emp = (Emp) request.getSession().getAttribute("loginUser");
		orders.setCreater(emp.getId());
		return ordersService.getByPage(page, rows, orders);
	}

	@RequestMapping("/orders_save")
	public Map<String, Object> orders_save(Integer supplierid, String type, String json, HttpServletRequest request)
			throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();

		List<Orderdetail> orderdetails = mapper.readValue(json, new TypeReference<List<Orderdetail>>() {
		});

		Orders orders = new Orders();
		Emp emp = (Emp) request.getSession().getAttribute("loginUser");
		// 设置创建人
		orders.setCreater(emp.getId());
		orders.setSupplierid(supplierid);
		orders.setType(type);
		orders.setOrderdetails(orderdetails);

		try {
			ordersService.addOrders(orders);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}
}
