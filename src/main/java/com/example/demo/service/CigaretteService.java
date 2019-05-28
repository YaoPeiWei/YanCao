package com.example.demo.service;

import com.example.demo.pojo.Cigarette;

import java.util.List;

public interface CigaretteService {
    public List<Cigarette> SelectCigarette(int index,int size);
    public int getCount();
    List<Cigarette> findCigarette();
    //新增烟草
    public int saveCigarette(Cigarette cigarette);
    //修改烟草
    public int modifyCigarette(Cigarette cigarette);
    //通过Account获取cigarette信息
    public Cigarette get(String account);
    public void deleteByAccount(String account);
    public int UpdateCigaretteSurplus(int surplus,String account);
}
