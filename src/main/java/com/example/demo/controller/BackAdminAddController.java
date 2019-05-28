package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.BackAdminService;
import com.example.demo.util.Base64;
import com.example.demo.util.MD5;
import com.example.demo.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IMChiLe on 2019/4/6 10:20
 */
@Controller
@RequestMapping("backAdminAdd")
public class BackAdminAddController {

    @Autowired
    @Resource
    private BackAdminService backAdminService;

    //后台页面
    @RequestMapping("/html")
    public String backAdminAdd(){
        return "BackAdminAdd";
    }

    //注册后台用户
    @ResponseBody
    @RequestMapping("/add")
    public String addAdmin(Model model,@RequestParam(value = "account")String account,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "phone") String phone,
                           @RequestParam(value = "sex") int sex,
                           @RequestParam(value = "location") int location,
                           @RequestParam(value = "img") String img,
                           HttpServletRequest session){
        JSONObject json=new JSONObject();
        String s1 = location + "backadmin";
        try {
            String s = backAdminService.selectAdmin(s1,account);
            if(s != null){
                json.put("zt","zai");
                return json.toJSONString();
            }
            Time time = new Time();
            MD5 md5 = new MD5();
            String entrytime = time.getTimeymd();
            String r3 = img.substring(img.indexOf(",")+1);//去掉base64字符串的开头部分
            String newimg = r3.replaceAll(" ","+");
            //设置文件名
            String newName = account + ".jpg";
            //生成图片的路径
            String pImagesPath = System.getProperty("user.dir") + "/src/main/resources/static/img/upLoad/" + newName;
            // 对字节数组字符串进行Base64解码并-->生成图片
            Base64 base64 = new Base64();
            base64.GenerateImage(newimg,pImagesPath);
            backAdminService.insertAdmin(s1,account,name,md5.getMD5(password),phone,newName,sex,entrytime);
            json.put("zt","ok");
            return json.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        json.put("zt","no");
        return json.toJSONString();
    }
}
