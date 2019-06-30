package com.hqyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.mapper.GoodsMapper;
import com.hqyj.mapper.StoreMapper;
import com.hqyj.mapper.StoredetailMapper;
import com.hqyj.pojo.Storedetail;
import com.hqyj.pojo.StoredetailExample;
import com.hqyj.pojo.StoredetailExample.Criteria;
import com.hqyj.service.StoredetailService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class StoredetailServiceImpl implements StoredetailService {

	@Autowired
	private StoredetailMapper storedetailMapper;
	
	@Autowired
	private StoreMapper storeMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public List<Storedetail> getAll() {
		
		StoredetailExample example = new StoredetailExample();
		
		List<Storedetail> list = storedetailMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public void addOrEditStoredetail(Storedetail storedetail) {
		
		if(storedetail.getId()==null) {
			storedetailMapper.insert(storedetail);
		} else {
			storedetailMapper.updateByPrimaryKey(storedetail);
		}
		
	}
	
	public String getGoodsName(Integer goodsid) {
		return goodsMapper.selectByPrimaryKey(goodsid).getName();
	}
	
	public String getStoreName(Integer storeid) {
		return storeMapper.selectByPrimaryKey(storeid).getName();
	}

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Storedetail storedetail) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		Integer storeid = storedetail.getStoreid();
		Integer goodsid = storedetail.getGoodsid();
		
		StoredetailExample example = new StoredetailExample();
		
		//封装条件
		Criteria criteria = example.createCriteria();
		
		//按登录名模糊查询
		if(storeid!=null) {
			criteria.andStoreidEqualTo(storeid);
		}
		if(goodsid!=null) {
			criteria.andGoodsidEqualTo(goodsid);
		}
		
		PageHelper.startPage(page, rows);
		
		List<Storedetail> list = storedetailMapper.selectByExample(example);
		
		for(Storedetail s : list) {
			Integer sId = s.getStoreid();
			Integer gId = s.getGoodsid();
			if(sId!=null) {
				s.setStoreName(getStoreName(sId));
			}
			if(gId!=null) {
				s.setGoodsName(getGoodsName(gId));
			}
		}
		
		PageInfo<Storedetail> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	@Override
	public void deleteStoredetails(List<Integer> list) {	
		for(Integer id:list) {
			storedetailMapper.deleteByPrimaryKey(id);
		}
		
	}

}
