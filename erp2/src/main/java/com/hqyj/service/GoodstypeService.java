package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Goodstype;
import com.hqyj.util.EasyUIDatagrid;

public interface GoodstypeService {

	public List<Goodstype> getAll();

	public void addOrEditGoodstype(Goodstype goodstype);

	public EasyUIDatagrid getByPage(Integer page, Integer rows, Goodstype goodstype);

	public void deleteGoodstypes(List<Integer> list);
}
