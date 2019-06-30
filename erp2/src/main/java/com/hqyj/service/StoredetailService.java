package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Storedetail;
import com.hqyj.util.EasyUIDatagrid;

public interface StoredetailService {

	public List<Storedetail> getAll();

	public void addOrEditStoredetail(Storedetail storedetail);

	public EasyUIDatagrid getByPage(Integer page, Integer rows, Storedetail storedetail);

	public void deleteStoredetails(List<Integer> list);
}
