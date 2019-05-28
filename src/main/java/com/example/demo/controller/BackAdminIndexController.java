package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.BackAdmin;
import com.example.demo.service.BackAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IMChiLe on 2019/4/10 21:00
 */
@Controller
@RequestMapping("backAdminIndex")
public class BackAdminIndexController {
    @Autowired
    @Resource
    private BackAdminService backAdminService;


    //所有业务员列表
    @RequestMapping("/user")
    public String backAdminUser(Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){
        try {
            PageHelper.startPage(start,size);
            List<BackAdmin> list = backAdminService.selectAllAdmin();
            PageInfo<BackAdmin> page = new PageInfo<>(list);
            model.addAttribute("backUser",page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "BackAdminUser";
    }

    //修改业务员状态
    @ResponseBody
    @RequestMapping("/update")
    public String updateService(@RequestParam(value = "account") String account,
                                @RequestParam(value = "location")int location){
        JSONObject json=new JSONObject();
        //System.out.println(location);
        String s1 = location + "backadmin";
        try {
            backAdminService.updateService(s1,account);
            json.put("zt","ok");
            return json.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("zt","no");
        return json.toJSONString();
    }

    //恢复业务员状态
    @ResponseBody
    @RequestMapping("/update1")
    public String updateService1(@RequestParam(value = "account") String account,
                                @RequestParam(value = "location")int location){
        JSONObject json=new JSONObject();
        //System.out.println(location);
        String s1 = location + "backadmin";
        try {
            backAdminService.updateService1(s1,account);
            json.put("zt","ok");
            return json.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("zt","no");
        return json.toJSONString();
    }
}
