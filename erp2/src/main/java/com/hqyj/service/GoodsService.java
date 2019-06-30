package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Goods;
import com.hqyj.util.EasyUIDatagrid;

public interface GoodsService {

	public List<Goods> getAll();

	public void addOrEditGoods(Goods goods);

	public EasyUIDatagrid getByPage(Integer page, Integer rows, Goods goods);

	public void deleteGoodss(List<Integer> list);
}
