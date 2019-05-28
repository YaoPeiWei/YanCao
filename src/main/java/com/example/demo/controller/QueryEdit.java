package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.BackAdmin;
import com.example.demo.pojo.Cigarette;
import com.example.demo.pojo.UserBuy;
import com.example.demo.service.CheckAndModifyService;
import com.example.demo.util.MD5;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/queryAndEdit")
public class QueryEdit {

    @Resource
    private CheckAndModifyService service;

    @RequestMapping("/cig")
    public String cig(Model model){

        List<Cigarette> cigaretteList=service.findCigarette();
        model.addAttribute("cigaretteList",cigaretteList);

        return "BackAdminCigarette";
    }

    //点击修改香烟操作时先查询出来并显示在要修改的表单上
    @RequestMapping("/modifyCig")
    public String cmodifyCigig(Model model,
                               @RequestParam(value="account")String account,HttpServletRequest request){
        //System.out.println("-----account------"+account);
        String url=null;

        //根据用户登陆的位置判断是否有权限修改烟草信息，若位置信息为3则有权限修改
        BackAdmin backAdmin=(BackAdmin)request.getSession().getAttribute("backadmin");
        int location=backAdmin.getLocation();

        if (location==3){
            Cigarette cigarette=service.findCig(account);
            model.addAttribute("cigarette",cigarette);

            url="BackAdminModifyCigarette";
        }else {
            request.getSession().setAttribute("noAuthority","该用户没有修改权限");
            url="forward:/queryAndEdit/cig";
        }

        return url;
    }

    //点击保存后修改香烟信息
    @RequestMapping("saveCigarette")
    public String saveCigarette(Cigarette cigarette,
                                  @RequestParam(value = "file",required=false) MultipartFile file,
                                  Model model,
                                  HttpServletRequest req){
        String fileName=null;
        String destFileName=null;

        try {
            //根据时间戳创建新的文件名(当前时间+原文件名)，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            fileName = System.currentTimeMillis()+file.getOriginalFilename();
            //获取当前项目的真实路径，然后拼接前面的文件名
            /*destFileName=req.getServletContext().getRealPath("")+"uploaded"+File.separator+fileName;*/

            destFileName = System.getProperty("user.dir") + "/src/main/resources/static/img/upLoadCigarette/" + fileName;
            /*destFileName = System.getProperty("user.dir") + File.separator+
                    "src"+File.separator+"main"+File.separator+"resources"+
                    File.separator+"static"+File.separator+"img"+File.separator+
                    "upLoad"+File.separator + fileName;*/
//            destFileName=req.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");

            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            //把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);

            model.addAttribute("fileName",fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            return "上传失败," + e.getMessage();
            req.setAttribute("msg","上传失败");
            return "forward:/queryAndEdit/modifyCig";
        } catch (IOException e) {
            e.printStackTrace();
//            return "上传失败," + e.getMessage();
            req.setAttribute("msg","上传失败");
            return "forward:/queryAndEdit/modifyCig";
        }
        //设置图片路径
        cigarette.setImg(fileName);

        int i=service.saveCigarette(cigarette);
        if (i!=0){
            return "forward:/queryAndEdit/cig";
        }else return "forward:/queryAndEdit/modifyCig";

    }


    @RequestMapping("processOrder")
    public String process(Model model, HttpServletRequest request){

        //根据用户登录的地区location查找预购置信息
        BackAdmin backAdmin=(BackAdmin)request.getSession().getAttribute("backadmin");
        //System.out.println("----getSession().getAttribute(\"backadmin\")-------"+backAdmin.getAccount());
        int location=backAdmin.getLocation();

        List<UserBuy> userBuysList=service.findUserBuyByLocation(location);
        model.addAttribute("userBuysList",userBuysList);

        return "BackAdminProcessAppointment";
    }

    /*@RequestMapping("/processed")
    @ResponseBody
    public Object process(@RequestParam(value="user") String user){
        Map<String ,Object> map=new HashMap<>();
        System.out.println(user);
        int i=service.updateUserBuyState(user);

        if (i>0)map.put("msg","success");
        else map.put("msg","error");

        return JSONObject.toJSONString(map);
    }*/
    @RequestMapping("/processed")
    public String process(@RequestParam(value="user") String user,
                          @RequestParam(value="order") String order,
                          HttpServletRequest request){
        Map<String ,Object> map=new HashMap<>();
        //System.out.println(user);
        System.out.println("----order-----"+order);
        int i=service.updateUserBuyState(user,order);

        if (i>0)map.put("msg","success");
        else map.put("msg","error");

        //更新后把原先登录时查询时放在session中的值remove掉再重新放
        request.getSession().removeAttribute("unprocessedInformation");
        //调用查询userbuy表中查询未处理信息总数的方法
        BackAdmin backAdmin=(BackAdmin)request.getSession().getAttribute("backadmin");
        int location=backAdmin.getLocation();
        int unprocessedInformation=service.findUnprocessedInformation(location);

        request.getSession().setAttribute("unprocessedInformation",unprocessedInformation);

        return "forward:/queryAndEdit/processOrder";
    }


    @RequestMapping("/queryPersonalInformation")
    public String queryPersonal(HttpServletRequest request,Model model){

        //获取登录用户信息,根据id查询个人信息在修改
        BackAdmin bAd=(BackAdmin)request.getSession().getAttribute("backadmin");
        String account=bAd.getAccount();
        //System.out.println("----account----"+account);
        //获取用户location去查询对应的表个人信息
        int location=bAd.getLocation();
        //System.out.println("-----location-------"+location);

//        String account="0gay";
//        int location=0;

        BackAdmin backAdmin=service.findBackAdmin(account,location);
        model.addAttribute("backAdmin",backAdmin);


        return "BackAdminMessage";
    }

    @RequestMapping("queryOldPassword")
    @ResponseBody
    public Object queryOldPassword(@RequestParam(value="oldpassword") String oldpassword,
                                   HttpServletRequest request){
        Map<String ,Object> map=new HashMap<>();

//        根据登录用户的location查找是哪张用户表，在根据id查找旧密码
//        request.getSession().getAttribute("");
        BackAdmin bAd=(BackAdmin)request.getSession().getAttribute("backadmin");
        int location=bAd.getLocation();
        String account=bAd.getAccount();

/*
        int location=0;
        String account="0gay";*/

        String queryPassword=service.findOldPassword(account,location);

        MD5 md5=new MD5();
        String password=md5.convertMD5(md5.convertMD5(queryPassword));
        System.out.println(password);

        if (password!=null && (!("".equals(password)))){
            if (password.equals(md5.getMD5(oldpassword)))
                map.put("msg","success");
            else map.put("msg","error");
        }else {
            map.put("msg","error");
        }


        return JSONObject.toJSONString(map);
    }

    @RequestMapping("modifyPersonalInformation")
    public String saveInformation(BackAdmin backAdmin,
                                  @RequestParam(value = "file",required=false) MultipartFile file,
                                  Model model,
                                  HttpServletRequest req){
        String fileName=null;
        String destFileName=null;

        try {
            //根据时间戳创建新的文件名(当前时间+原文件名)，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
            fileName = System.currentTimeMillis()+file.getOriginalFilename();
            //获取当前项目的真实路径，然后拼接前面的文件名
            /*destFileName=req.getServletContext().getRealPath("")+"uploaded"+File.separator+fileName;*/

            destFileName = System.getProperty("user.dir") + "/src/main/resources/static/img/upLoad/" + fileName;
            /*destFileName = System.getProperty("user.dir") + File.separator+
                    "src"+File.separator+"main"+File.separator+"resources"+
                    File.separator+"static"+File.separator+"img"+File.separator+
                    "upLoad"+File.separator + fileName;*/
//            destFileName=req.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");

            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            //把浏览器上传的文件复制到希望的位置
            file.transferTo(destFile);

            model.addAttribute("fileName",fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            return "上传失败," + e.getMessage();
            req.setAttribute("msg","上传失败");
            return "forward:/queryAndEdit/ModifyInformation";
        } catch (IOException e) {
            e.printStackTrace();
//            return "上传失败," + e.getMessage();
            req.setAttribute("msg","上传失败");
            return "forward:/queryAndEdit/ModifyInformation";
        }
        //设置图片路径
        backAdmin.setImg(fileName);

        //密码加密
        String pass=backAdmin.getPassword();
        MD5 md5=new MD5();
        backAdmin.setPassword(md5.getMD5(pass));

        //根据新密码为不为空在调用方法改（动态SQL）
        //旧密码已有异步判断是否正确，若正确则删除新密码的只读属性
        //System.out.println("password---"+backAdmin.getPassword());
       // System.out.println("phone---"+backAdmin.getPhone());
        //System.out.println("img---"+backAdmin.getImg());
        //System.out.println("service---"+backAdmin.getService());


        int i=service.saveInformation(backAdmin);
        if (i!=0){
            return "BackAdminIndex";
        }else return "forward:/queryAndEdit/queryPersonalInformation";

    }



}
