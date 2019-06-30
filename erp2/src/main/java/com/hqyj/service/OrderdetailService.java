package com.hqyj.service;

public interface OrderdetailService {

	/**
	 * 采购入库
	 * @param id 订单明细id
	 * @param storeid 仓库id
	 * @param empid 库管员id
	 */
	public void doInStore(Integer id,Integer storeid,Integer empid);
	
	/**
	 * 销售出库操作
	 */
	public void doOutStore(Integer id,Integer storeid,Integer empid);
}
