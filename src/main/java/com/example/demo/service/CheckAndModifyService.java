package com.example.demo.service;

import com.example.demo.pojo.BackAdmin;
import com.example.demo.pojo.Cigarette;
import com.example.demo.pojo.UserBuy;

import java.util.List;

public interface CheckAndModifyService {

    public List<Cigarette> findCigarette();

    public Cigarette findCig(String account);

    public int saveCigarette(Cigarette cigarette);

    public List<UserBuy> findUserBuyByLocation(int location);

    public BackAdmin findBackAdmin(String account, int location);

    public String findOldPassword(String account, int location);

    public int saveInformation(BackAdmin backAdmin);

    public int updateUserBuyState(String user,String order);

    public int findUnprocessedInformation(int location);

}
