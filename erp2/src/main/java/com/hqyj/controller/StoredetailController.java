package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Storedetail;
import com.hqyj.service.StoredetailService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class StoredetailController {

	@Autowired
	private StoredetailService storedetailService;

	@RequestMapping("/storedetail_all")
	public List<Storedetail> storedetail_all() {
		return storedetailService.getAll();
	}

	// 添加或修改
	@RequestMapping("/storedetail_addOrEdit")
	public Map<String, Object> storedetail_addOrEdit(Storedetail storedetail) {

		Map<String, Object> map = new HashMap<>();

		try {
			storedetailService.addOrEditStoredetail(storedetail);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;

	}

	// 分页查询
	@RequestMapping("/storedetail_page")
	public EasyUIDatagrid storedetail_page(Integer page, Integer rows, Storedetail storedetail) {
		return storedetailService.getByPage(page, rows, storedetail);
	}

	@RequestMapping("/storedetail_delete")
	public Map<String, Object> storedetail_delete(String ids) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {
		});

		Map<String, Object> map = new HashMap<>();

		try {
			storedetailService.deleteStoredetails(lists);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}
}
