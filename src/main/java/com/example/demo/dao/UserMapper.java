package com.example.demo.dao;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by IMChiLe on 2019/4/16 9:52
 */
@Mapper
public interface UserMapper {

    //是否有该用户返回user
    @Select("select * from user where name=#{name} or phone=#{name}")
    public User selectUserPassword(@Param(value="name") String name)throws Exception;
    //是否有该用户
    @Select("select name from user where name = #{name} or phone = #{name}")
    public String selectUserName(@Param(value = "name") String name) throws Exception;

    //账户ID登陆验证
    @Select("select * from user where name = #{name} and password = #{password}")
    public User userLogin(@Param(value = "name") String name,@Param(value = "password") String password)throws Exception;

    //手机号码登陆验证
    @Select("select * from user where phone = #{phone} and password = #{password}")
    public User userLoginPhone(@Param(value = "phone") String phone,@Param(value = "password") String password)throws Exception;

    //注册用户
    @Insert("insert into user(name,password,location,phone) values(#{name},#{password},#{location},#{phone})")
    public int insertUser(@Param(value = "name")String name,
                          @Param(value = "password")String password,
                          @Param(value = "location") int location,
                          @Param(value = "phone") String phone)throws Exception;

    //判断是否存在相同ID
    @Select("select name from user where name = #{name}")
    public String selectUser(@Param(value = "name") String name) throws Exception;

    //判断是否存在相同手机号码
    @Select("select name from user where phone = #{phone}")
    public String selectUserPhone(@Param(value = "phone") String phone) throws Exception;
}
