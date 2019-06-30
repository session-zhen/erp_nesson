package com.hqyj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.pojo.Dep;
import com.hqyj.service.DepService;
import com.hqyj.util.EasyUIDatagrid;

@RestController
public class DepController {

	@Autowired
	private DepService depService;

	@RequestMapping("/dep_all")
	public List<Dep> dep_all() {
		return depService.getAll();
	}

	// 添加或修改
	@RequestMapping("/dep_addOrEdit")
	public Map<String, Object> dep_addOrEdit(Dep dep) {

		Map<String, Object> map = new HashMap<>();

		try {
			depService.addOrEditDep(dep);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;

	}

	// 分页查询
	@RequestMapping("/dep_page")
	public EasyUIDatagrid dep_page(Integer page, Integer rows, Dep dep) {
		return depService.getByPage(page, rows, dep);
	}

	@RequestMapping("/dep_delete")
	public Map<String, Object> dep_delete(String ids) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {
		});

		Map<String, Object> map = new HashMap<>();

		try {
			depService.deleteDeps(lists);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}

		return map;
	}

}
