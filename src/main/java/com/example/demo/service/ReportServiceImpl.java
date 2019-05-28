package com.example.demo.service;

import com.example.demo.dao.ReportMapper;
import com.example.demo.pojo.Report;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{
    @Resource
    private ReportMapper mapper;
    @Override
    public int InsertReport(Report report) throws Exception{
        return mapper.InsertReport(report);
    }

    @Override
    public List<Report> selectReport() throws Exception {
        return mapper.selectReport();
    }
}
