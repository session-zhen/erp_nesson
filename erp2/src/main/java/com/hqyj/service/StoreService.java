package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Store;
import com.hqyj.util.EasyUIDatagrid;

public interface StoreService {

	public List<Store> getAll(Integer empid);
	
	public void addOrEditStore(Store store);
	
	public EasyUIDatagrid getByPage(Integer page,Integer rows,Store store);
	
	public void deleteStores(List<Integer> list);
}
