package com.hqyj.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.exception.ErpException;
import com.hqyj.mapper.EmpMapper;
import com.hqyj.mapper.OrderdetailMapper;
import com.hqyj.mapper.OrdersMapper;
import com.hqyj.mapper.SupplierMapper;
import com.hqyj.pojo.Orderdetail;
import com.hqyj.pojo.Orders;
import com.hqyj.pojo.OrdersExample;
import com.hqyj.pojo.OrdersExample.Criteria;
import com.hqyj.service.OrdersService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper ordersMapper;
	
	@Autowired
	private OrderdetailMapper orderdetailMapper;
	
	@Autowired
	private EmpMapper empMapper;
	
	@Autowired
	private SupplierMapper supplierMapper;

	@Override
	public List<Orders> getAll() {
		
		OrdersExample example = new OrdersExample();
		
		List<Orders> list = ordersMapper.selectByExample(example);
		
		return list;
	}

	//获取员工号姓名
	public String getEmpName(Integer id) {
		return empMapper.selectByPrimaryKey(id).getName();
	}
	
	//获取供应商姓名
	public String getSupplierName(Integer id) {
		return supplierMapper.selectByPrimaryKey(id).getName();
	}
	

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Orders orders) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		OrdersExample example = new OrdersExample();
		Criteria criteria = example.createCriteria();
		
		String type = orders.getType();
		String state = orders.getState();
		Integer empid = orders.getCreater();
		
		//查询当前用户发出的订单
		if(empid != null) {
			criteria.andCreaterEqualTo(empid);
		}
		
		//按订单类型条件查询
		if(!StringUtils.isEmpty(type)) {
			criteria.andTypeEqualTo(type);
		}
		//按订单状态条件查询
		if(!StringUtils.isEmpty(state)) {
			criteria.andStateEqualTo(state);
		}
		
		PageHelper.startPage(page, rows);
		
		List<Orders> list = ordersMapper.selectByExample(example);
		
		for(Orders o:list) {
			
			Integer creater = o.getCreater();
			Integer checker = o.getChecker();
			Integer starter = o.getStarter();
			Integer ender = o.getEnder();
			Integer supplierid = o.getSupplierid();
			
			if(creater!=null) {
				o.setCreaterName(getEmpName(creater));
			}
			if(checker!=null) {
				o.setCheckerName(getEmpName(checker));
			}
			if(starter!=null) {
				o.setStarterName(getEmpName(starter));
			}
			if(ender!=null) {
				o.setEnderName(getEmpName(ender));
			}
			if(supplierid!=null) {
				o.setSupplierName(getEmpName(supplierid));
			}
		}
		
		PageInfo<Orders> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	@Override
	public void deleteOrderss(List<Integer> list) {	
		for(Integer id:list) {
			ordersMapper.deleteByPrimaryKey(id);
		}
		
	}

	@Override
	public void addOrders(Orders orders) {
		//当前时间
		orders.setCreatetime(new Date());
		//采购订单
		//orders.setType(Orders.TYPE_IN);
		//未审核
		orders.setState(Orders.STATE_CREATE);
		//总金额
		double totalmoney = 0;
		
		List<Orderdetail> orderdetails = orders.getOrderdetails();
		
		for(Orderdetail od : orderdetails) {
			//设置状态，未入库
			od.setState(Orderdetail.STATE_ONT_IN);
			totalmoney += od.getMoney();
		}
		//设置总金额
		orders.setTotalmoney(totalmoney);
		
		//插入订单Orders
		ordersMapper.insert(orders);
		
		//设置订单关联订单详情的id
		//插入订单详情
		for(Orderdetail od : orderdetails) {
			od.setOrdersid(orders.getId());
			orderdetailMapper.insert(od);
		}
		
	}

	@Override
	public void doCheck(Integer id, Integer empid) {
		Orders orders = ordersMapper.selectByPrimaryKey(id);
		
		if(!Orders.STATE_CREATE.equals(orders.getState())) {
			throw new ErpException("该订单已审核过！");
		}
		//审核人
		orders.setChecker(empid);
		//审核时间
		orders.setChecktime(new Date());
		//已审核
		orders.setState(Orders.STATE_CHECK);
		//更新数据库
		ordersMapper.updateByPrimaryKeySelective(orders);
	}

	@Override
	public void doStart(Integer id, Integer empid) {
		Orders orders = ordersMapper.selectByPrimaryKey(id);
		
		if(!Orders.STATE_CHECK.equals(orders.getState())) {
			throw new ErpException("该订单已确认过！");
		}
		//确认人
		orders.setStarter(empid);
		//确认时间
		orders.setStarttime(new Date());
		//已确认
		orders.setState(Orders.STATE_START);
		//更新数据库
		ordersMapper.updateByPrimaryKeySelective(orders);
	}

}
