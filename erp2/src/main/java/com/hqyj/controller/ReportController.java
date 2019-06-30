package com.hqyj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqyj.pojo.Report;
import com.hqyj.service.ReportService;

@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@RequestMapping("/report_all")
	public List<Report> report_all(Report report) {
		return reportService.getReportList(report);
	}
}
