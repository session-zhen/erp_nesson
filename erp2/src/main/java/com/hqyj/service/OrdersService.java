package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Orders;
import com.hqyj.util.EasyUIDatagrid;

public interface OrdersService {

	public List<Orders> getAll();

	public void addOrders(Orders orders);

	public EasyUIDatagrid getByPage(Integer page, Integer rows, Orders dep);

	public void deleteOrderss(List<Integer> list);
	
	//审核订单
	public void doCheck(Integer id,Integer empid);
	
	//确认订单
	public void doStart(Integer id,Integer empid);
}
