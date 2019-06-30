package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Storeoper;
import com.hqyj.service.StoreoperService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class StoreoperController {

	@Autowired
	private StoreoperService storeoperService;

	@RequestMapping("/storeoper_all")
	public List<Storeoper> storeoper_all() {
		return storeoperService.getAll();
	}

	// 添加或修改
	@RequestMapping("/storeoper_addOrEdit")
	public Map<String, Object> storeoper_addOrEdit(Storeoper storeoper) {

		Map<String, Object> map = new HashMap<>();

		try {
			storeoperService.addOrEditStoreoper(storeoper);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;

	}

	// 分页查询
	@RequestMapping("/storeoper_page")
	public EasyUIDatagrid storeoper_page(Integer page, Integer rows, Storeoper storeoper) {
		return storeoperService.getByPage(page, rows, storeoper);
	}

	@RequestMapping("/storeoper_delete")
	public Map<String, Object> storeoper_delete(String ids) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {
		});

		Map<String, Object> map = new HashMap<>();

		try {
			storeoperService.deleteStoreopers(lists);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}
}
