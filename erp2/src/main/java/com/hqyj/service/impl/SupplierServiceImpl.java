package com.hqyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.mapper.SupplierMapper;
import com.hqyj.pojo.Supplier;
import com.hqyj.pojo.SupplierExample;
import com.hqyj.pojo.SupplierExample.Criteria;
import com.hqyj.service.SupplierService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierMapper supplierMapper;

	@Override
	public List<Supplier> getAll(String type) {
		
		SupplierExample example = new SupplierExample();
		
		Criteria criteria = example.createCriteria();
		
		if(type!=null) {
			criteria.andTypeEqualTo(type);
		}
		
		
		List<Supplier> list = supplierMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public void addOrEditSupplier(Supplier supplier) {
		
		if(supplier.getId()==null) {
			supplierMapper.insert(supplier);
		} else {
			supplierMapper.updateByPrimaryKey(supplier);
		}
		
	}

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Supplier supplier) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		String name = supplier.getName();
		String address = supplier.getAddress();
		String contact = supplier.getContact();
		String tele = supplier.getTele();
		String email = supplier.getEmail();
		String type = supplier.getType();
		
		SupplierExample example = new SupplierExample();
		
		//封装条件
		Criteria criteria = example.createCriteria();
		
		//按登录名模糊查询
		if(!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if(!StringUtils.isEmpty(address)) {
			criteria.andAddressEqualTo(address);
		}
		if(!StringUtils.isEmpty(contact)) {
			criteria.andContactEqualTo(contact);
		}
		if(!StringUtils.isEmpty(tele)) {
			criteria.andTeleEqualTo(tele);
		}
		if(!StringUtils.isEmpty(email)) {
			criteria.andEmailEqualTo(email);
		}
		if(!StringUtils.isEmpty(type)) {
			criteria.andTypeEqualTo(type);
		}
		
		PageHelper.startPage(page, rows);
		
		List<Supplier> list = supplierMapper.selectByExample(example);
		
		PageInfo<Supplier> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	@Override
	public void deleteSuppliers(List<Integer> list) {	
		for(Integer id:list) {
			supplierMapper.deleteByPrimaryKey(id);
		}
		
	}

}
