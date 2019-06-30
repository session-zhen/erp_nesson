package com.hqyj.service.impl;

import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.exception.ErpException;
import com.hqyj.mapper.EmpMapper;
import com.hqyj.pojo.Emp;
import com.hqyj.pojo.EmpExample;
import com.hqyj.pojo.EmpExample.Criteria;
import com.hqyj.service.EmpService;
import com.hqyj.util.EasyUIDatagrid;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
	
	@Autowired
	private EmpMapper empMapper;
	
	//查询所有
	@Override
	public List<Emp> getAll() {
		
		EmpExample example = new EmpExample();
		
		return empMapper.selectByExample(example);
	}

	//分页查询
	@Override
	public EasyUIDatagrid getByPage(Integer page, Integer rows,Emp emp) {
		
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		
		String username = emp.getUsername();
		String gender = emp.getGender();
		Integer depid = emp.getDepid();
		
		EmpExample example = new EmpExample();
		
		//封装条件
		Criteria criteria = example.createCriteria();
		
		//按登录名模糊查询
		if(!StringUtils.isEmpty(username)) {
			criteria.andUsernameLike("%" + username + "%");
		}
		if(!StringUtils.isEmpty(gender)) {
			criteria.andGenderEqualTo(gender);
		}
		if(depid!=null) {
			criteria.andDepidEqualTo(depid);
		}
		
		PageHelper.startPage(page, rows);
		
		List<Emp> list = empMapper.selectByExample(example);
		
		PageInfo<Emp> pageInfo = new PageInfo<>(list);
		
		datagrid.setTotal(pageInfo.getTotal());
		datagrid.setRows(pageInfo.getList());
		
		return datagrid;
	}

	//添加或修改
	@Override
	public void addOrEditEmp(Emp emp) {
		//添加用户
		if(emp.getId()==null) {
			//设置原始密码
			Md5Hash md5 = new Md5Hash(emp.getUsername(), emp.getUsername(), 2);
			emp.setPwd(md5.toString());
			empMapper.insert(emp);
		//修改用户
		} else {
			//不修改密码
			empMapper.updateByPrimaryKeySelective(emp);
		}
	}

	@Override
	public void deleteEmps(List<Integer> list) {
		for(Integer id : list) {
			empMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public Emp loginUser(Emp emp) {
		
		EmpExample example = new EmpExample();
		
		String username = emp.getUsername();
		String pwd = new Md5Hash(emp.getPwd(), emp.getUsername(), 2).toString();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andUsernameEqualTo(username);
		criteria.andPwdEqualTo(pwd);
		
		List<Emp> list = empMapper.selectByExample(example);
		
		//没有用户
		if(list.size()==0) {
			throw new ErpException("用户名不存在或密码输入错误！");
		} else {
			return list.get(0);
		}
		
	}

	@Override
	public void editPwd(Emp emp) {
		Emp e = empMapper.selectByPrimaryKey(emp.getId());
		//对密码加密
		String pwd = new Md5Hash(emp.getPwd(), e.getUsername(), 2).toString();
		//设置加密的新密码
		emp.setPwd(pwd);
		//更新数据库
		empMapper.updateByPrimaryKeySelective(emp);
		
	}
}
