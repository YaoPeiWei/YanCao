package com.example.demo.dao;

import com.example.demo.pojo.BackAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by IMChiLe on 2019/4/8 11:37
 */
@Mapper
public interface BackAdminMapper {

    //查询账户是否存在
    @Select("select account from ${s1} where account = #{account}")
    public String selectAdmin(@Param("s1") String s1, @Param("account") String account) throws Exception;

    //查询账户是否有登陆权限
    @Select("select service from ${s1} where account = #{account}")
    public int selectAdminService(@Param("s1") String s1, @Param("account") String account) throws Exception;


    //注册业务员
    @Insert("insert into ${s1}(account,name,password,phone,img,sex,entrytime)" +
            " values (#{account},#{name},#{password},#{phone},#{img},#{sex},#{entrytime})")
    public int insertAdmin(@Param("s1") String s1, @Param("account") String account,
                           @Param("name") String name, @Param("password") String password,
                           @Param("phone") String phone, @Param("img") String img,
                           @Param("sex") int sex,
                           @Param("entrytime") String entrytime) throws Exception;

    //登陆验证
    @Select("select * from ${s1} where account = #{account} and password = #{password}")
    public BackAdmin loginAdmin(@Param("s1") String s1, @Param("account") String account, @Param("password") String password) throws Exception;

    //查询所有业务员
    @Select("select * from 0backadmin " +
            " UNION SELECT * FROM 1backadmin " +
            "UNION SELECT * from 2backadmin " +
            "union SELECT * FROM 3backadmin ")
    public List<BackAdmin> selectAllAdmin()throws Exception;

    //取消业务员在职状态
    @Update("update ${s1} set service = '0' where account = #{account}")
    public int updateService(@Param("s1") String s1, @Param("account") String account) throws Exception;

    //恢复业务员在职状态
    @Update("update ${s1} set service = '1' where account = #{account}")
    public int updateService1(@Param("s1") String s1, @Param("account") String account) throws Exception;
}
