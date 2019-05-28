package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.util.MD5;
import com.example.demo.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IMChiLe on 2019/4/16 10:23
 */
@Controller
@RequestMapping("/userIndex")
public class UserIndexController {

    @Autowired
    @Resource
    private UserService userService;

    //用户登陆页面
    @RequestMapping("/html")
    public String Logins(HttpSession session){
        return "UserLogin";
    }

    @RequestMapping("/head")
    public String userHeads(HttpSession session){
        return "head";
    }

    //登陆验证
    @ResponseBody
    @RequestMapping("/user")
    public String selectUserLogin(@RequestParam(value = "name") String name,
                                  @RequestParam(value = "password")String password, HttpServletRequest session){
        JSONObject json=new JSONObject();
        try {
            String s = userService.selectUserName(name);
            if(s!=null){

                MD5 md5 = new MD5();
                User user = userService.userLogin(name,md5.getMD5(password));
                if(user!=null){
                    user.setPassword(null);
                    session.getSession().setAttribute("user",user);
                    json.put("zt","ok");
                    return json.toJSONString();
                }
                user = userService.userLoginPhone(name,md5.getMD5(password));
                if(user!=null){
                    user.setPassword(null);
                    session.getSession().setAttribute("user",user);
                    json.put("zt","ok");
                    return json.toJSONString();
                }
                json.put("zt","noPass");
                return json.toJSONString();
            }else {
                json.put("zt","noName");
                return json.toJSONString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("zt","no");
        return json.toJSONString();
    }

    //系统登出
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest session){
        session.getSession().removeAttribute("user");
        return "redirect:../userIndex/html";
    }

    @RequestMapping("/findPassword")
    public String findPassword(){
        return "UserMail";
    }

    @RequestMapping("/userMail")
    public String userMail(@RequestParam(value = "userid") String userid, @RequestParam(value = "email")String email)throws Exception{
        //获取用户的邮箱
        User user = null;
        //实例化一个发送邮件的对象
        SendMail mySendMail = new SendMail();
        //根据邮箱找到该用户信息
        user = userService.selectUserPassword(userid);
        MD5 md5 = new MD5();
        if(user!=null) {
            System.out.println("发送邮件成功");
            //设置收件人和消息内容
            mySendMail.sendMail(email, "汕尾烟草管理系统提醒，您的密码为："+md5.convertMD5(md5.convertMD5(user.getPassword())));

        }
        return "redirect:../userIndex/html";
    }
}
