package com.hqyj.service;

import java.util.List;

import com.hqyj.pojo.Dep;
import com.hqyj.util.EasyUIDatagrid;

public interface DepService {

	public List<Dep> getAll();
	
	public void addOrEditDep(Dep dep);
	
	public EasyUIDatagrid getByPage(Integer page,Integer rows,Dep dep);
	
	public void deleteDeps(List<Integer> list);
}
