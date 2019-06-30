package com.hqyj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqyj.pojo.Menu;
import com.hqyj.service.MenuService;

@RestController
public class MenuController {
	
	@Autowired
	private MenuService menuService;

	@RequestMapping("/get_menu")
	public Menu getMenu() {
		return menuService.getMenuById("0");
	}
}
