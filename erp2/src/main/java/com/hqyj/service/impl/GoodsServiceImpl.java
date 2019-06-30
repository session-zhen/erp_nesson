package com.hqyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.mapper.GoodsMapper;
import com.hqyj.pojo.Goods;
import com.hqyj.pojo.GoodsExample;
import com.hqyj.pojo.GoodsExample.Criteria;
import com.hqyj.service.GoodsService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public List<Goods> getAll() {
		
		GoodsExample example = new GoodsExample();
		
		List<Goods> list = goodsMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public void addOrEditGoods(Goods goods) {
		
		if(goods.getId()==null) {
			goodsMapper.insert(goods);
		} else {
			goodsMapper.updateByPrimaryKey(goods);
		}
		
	}

	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows, Goods goods) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		String name = goods.getName();
		String origin = goods.getOrigin();
		String producer = goods.getProducer();
		String unit = goods.getUnit();
		Integer goodstypeid = goods.getGoodstypeid();
		
		GoodsExample example = new GoodsExample();
		
		//封装条件
		Criteria criteria = example.createCriteria();
		
		//按登录名模糊查询
		if(!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if(!StringUtils.isEmpty(origin)) {
			criteria.andOriginEqualTo(origin);
		}
		if(!StringUtils.isEmpty(producer)) {
			criteria.andProducerEqualTo(producer);
		}
		if(!StringUtils.isEmpty(unit)) {
			criteria.andUnitEqualTo(unit);
		}
		if(goodstypeid!=null) {
			criteria.andGoodstypeidEqualTo(goodstypeid);
		}
		
		PageHelper.startPage(page, rows);
		
		List<Goods> list = goodsMapper.selectByExample(example);
		
		PageInfo<Goods> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	@Override
	public void deleteGoodss(List<Integer> list) {	
		for(Integer id:list) {
			goodsMapper.deleteByPrimaryKey(id);
		}
		
	}

}
