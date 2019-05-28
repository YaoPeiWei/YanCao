package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.pojo.Cigarette;
import com.example.demo.service.CigaretteService;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Controller
@RequestMapping("/Cigarette")
public class CigaretteController {
    @Resource
    private CigaretteService cigaretteService;
    @RequestMapping("/SelectCigarette")
    public String SelectCigarette(ModelMap model, @RequestParam(value = "index",defaultValue="1",required=false) int index, @RequestParam(value = "size",defaultValue="2",required=false)int size){
        System.out.println("开始执行查询烟草");
        List<Cigarette> cigarettes = cigaretteService.SelectCigarette((index-1)*size,size);
        int count=cigaretteService.getCount();
        int page;
        if(count%size==0){
            page=count/size;
        }else{
            page=count/size+1;
        }
        System.out.println("page:"+page);
        //System.out.println(cigarettes.size());
        model.addAttribute("cigarettes",cigarettes);
        model.addAttribute("count",page);
        model.addAttribute("index",index);
        return "UserIndex";
    }

    @ResponseBody
    @RequestMapping("/SelectCigaretteAjax")
    public HashMap SelectCigaretteAjax(@RequestParam(value = "index",defaultValue="1",required=false) int index, @RequestParam(value = "size",defaultValue="2",required=false)int size){
        System.out.println("开始执行SelectCigaretteAjax");
        HashMap map=new HashMap();
        List<Cigarette> cigarettes = cigaretteService.SelectCigarette((index-1)*size,size);
        int count=cigaretteService.getCount();
        //System.out.println(cigarettes.size());
        map.put("cigarettes",cigarettes);
//        map.put("count",count/size);
//        map.put("index",index);
        return map;
    }
    @RequestMapping("/listCategory")
    public String listCigarette(Model m) throws Exception {
        List<Cigarette> cs=cigaretteService.findCigarette();
        m.addAttribute("cs", cs);
        return "ListCigarette";
    }

    /*
    跳转到新增烟草页面
     */
    @RequestMapping("/add")
    public String Add() throws Exception{
        return "BackAdminAddCigarette";
    }
    /*
    增加烟草信息
     */
    @RequestMapping(value ="/addCigarette",method = RequestMethod.POST)
    public String AddCigarette(HttpServletRequest req, @RequestParam("file") MultipartFile file,
                               Cigarette cigarette, Model model) throws Exception{
        String fileName=null;
        String destFileName=null;
        try {
            fileName = System.currentTimeMillis() + file.getOriginalFilename();
//            String destFileName = req.getServletContext().getRealPath("") + "upLoadCigarette" + File.separator + fileName;
            destFileName = System.getProperty("user.dir") + "/src/main/resources/static/img/upLoadCigarette/" + fileName;
            //System.out.println(destFileName);
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            model.addAttribute("fileName", fileName);
            cigarette.setImg(fileName);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return "文件上传失败"+e.getMessage();
        }catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }
        if ( cigaretteService.saveCigarette(cigarette)>0){
//            System.out.println(cigarette.getImg());
//            System.out.println(cigarette.getAccount());
            return "redirect:../queryAndEdit/cig";
        }else
            return "redirect:add";
    }

    /*
    跳转到烟草修改页面，获得account，把cigarette加到Attribute,以便后续使用
     */
    @RequestMapping("/modify")
    public String Modify(String account, Model model
    ) throws Exception{
        Cigarette cigarette=cigaretteService.get(account);
        model.addAttribute("cigarette",cigarette);
        //System.out.println(cigarette.getImg());
        return "ModifyCigarette";
    }

    /*
    修改烟草信息
     */
    @RequestMapping(value = "/updateCigarette",method = RequestMethod.POST)
    public  String ModifyCigarette(HttpServletRequest req, @RequestParam("file") MultipartFile file,
                                   Cigarette cigarette, Model model) throws Exception{
        String fileName=null;
        String destFileName=null;
        try {
            fileName = System.currentTimeMillis() + file.getOriginalFilename();
            destFileName = System.getProperty("user.dir") + "/src/main/resources/static/img/upLoadCigarette/" + fileName;
            //System.out.println(destFileName);
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
            model.addAttribute("fileName", fileName);
            cigarette.setImg(fileName);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return "文件上传失败"+e.getMessage();
        }catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }
        if (cigaretteService.modifyCigarette(cigarette)>0){
            return "redirect:listCategory";
        }
        return "redirect:modify";
    }

    /*
    json方式检查Account是否可用

     */
    @RequestMapping(value = "check",method = RequestMethod.GET)
    @ResponseBody
    public String CheckAccount(@RequestParam String account){
        HashMap<String,String> map=new HashMap<String,String>();
        if(StringUtils.isNullOrEmpty(account)){
            map.put("account","empty");
        }else{
            Cigarette cigarette=null;
            try {
                cigarette = cigaretteService.get(account);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(cigarette!=null){
                map.put("account","exist");
            }else{
                map.put("account","noexist");
            }
        }
        return JSONArray.toJSONString(map);
    }

    @RequestMapping("/deleteCig")
    public String DeleteAccount(Cigarette cigarette) throws Exception{
        cigaretteService.deleteByAccount(cigarette.getAccount());
        return "redirect:../queryAndEdit/cig";
    }

}
