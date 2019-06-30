package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Supplier;
import com.hqyj.service.SupplierService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@RequestMapping("/supplier_all")
	public List<Supplier> supplier_all(String type) {
		return supplierService.getAll(type);
	}

	// 添加或修改
	@RequestMapping("/supplier_addOrEdit")
	public Map<String, Object> supplier_addOrEdit(Supplier supplier) {

		Map<String, Object> map = new HashMap<>();

		try {
			supplierService.addOrEditSupplier(supplier);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;

	}

	// 分页查询
	@RequestMapping("/supplier_page")
	public EasyUIDatagrid supplier_page(Integer page, Integer rows, Supplier supplier) {
		return supplierService.getByPage(page, rows, supplier);
	}

	@RequestMapping("/supplier_delete")
	public Map<String, Object> supplier_delete(String ids) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {
		});

		Map<String, Object> map = new HashMap<>();

		try {
			supplierService.deleteSuppliers(lists);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}
}
