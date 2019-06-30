package com.hqyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.mapper.GoodstypeMapper;
import com.hqyj.pojo.Goodstype;
import com.hqyj.pojo.GoodstypeExample;
import com.hqyj.pojo.GoodstypeExample.Criteria;
import com.hqyj.service.GoodstypeService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class GoodstypeServiceImpl implements GoodstypeService {

	@Autowired
	private GoodstypeMapper goodstypeMapper;

	@Override
	public List<Goodstype> getAll() {
		
		GoodstypeExample example = new GoodstypeExample();
		
		List<Goodstype> list = goodstypeMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public void addOrEditGoodstype(Goodstype goodstype) {
		
		if(goodstype.getId()==null) {
			goodstypeMapper.insert(goodstype);
		} else {
			goodstypeMapper.updateByPrimaryKey(goodstype);
		}
		
	}

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Goodstype goodstype) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		String name = goodstype.getName();
		
		GoodstypeExample example = new GoodstypeExample();
		
		//封装条件
		Criteria criteria = example.createCriteria();
		
		//按登录名模糊查询
		if(!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		
		PageHelper.startPage(page, rows);
		
		List<Goodstype> list = goodstypeMapper.selectByExample(example);
		
		PageInfo<Goodstype> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	@Override
	public void deleteGoodstypes(List<Integer> list) {	
		for(Integer id:list) {
			goodstypeMapper.deleteByPrimaryKey(id);
		}
		
	}

}
