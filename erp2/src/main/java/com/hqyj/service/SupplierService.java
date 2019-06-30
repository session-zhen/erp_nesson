package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Supplier;
import com.hqyj.util.EasyUIDatagrid;

public interface SupplierService {

	public List<Supplier> getAll(String type);

	public void addOrEditSupplier(Supplier supplier);

	public EasyUIDatagrid getByPage(Integer page, Integer rows, Supplier supplier);

	public void deleteSuppliers(List<Integer> list);
}
