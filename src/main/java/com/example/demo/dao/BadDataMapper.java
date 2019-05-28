package com.example.demo.dao;

import com.example.demo.pojo.BadData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BadDataMapper {
    @Select("SELECT * FROM yancao.`baddata`")
    public List<BadData> GetBadData();
}
