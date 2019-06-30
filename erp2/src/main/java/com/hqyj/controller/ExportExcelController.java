package com.hqyj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqyj.pojo.Orderdetail;
import com.hqyj.pojo.Orders;
import com.hqyj.service.MyExcelService;

@RestController
public class ExportExcelController {
	@Autowired
	private MyExcelService myExcelService;
	@RequestMapping("/exceldata_out")
	public void excel_out(Orders orders) {
		myExcelService.myExcelOut(orders);
	}
}
