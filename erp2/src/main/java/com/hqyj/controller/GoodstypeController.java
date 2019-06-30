package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Goodstype;
import com.hqyj.service.GoodstypeService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class GoodstypeController {

	@Autowired
	private GoodstypeService goodstypeService;

	@RequestMapping("/goodstype_all")
	public List<Goodstype> goodstype_all() {
		return goodstypeService.getAll();
	}

	// 添加或修改
	@RequestMapping("/goodstype_addOrEdit")
	public Map<String, Object> goodstype_addOrEdit(Goodstype goodstype) {

		Map<String, Object> map = new HashMap<>();

		try {
			goodstypeService.addOrEditGoodstype(goodstype);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;

	}

	// 分页查询
	@RequestMapping("/goodstype_page")
	public EasyUIDatagrid goodstype_page(Integer page, Integer rows, Goodstype goodstype) {
		return goodstypeService.getByPage(page, rows, goodstype);
	}

	@RequestMapping("/goodstype_delete")
	public Map<String, Object> goodstype_delete(String ids) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {
		});

		Map<String, Object> map = new HashMap<>();

		try {
			goodstypeService.deleteGoodstypes(lists);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}
}
