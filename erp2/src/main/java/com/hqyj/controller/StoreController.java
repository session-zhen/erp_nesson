package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Emp;
import com.hqyj.pojo.Store;
import com.hqyj.service.StoreService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class StoreController {

	@Autowired
	private StoreService storeService;

	@RequestMapping("/store_all")
	public List<Store> store_all() {
		return storeService.getAll(null);
	}
	
	@RequestMapping("/store_myList")
	public List<Store> store_myList(HttpServletRequest request) {
		Emp emp = (Emp) request.getSession().getAttribute("loginUser");
		return storeService.getAll(emp.getId());
	}

	// 添加或修改
	@RequestMapping("/store_addOrEdit")
	public Map<String, Object> store_addOrEdit(Store store) {

		Map<String, Object> map = new HashMap<>();

		try {
			storeService.addOrEditStore(store);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;

	}

	// 分页查询
	@RequestMapping("/store_page")
	public EasyUIDatagrid store_page(Integer page, Integer rows, Store store) {
		return storeService.getByPage(page, rows, store);
	}

	@RequestMapping("/store_delete")
	public Map<String, Object> store_delete(String ids) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {
		});

		Map<String, Object> map = new HashMap<>();

		try {
			storeService.deleteStores(lists);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}
}
