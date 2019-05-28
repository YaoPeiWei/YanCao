package com.example.demo.service;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by IMChiLe on 2019/4/16 10:20
 */
public interface UserService {
    public User selectUserPassword(String name)throws Exception;
    public String selectUserName(String name) throws Exception;
    public User userLogin(String name,String password) throws Exception;
    public User userLoginPhone(String phone,String password)throws Exception;
    public int insertUser(String name,String password,int location,String phone) throws Exception;
    public String selectUser(String name) throws Exception;
    public String selectUserPhone(String phone) throws Exception;

}
