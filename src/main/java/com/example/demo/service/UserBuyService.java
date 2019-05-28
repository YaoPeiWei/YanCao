package com.example.demo.service;

import com.example.demo.pojo.UserBuy;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserBuyService {
    public int InsertUserBuy(UserBuy userBuy);
    public int UpdateUserBuy(String address, String phone, String user, String order,double price);
    public int UpdateUserBuyOrderNull(String user, String order);
    public List<UserBuy> selectUserBuyListWhereUser(String user,int state,int state1);
    public List<UserBuy> selectUserBuyListWhereUserAndStateEQ0(String user,int state);
}
