package com.hqyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqyj.mapper.ReportMapper;
import com.hqyj.pojo.Report;
import com.hqyj.service.ReportService;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportMapper reportMapper;

	@Override
	public List<Report> getReportList(Report report) {
		return reportMapper.getReportList(report);
	}

}
