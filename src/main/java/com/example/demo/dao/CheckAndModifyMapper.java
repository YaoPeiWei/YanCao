package com.example.demo.dao;

import com.example.demo.pojo.BackAdmin;
import com.example.demo.pojo.Cigarette;
import com.example.demo.pojo.UserBuy;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CheckAndModifyMapper {

    @Select("select * from cigarette")
    public List<Cigarette> getCigarette();

    @Select("select * from cigarette where account = #{account}")
    public Cigarette getCig(String account);

    @Update("<script>"
            + "UPDATE cigarette "
            + "<set>"
            + "<if test='#{name} != null'>name = #{name}, </if>"
            + "<if test='#{surplus} != null'>surplus = #{surplus}, </if>"
            + "<if test='#{img} != null'>img = #{img}, </if>"
            + "<if test='#{cumulative} != null'>cumulative = #{cumulative}, </if>"
            + "<if test='#{price} != null'>price = #{price}, </if>"
            + "</set>"
            + "WHERE account = #{account};"
            + "</script>")
    public int setCigarette(Cigarette cigarette);

    @Select("select * from userbuy where location = #{location}")
    public List<UserBuy> getUserBuyByLocation(int location);

    @Select("select * from #{location}backadmin where account = #{account}")
    public BackAdmin getBackadmin(@Param(value = "account") String account, @Param(value = "location") int location);


    /*根据不同地区的用户id查询旧密码*/
    @Select("select password from #{location}backadmin where account = #{account}")
    public String getOldPassword(@Param(value = "account") String account, @Param(value = "location") int location);

    /*将userbuy表的state状态从1（支付未处理）改成2（已处理）*/
    @Update("update userbuy set state=2 where user = #{user} and userbuy.order = #{order}")
    public int updateUserBuyByUser(@Param(value = "user") String user,@Param(value = "order")String order);

    @Update("<script>"
            + "UPDATE #{location}backadmin "
            + "<set>"
            + "<if test='#{password} != null'>password = #{password}, </if>"
            + "<if test='#{phone} != null'>phone = #{phone}, </if>"
            + "<if test='#{img} != null'>img = #{img}, </if>"
            + "<if test='#{service} != null'>service = #{service}, </if>"
            + "<if test='#{sex} != null'>sex = #{sex}, </if>"
            + "</set>"
            + "WHERE account = #{account};"
            + "</script>")
    public int setInformation(BackAdmin backAdmin);

    @Select("select count(*) from userbuy where location = #{location} and state=1")
    public int getCountUnProcessed(int location);


        }
