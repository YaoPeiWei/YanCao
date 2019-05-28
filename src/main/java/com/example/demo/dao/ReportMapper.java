package com.example.demo.dao;

import com.example.demo.pojo.Report;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportMapper {
    @Insert("INSERT INTO report(yancao.`report`.`name`,neirong,reportdate) VALUES(#{r.name},#{r.neirong},#{r.reportdate})")
    public int InsertReport(@Param("r") Report report) throws Exception;

    @Select("select *  from report")
    public List<Report> selectReport() throws Exception;
}
