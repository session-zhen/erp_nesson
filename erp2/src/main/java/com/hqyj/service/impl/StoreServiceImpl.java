package com.hqyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.mapper.StoreMapper;
import com.hqyj.pojo.Store;
import com.hqyj.pojo.StoreExample;
import com.hqyj.pojo.StoreExample.Criteria;
import com.hqyj.service.StoreService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreMapper storeMapper;

	@Override
	public List<Store> getAll(Integer empid) {
		
		StoreExample example = new StoreExample();
		
		if(empid != null) {
			Criteria criteria = example.createCriteria();
			criteria.andEmpidEqualTo(empid);
		}

		List<Store> list = storeMapper.selectByExample(example);

		return list;
	}

	@Override
	public void addOrEditStore(Store store) {

		if (store.getId() == null) {
			storeMapper.insert(store);
		} else {
			storeMapper.updateByPrimaryKey(store);
		}

	}

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Store store) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();

		String name = store.getName();
		Integer empid = store.getEmpid();

		StoreExample example = new StoreExample();

		// 封装条件
		Criteria criteria = example.createCriteria();

		// 按登录名模糊查询
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if (empid != null) {
			criteria.andEmpidEqualTo(empid);
		}

		PageHelper.startPage(page, rows);

		List<Store> list = storeMapper.selectByExample(example);

		PageInfo<Store> pageInfo = new PageInfo<>(list);

		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());

		return datagrid;
	}

	@Override
	public void deleteStores(List<Integer> list) {
		for (Integer id : list) {
			storeMapper.deleteByPrimaryKey(id);
		}

	}

}
