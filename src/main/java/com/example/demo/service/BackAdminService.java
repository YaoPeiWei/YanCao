package com.example.demo.service;

import com.example.demo.pojo.BackAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IMChiLe on 2019/4/8 11:39
 */
public interface BackAdminService {
    //查询账户是否存在
    public String selectAdmin(String s1, String account) throws Exception;

    //查询账户是否有登陆权限
    public int selectAdminService(String s1, String account) throws Exception;

    //注册业务员
    public int insertAdmin(String s1, String account, String name, String password,
                           String phone, String img, int sex, String entrytime) throws Exception;

    //登陆验证
    public BackAdmin loginAdmin(String s1, String account, String password) throws Exception;

    //查询所有业务员
    public List<BackAdmin> selectAllAdmin()throws Exception;

    //取消业务员在职状态
    public int updateService(String s1, String account)throws Exception;

    //恢复业务员在职状态
    public int updateService1(String s1, String account)throws Exception;
}
