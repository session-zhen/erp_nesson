package com.hqyj.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.mapper.EmpMapper;
import com.hqyj.mapper.GoodsMapper;
import com.hqyj.mapper.StoreMapper;
import com.hqyj.mapper.StoreoperMapper;
import com.hqyj.pojo.Storeoper;
import com.hqyj.pojo.StoreoperExample;
import com.hqyj.pojo.StoreoperExample.Criteria;
import com.hqyj.service.StoreoperService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class StoreoperServiceImpl implements StoreoperService {

	@Autowired
	private StoreoperMapper storeoperMapper;
	@Autowired
	private EmpMapper empMapper;
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public List<Storeoper> getAll() {
		
		StoreoperExample example = new StoreoperExample();
		
		List<Storeoper> list = storeoperMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public void addOrEditStoreoper(Storeoper storeoper) {
		
		if(storeoper.getId()==null) {
			storeoperMapper.insert(storeoper);
		} else {
			storeoperMapper.updateByPrimaryKey(storeoper);
		}
		
	}
	
	public String getEmpName(Integer empid) {
		return empMapper.selectByPrimaryKey(empid).getName();
	}
	public String getGoodsName(Integer goodsid) {
		return goodsMapper.selectByPrimaryKey(goodsid).getName();
	}
	public String getStoreName(Integer storeid) {
		return storeMapper.selectByPrimaryKey(storeid).getName();
	}

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Storeoper storeoper) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		Integer empid = storeoper.getEmpid();
		Integer storeid = storeoper.getStoreid();
		Integer goodsid = storeoper.getGoodsid();
		String type = storeoper.getType();
		
		Date startTime = storeoper.getStartTime();
		Date endTime = storeoper.getEndTime();
		
		StoreoperExample example = new StoreoperExample();
		
		//封装条件
		Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(type)) {
			criteria.andTypeEqualTo(type);
		}
		if(empid!=null) {
			criteria.andEmpidEqualTo(empid);
		}
		if(storeid!=null) {
			criteria.andStoreidEqualTo(storeid);
		}
		if(goodsid!=null) {
			criteria.andGoodsidEqualTo(goodsid);
		}
		if(startTime!=null) {
			criteria.andOpertimeGreaterThanOrEqualTo(startTime);
		}
		if(endTime!=null) {
			criteria.andOpertimeLessThanOrEqualTo(endTime);
		}
		
		PageHelper.startPage(page, rows);
		
		List<Storeoper> list = storeoperMapper.selectByExample(example);
		
		for(Storeoper s : list) {
			Integer eId = s.getEmpid();
			Integer sId = s.getStoreid();
			Integer gId = s.getGoodsid();
			
			if(eId!=null) {
				s.setEmpName(getEmpName(eId));
			}
			if(sId!=null) {
				s.setStoreName(getStoreName(sId));
			}
			if(gId!=null) {
				s.setGoodsName(getGoodsName(gId));
			}
		}
		
		PageInfo<Storeoper> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	@Override
	public void deleteStoreopers(List<Integer> list) {	
		for(Integer id:list) {
			storeoperMapper.deleteByPrimaryKey(id);
		}
		
	}

}
