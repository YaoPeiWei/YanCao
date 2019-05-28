package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.UserService;
import com.example.demo.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by IMChiLe on 2019/4/20 22:33
 */
@Controller
@RequestMapping("userAdd")
public class UserAddController {

    @Autowired
    @Resource
    private UserService userService;
    //用户注册页面
    @RequestMapping("/html")
    public String userAddHtml(HttpSession session){
        return "UserAdd";
    }

    //注册用户
    @ResponseBody
    @RequestMapping("/add")
    public String userAdd(@RequestParam(value = "name") String name,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "location") int location,
                          @RequestParam(value = "phone") String phone){
        JSONObject json=new JSONObject();
        try {
            String s= userService.selectUser(name);
            String s1 = userService.selectUserPhone(phone);
            if(s != null){
                json.put("zt","name");
                return json.toJSONString();
            }
            if(s1 != null){
                json.put("zt","phone");
                return json.toJSONString();
            }
            MD5 md5 = new MD5();
            userService.insertUser(name,md5.getMD5(password),location,phone);
            json.put("zt","ok");
            return json.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("zt","no");
        return json.toJSONString();
    }
}
