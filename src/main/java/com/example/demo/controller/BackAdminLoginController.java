package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.BackAdmin;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserBuy;
import com.example.demo.service.BackAdminService;
import com.example.demo.service.CheckAndModifyService;
import com.example.demo.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IMChiLe on 2019/4/8 10:59
 */
@Controller
@RequestMapping("backAdminLogin")
public class BackAdminLoginController {

    @Autowired
    @Resource
    private BackAdminService backAdminService;

    @Resource
    private CheckAndModifyService checkAndModifyService;

    @RequestMapping("/html")
    public String backAdminLogin(){
        return "BackAdminLogin";
    }

    @RequestMapping("/html2")
    public String backAdmin(){
        return "DaoHang";
    }

    @RequestMapping("/index")
    public String backAdminIndex(){
        return "BackAdminIndex";
    }

    //业务员登陆
    @ResponseBody
    @RequestMapping("/login")
    public String backUserSubmit(Model model, @RequestParam(value = "account") String account,
                                 @RequestParam(value = "password") String password, HttpServletRequest session){
        try {
            JSONObject json=new JSONObject();
            char c = account.charAt(0);//获取登陆地区,拼接表名
            String s1= c + "backadmin";
            String  s = backAdminService.selectAdmin(s1,account);
            if(s != null){
                int service = backAdminService.selectAdminService(s1,account);
                System.out.println(service);
                if(service != 0){
                    MD5 md5 = new MD5();
                    BackAdmin backAdmin  = backAdminService.loginAdmin(s1,account,md5.getMD5(password));
                    if(backAdmin != null){
                        backAdmin.setPassword(null);
                        session.getSession().setAttribute("backadmin",backAdmin);
                        json.put("zt","ok");


                        //调用查询userbuy表中查询未处理信息总数的方法
                        int location=backAdmin.getLocation();
                        int unprocessedInformation=checkAndModifyService.findUnprocessedInformation(location);
                        session.getSession().setAttribute("unprocessedInformation",unprocessedInformation);
                        return json.toJSONString();
                    }else {
                        json.put("zt","noPass");
                        return json.toJSONString();
                    }
                }else {
                    json.put("zt","noService");
                    return json.toJSONString();
                }
            }else {
                json.put("zt","noName");
                return json.toJSONString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{zt:'no'}";
    }

    //系统登出
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest session){
        session.getSession().removeAttribute("backadmin");
        return "redirect:../backAdminLogin/html";
    }
}
