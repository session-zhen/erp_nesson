package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Storeoper;
import com.hqyj.util.EasyUIDatagrid;

public interface StoreoperService {

	public List<Storeoper> getAll();
	
	public void addOrEditStoreoper(Storeoper storeoper);
	
	public EasyUIDatagrid getByPage(Integer page,Integer rows,Storeoper storeoper);
	
	public void deleteStoreopers(List<Integer> list);
}
