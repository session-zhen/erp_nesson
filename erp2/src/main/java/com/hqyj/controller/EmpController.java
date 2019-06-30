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
import com.hqyj.service.EmpService;
import com.hqyj.util.EasyUIDatagrid;

@RestController//@RestController=@Controller + @ResponseBody
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	
	//退出登录
	@RequestMapping("/emp_loginout")
	public void emp_loginout(HttpServletRequest request) {
		request.getSession().removeAttribute("loginUser");
	}
	
	@RequestMapping("/emp_editPwd")
	public Map<String,Object> emp_editPwd(Emp emp,HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		
		try {
			empService.editPwd(emp);
			//移除Session
			request.getSession().removeAttribute("loginUser");
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		
		return map;
	}
	
	@RequestMapping("/check_login")
	public Map<String,Object> check_login(HttpServletRequest request) {
		
		Map<String,Object> map = new HashMap<>();
		
		Emp loginUser = (Emp) request.getSession().getAttribute("loginUser");
		
		if(loginUser!=null) {
			map.put("success", true);
			map.put("loginUser", loginUser);
		} else {
			map.put("success", false);
		}
		
		return map;
	}
	
	
	@RequestMapping("/emp_login")
	public Map<String,Object> emp_login(Emp emp,HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<>();
		
		try {
			Emp loginUser = empService.loginUser(emp);
			request.getSession().setAttribute("loginUser", loginUser);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
	
	//添加或修改
	@RequestMapping("/emp_addOrEdit")
	public Map<String,Object> emp_addOrEdit(Emp emp) {
		
		Map<String,Object> map = new HashMap<>();
		
		try {
			empService.addOrEditEmp(emp);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		
		return map;
		
	}

	//查询所有
	@RequestMapping("/emp_all")
	public List<Emp> emp_all() {
		return empService.getAll();
	}
	
	//分页查询
	@RequestMapping("/emp_page")
	public EasyUIDatagrid emp_page(Integer page,Integer rows,Emp emp) {
		return empService.getByPage(page, rows,emp);
	}
	
	@RequestMapping("/emp_delete")
	public Map<String,Object> emp_delete(String ids) throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<Integer> lists = mapper.readValue(ids, new TypeReference<List<Integer>>() {});
		
		Map<String,Object> map = new HashMap<>();
		
		try {
			empService.deleteEmps(lists);
			map.put("success", true);
		} catch(Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}	
		return map;
	}
	
	
}
