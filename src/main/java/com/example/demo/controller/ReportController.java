package com.example.demo.controller;

import com.example.demo.pojo.BackAdmin;
import com.example.demo.pojo.BadData;
import com.example.demo.pojo.Report;
import com.example.demo.pojo.User;
import com.example.demo.service.BadDataService;
import com.example.demo.service.ReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/Report")
public class ReportController {
    @Resource
    private BadDataService badDataService;

    @Resource
    private ReportService reportService;

    @RequestMapping("/GetReport")
    public String GetReport(){
        return "Report";
    }

    @RequestMapping("/InsertReport")
    public String InsertReport(@Param("report") String report, HttpSession session){
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(date);
        User user = (User)session.getAttribute("user");
        Report report1=new Report(user.getName(),report,dateString);
        int i = 0;
        try {
            i = reportService.InsertReport(report1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(i>0){
            return "redirect:/Cigarette/SelectCigarette";
        }
        return "test";
    }

    @ResponseBody
    @RequestMapping("/CheckChar")
    public HashMap<String,List> CheckChar(@Param("report") String report){
        //System.out.println("ajax请求到达");
        HashMap<String,List> map=new HashMap<String,List>();
        List<String> list=new ArrayList<String>();
        List<BadData> strings = badDataService.GetBadData();
        //System.out.println("s:"+strings.size());
        for (int i = 0; i < strings.size(); i++) {
            String s=strings.get(i).getBaddata();
            //System.out.println(s);
            if(report.contains(s)){
                list.add(s);
            }
        }
        map.put("data",list);
        return map;
    }

    @RequestMapping("/selectReport")
    public String selectReport(Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){
        try {
            PageHelper.startPage(start,size);
            List<Report> reportList = reportService.selectReport();
            PageInfo<Report> page = new PageInfo<>(reportList);
            model.addAttribute("report",page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "BackAdminReport";
    }
}
