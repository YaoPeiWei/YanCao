package com.example.demo.service;

import com.example.demo.dao.BackAdminMapper;
import com.example.demo.pojo.BackAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IMChiLe on 2019/4/8 11:39
 */
@Service
public class BackAdminServiceImpl implements BackAdminService {

    @Autowired
    @Resource
    private BackAdminMapper backAdminMapper;

    @Override
    public String selectAdmin(String s1, String account) throws Exception {
        return backAdminMapper.selectAdmin(s1,account);
    }

    @Override
    public int selectAdminService(String s1, String account) throws Exception {
        return backAdminMapper.selectAdminService(s1,account);
    }

    @Override
    public int insertAdmin(String s1, String account, String name, String password, String phone, String img, int sex, String entrytime) throws Exception {
        return backAdminMapper.insertAdmin(s1,account,name,password,phone,img,sex,entrytime);
    }

    @Override
    public BackAdmin loginAdmin(String s1, String account, String password) throws Exception {
        return backAdminMapper.loginAdmin(s1,account,password);
    }

    @Override
    public List<BackAdmin> selectAllAdmin() throws Exception {
        return backAdminMapper.selectAllAdmin();
    }

    @Override
    public int updateService(String s1, String account) throws Exception {
        return backAdminMapper.updateService(s1,account);
    }

    @Override
    public int updateService1(String s1, String account) throws Exception {
        return backAdminMapper.updateService1(s1,account);
    }
}
