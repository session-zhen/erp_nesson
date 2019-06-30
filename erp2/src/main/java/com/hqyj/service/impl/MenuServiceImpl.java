package com.hqyj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqyj.mapper.MenuMapper;
import com.hqyj.pojo.Menu;
import com.hqyj.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public Menu getMenuById(String menuid) {
		return menuMapper.selectByPrimaryKey(menuid);
	}

}
