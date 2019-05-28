package com.example.demo.service;

import com.example.demo.dao.CheckAndModifyMapper;
import com.example.demo.pojo.BackAdmin;
import com.example.demo.pojo.Cigarette;
import com.example.demo.pojo.UserBuy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckAndModifyServiceImpl implements CheckAndModifyService {

    @Resource
    private CheckAndModifyMapper mapper;

    @Override
    public List<Cigarette> findCigarette() {
        List<Cigarette> cigaretteList=mapper.getCigarette();

        return cigaretteList;
    }

    @Override
    public Cigarette findCig(String account) {
        Cigarette cigarette=mapper.getCig(account);
        return cigarette;
    }

    @Override
    public int saveCigarette(Cigarette cigarette) {
        int i=mapper.setCigarette(cigarette);
        return i;
    }


    @Override
    public List<UserBuy> findUserBuyByLocation(int location) {
        List<UserBuy> userBuyList=mapper.getUserBuyByLocation(location);
        return userBuyList;
    }

    @Override
    public BackAdmin findBackAdmin(String account,int location) {
        BackAdmin backAdmin=null;

        System.out.println("account,location----"+account+"------"+location);
        backAdmin=mapper.getBackadmin(account,location);

        return backAdmin;
    }

    @Override
    public String findOldPassword(String account, int location) {

        String oldPassword=mapper.getOldPassword(account,location);

        return oldPassword;
    }

    @Override
    public int saveInformation(BackAdmin backAdmin) {
        int i=mapper.setInformation(backAdmin);

        return i;
    }

    @Override
    public int updateUserBuyState(String user,String order) {
        int i=mapper.updateUserBuyByUser(user,order);
        return i;
    }

    @Override
    public int findUnprocessedInformation(int location) {
        int i=mapper.getCountUnProcessed(location);

        return i;
    }
}
