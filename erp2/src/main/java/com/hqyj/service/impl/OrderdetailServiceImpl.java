package com.hqyj.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqyj.exception.ErpException;
import com.hqyj.mapper.OrderdetailMapper;
import com.hqyj.mapper.OrdersMapper;
import com.hqyj.mapper.StoredetailMapper;
import com.hqyj.mapper.StoreoperMapper;
import com.hqyj.pojo.Orderdetail;
import com.hqyj.pojo.OrderdetailExample;
import com.hqyj.pojo.Orders;
import com.hqyj.pojo.Storedetail;
import com.hqyj.pojo.StoredetailExample;
import com.hqyj.pojo.StoredetailExample.Criteria;
import com.hqyj.pojo.Storeoper;
import com.hqyj.service.OrderdetailService;

@Service
@Transactional
public class OrderdetailServiceImpl implements OrderdetailService {

	@Autowired
	private StoredetailMapper storedetailMapper;
	
	@Autowired
	private StoreoperMapper storeoperMapper;
	
	@Autowired
	private OrderdetailMapper orderdetailMapper;
	
	@Autowired
	private OrdersMapper ordersMapper;

	@Override
	public void doInStore(Integer id, Integer storeid, Integer empid) {
		
		//更新商品明细
		Orderdetail orderdetail = orderdetailMapper.selectByPrimaryKey(id);
		//确认订单明细是否入库
		if(!Orderdetail.STATE_ONT_IN.equals(orderdetail.getState())) {
			throw new ErpException("该订单明细已经入库！");
		}
		//设置订单明细已入库
		orderdetail.setState(Orderdetail.STATE_IN);
		//设置仓库操作员
		orderdetail.setEnder(empid);
		//设置仓库编号
		orderdetail.setStoreid(storeid);
		//入库时间
		orderdetail.setEndtime(new Date());
		//更新商品明细
		orderdetailMapper.updateByPrimaryKey(orderdetail);
		
		//------------------------------------------------------------------------------------------//
		
		//更新库存表
		Storedetail storedetail = new Storedetail();
		//设置仓库号
		storedetail.setStoreid(storeid);
		//设置商品id
		storedetail.setGoodsid(orderdetail.getGoodsid());
		//设置商品数量
		storedetail.setNum(orderdetail.getNum());
		
		StoredetailExample storedetailExample = new StoredetailExample();
		Criteria storedetailCriteria = storedetailExample.createCriteria();
		//根据商品id查询库存记录
		storedetailCriteria.andGoodsidEqualTo(storedetail.getGoodsid());
		storedetailCriteria.andStoreidEqualTo(storeid);
		List<Storedetail> storedetailList = storedetailMapper.selectByExample(storedetailExample);
		//如果库存有商品，则更新数量
		if(storedetailList.size()>0) {
			Storedetail sd = storedetailList.get(0);
			sd.setNum(storedetail.getNum() + sd.getNum());
			//更新数据库
			storedetailMapper.updateByPrimaryKey(sd);
		} else {//如果库存没有商品，则添加商品
			storedetailMapper.insert(storedetail);
		}
		
		//------------------------------------------------------------------------------------------//
		
		//更新库存变动记录表
		Storeoper storeoper = new Storeoper();		
		//设置仓库操作员
		storeoper.setEmpid(empid);
		storeoper.setOpertime(orderdetail.getEndtime());
		storeoper.setStoreid(storeid);
		storeoper.setGoodsid(orderdetail.getGoodsid());
		storeoper.setNum(orderdetail.getNum());
		storeoper.setType(Storeoper.TYPE_IN);
		
		storeoperMapper.insert(storeoper);
		
		//------------------------------------------------------------------------------------------//
		//更新订单状态
		Orders orders = orderdetail.getOrders();
		
		OrderdetailExample orderdetailExample = new OrderdetailExample();
		com.hqyj.pojo.OrderdetailExample.Criteria orderdetailCriteria = orderdetailExample.createCriteria();
		
		orderdetailCriteria.andStateEqualTo(Orderdetail.STATE_ONT_IN);
		orderdetailCriteria.andOrdersidEqualTo(orders.getId());
		
		List<Orderdetail> orderdetailList = orderdetailMapper.selectByExample(orderdetailExample);
		
		//如果订单对应的订单明细都已入库
		if(orderdetailList.size() == 0) {
			orders.setEnder(empid);
			orders.setState(Orders.STATE_END);
			orders.setEndtime(orderdetail.getEndtime());
			
			//更新数据库
			ordersMapper.updateByPrimaryKey(orders);
		}
		
		
	}

	@Override
	public void doOutStore(Integer id, Integer storeid, Integer empid) {
		//获取订单明细
		Orderdetail orderdetail = orderdetailMapper.selectByPrimaryKey(id);
		if(!"0".equals(orderdetail.getState())){
	        throw new ErpException("亲！该明细已经出库了，不能重复出库哦!");
	    }
		orderdetail.setEnder(empid);
		orderdetail.setEndtime(new Date());
		orderdetail.setState("1");
		orderdetail.setStoreid(storeid);
		//更新商品明细
		orderdetailMapper.updateByPrimaryKey(orderdetail);
		
		//------------------------------------------------------------------------------------------//
		//查询库存
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsid(orderdetail.getGoodsid());
		storedetail.setStoreid(storeid);
		
		StoredetailExample storedetailExample = new StoredetailExample();
		Criteria storedetailCriteria = storedetailExample.createCriteria();
		//根据商品id查询库存记录
		storedetailCriteria.andGoodsidEqualTo(storedetail.getGoodsid());
		storedetailCriteria.andStoreidEqualTo(storeid);
		List<Storedetail> storedetailList = storedetailMapper.selectByExample(storedetailExample);
		
		Integer num = 0;
		//如果库存有商品，则更新数量
		if(storedetailList.size()>0) {
			storedetail = storedetailList.get(0);
			num = storedetail.getNum() - orderdetail.getNum();
		}
		//库存充足
		if(num>0) {
			storedetail.setNum(num);
			storedetailMapper.updateByPrimaryKey(storedetail);
		} else {//库存不足
			throw new ErpException("库存不足!");
		}
		
		//------------------------------------------------------------------------------------------//
		//更新库存变动记录表
		Storeoper storeoper = new Storeoper();		
		//设置仓库操作员
		storeoper.setEmpid(empid);
		storeoper.setOpertime(orderdetail.getEndtime());
		storeoper.setStoreid(storeid);
		storeoper.setGoodsid(orderdetail.getGoodsid());
		storeoper.setNum(orderdetail.getNum());
		storeoper.setType("2");
		
		storeoperMapper.insert(storeoper);
		
		//------------------------------------------------------------------------------------------//
		//检查是否订单下的所有明细都已经出库
		//更新订单状态
		Orders orders = orderdetail.getOrders();
		
		OrderdetailExample orderdetailExample = new OrderdetailExample();
		com.hqyj.pojo.OrderdetailExample.Criteria orderdetailCriteria = orderdetailExample.createCriteria();
		
		orderdetailCriteria.andStateEqualTo("0");
		orderdetailCriteria.andOrdersidEqualTo(orders.getId());
		
		List<Orderdetail> orderdetailList = orderdetailMapper.selectByExample(orderdetailExample);
		
		//如果订单对应的订单明细都已入库
		if(orderdetailList.size() == 0) {
			orders.setEnder(empid);
			orders.setState("1");
			orders.setEndtime(orderdetail.getEndtime());
			
			//更新数据库
			ordersMapper.updateByPrimaryKey(orders);
		}
	}
}
