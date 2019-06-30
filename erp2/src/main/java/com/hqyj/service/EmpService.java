package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Emp;
import com.hqyj.util.EasyUIDatagrid;

public interface EmpService {

	public List<Emp> getAll();
	
	/**
	 * 修改密码
	 * @param emp
	 */
	public void editPwd(Emp emp);
	
	/**
	 * 登录用户方法
	 * @param emp
	 * @return
	 */
	public Emp loginUser(Emp emp);
	
	/**
	 * 添加员工
	 * @param emp
	 */
	public void addOrEditEmp(Emp emp);
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @param rows
	 * @return emp 封装查询条件
	 */
	public EasyUIDatagrid getByPage(Integer page,Integer rows,Emp emp);
	
	/**
	 * 删除用户
	 * @param list
	 */
	public void deleteEmps(List<Integer> list);
}
