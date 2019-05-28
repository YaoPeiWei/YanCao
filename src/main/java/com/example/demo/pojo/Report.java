package com.example.demo.pojo;

import java.util.Date;

public class Report {
    private String name;
    private  String neirong;
    private String reportdate;

    public Report(String name, String neirong, String reportdate) {
        this.name = name;
        this.neirong = neirong;
        this.reportdate = reportdate;
    }

    public Report() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }
}
