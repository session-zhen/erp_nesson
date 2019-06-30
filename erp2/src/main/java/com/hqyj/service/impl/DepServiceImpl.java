package com.hqyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.mapper.DepMapper;
import com.hqyj.pojo.Dep;
import com.hqyj.pojo.DepExample;
import com.hqyj.pojo.DepExample.Criteria;
import com.hqyj.service.DepService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class DepServiceImpl implements DepService {
	
	@Autowired
	private DepMapper depMapper;

	@Override
	public List<Dep> getAll() {
		
		DepExample example = new DepExample();
		
		List<Dep> list = depMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public void addOrEditDep(Dep dep) {
		
		if(dep.getId()==null) {
			depMapper.insert(dep);
		} else {
			depMapper.updateByPrimaryKey(dep);
		}
		
	}

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Dep dep) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		String name = dep.getName();
		String tele = dep.getTele();
		
		DepExample example = new DepExample();
		
		//封装条件
		Criteria criteria = example.createCriteria();
		
		//按登录名模糊查询
		if(!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if(!StringUtils.isEmpty(tele)) {
			criteria.andTeleEqualTo(tele);
		}
		
		PageHelper.startPage(page, rows);
		
		List<Dep> list = depMapper.selectByExample(example);
		
		PageInfo<Dep> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	@Override
	public void deleteDeps(List<Integer> list) {	
		for(Integer id:list) {
			depMapper.deleteByPrimaryKey(id);
		}
		
	}

}
