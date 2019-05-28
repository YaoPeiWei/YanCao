package com.example.demo.service;

import com.example.demo.dao.CigaretteMapper;
import com.example.demo.pojo.Cigarette;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CigaretteServiceImpl implements CigaretteService {
    @Resource
    private CigaretteMapper cigaretteMapper;
    @Override
    public List<Cigarette> SelectCigarette(int index,int size) {
        return cigaretteMapper.SelectCigarette(index,size);
    }

    @Override
    public int getCount() {
        return cigaretteMapper.getCount();
    }

    @Override
    public List<Cigarette> findCigarette() {
        return cigaretteMapper.findCigarette();
    }

    @Override
    public int saveCigarette(Cigarette cigarette) {
        return cigaretteMapper.saveCigarette(cigarette);
    }

    @Override
    public int modifyCigarette(Cigarette cigarette) {
        return cigaretteMapper.modifyCigarette(cigarette);
    }

    @Override
    public Cigarette get(String account) {
        return cigaretteMapper.get(account);
    }

    @Override
    public void deleteByAccount(String account) {
        cigaretteMapper.deleteByAccount(account);
    }

    @Override
    public int UpdateCigaretteSurplus(int surplus, String account) {
        return cigaretteMapper.UpdateCigaretteSurplus(surplus, account);
    }
}
