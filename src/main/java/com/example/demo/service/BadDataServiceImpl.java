package com.example.demo.service;

import com.example.demo.dao.BadDataMapper;
import com.example.demo.pojo.BadData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BadDataServiceImpl implements BadDataService{
    @Resource
    private BadDataMapper mapper;
    @Override
    public List<BadData> GetBadData() {
        return mapper.GetBadData();
    }
}
