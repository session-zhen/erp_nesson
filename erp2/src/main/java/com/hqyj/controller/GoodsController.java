package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Goods;
import com.hqyj.service.GoodsService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class GoodsController {

	
	@Autowired
	private GoodsService goodsService;

	@RequestMapping("/goods_all")
	public List<Goods> goods_all() {
		return goodsService.getAll();
	}

	// 添加或修改
	@RequestMapping("/goods_addOrEdit")
	public Map<String, Object> goods_addOrEdit(Goods goods) {

		Map<String, Object> map = new HashMap<>();

		try {
			goodsService.addOrEditGoods(goods);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;

	}

	// 分页查询
	@RequestMapping("/goods_page")
	public EasyUIDatagrid goods_page(Integer page, Integer rows, Goods goods) {
		return goodsService.getByPage(page, rows, goods);
	}

	@RequestMapping("/goods_delete")
	public Map<String, Object> goods_delete(String ids) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {
		});

		Map<String, Object> map = new HashMap<>();

		try {
			goodsService.deleteGoodss(lists);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}
}
