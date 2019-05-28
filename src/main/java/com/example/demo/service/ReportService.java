package com.example.demo.service;

import com.example.demo.pojo.Report;

import java.util.List;

public interface ReportService {
    public int InsertReport(Report report)throws Exception;
    public List<Report> selectReport() throws Exception;
}
