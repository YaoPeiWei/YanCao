package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IMChiLe on 2019/4/16 10:20
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    @Resource
    private UserMapper userMapper;


    @Override
    public User selectUserPassword(String name) throws Exception {
        return userMapper.selectUserPassword(name);
    }

    //判断是否存在user
    @Override
    public String selectUserName(String name) throws Exception {
        return userMapper.selectUserName(name);
    }
    //账户ID登陆判断
    @Override
    public User userLogin(String name, String password) throws Exception {
        return userMapper.userLogin(name,password);
    }

    //手机号码登陆判断
    @Override
    public User userLoginPhone(String phone, String password) throws Exception {
        return userMapper.userLoginPhone(phone,password);
    }

    //注册用户
    @Override
    public int insertUser(String name, String password, int location, String phone) throws Exception {
        return userMapper.insertUser(name,password,location,phone);
    }

    @Override
    public String selectUser(String name) throws Exception {
        return userMapper.selectUser(name);
    }

    @Override
    public String selectUserPhone(String phone) throws Exception {
        return userMapper.selectUserPhone(phone);
    }

}
