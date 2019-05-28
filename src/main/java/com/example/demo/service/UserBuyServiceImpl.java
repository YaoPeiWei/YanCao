package com.example.demo.service;

import com.example.demo.dao.UserBuyMapper;
import com.example.demo.pojo.UserBuy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserBuyServiceImpl implements UserBuyService {
    @Resource
    private UserBuyMapper mapper;
    @Override
    public int InsertUserBuy(UserBuy userBuy) {
        return mapper.InsertUserBuy(userBuy);
    }

    @Override
    public int UpdateUserBuy(String address, String phone, String user,String order,double price) {
        return mapper.UpdateUserBuy(address,phone,user,order,price);
    }

    @Override
    public int UpdateUserBuyOrderNull(String user,String order) {
        return mapper.UpdateUserBuyOrderNull(user,order);
    }

    @Override
    public List<UserBuy> selectUserBuyListWhereUser(String user, int state,int state1) {
        return mapper.selectUserBuyListWhereUser(user,state,state1);
    }

    @Override
    public List<UserBuy> selectUserBuyListWhereUserAndStateEQ0(String user, int state) {
        return mapper.selectUserBuyListWhereUserAndStateEQ0(user,state);
    }
}
