package com.example.demo.dao;

import com.example.demo.pojo.UserBuy;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserBuyMapper {
    @Insert("INSERT INTO yancao.`userbuy`(yancao.`userbuy`.`user`,yancao.`userbuy`.`location`,yancao.`userbuy`.`order`,yancao.`userbuy`.`state`,yancao.`userbuy`.`address`,yancao.`userbuy`.`phone`) " +
            "VALUES(#{userbuy.user},#{userbuy.location},#{userbuy.order},0,#{userbuy.address},#{userbuy.phone})")
    public int InsertUserBuy(@Param("userbuy") UserBuy userbuy);
    @Update("UPDATE yancao.`userbuy` SET yancao.`userbuy`.`address`= #{address},yancao.`userbuy`.`phone`=#{phone} , yancao.`userbuy`.`state`=1 ,  yancao.`userbuy`.`price`=#{price}" +
            "WHERE yancao.`userbuy`.`user`=#{user} and yancao.`userbuy`.`order`=#{order} and yancao.`userbuy`.`state`=0")
    public int UpdateUserBuy(@Param("address") String address, @Param("phone") String phone, @Param("user") String user, @Param("order") String order,@Param("price") double price);

    @Update("UPDATE yancao.`userbuy` SET yancao.`userbuy`.`order`=#{order} WHERE yancao.`userbuy`.`user`=#{user}")
    public int UpdateUserBuyOrderNull(@Param("user") String user, @Param("order") String order);

    @Select("SELECT * FROM yancao.`userbuy` WHERE yancao.`userbuy`.`user`=#{user} AND (yancao.`userbuy`.`state`=#{state} or #{state1})")
    public List<UserBuy> selectUserBuyListWhereUser(@Param("user") String user,@Param("state") int state,@Param("state1") int state1);

    @Select("SELECT * FROM yancao.`userbuy` WHERE yancao.`userbuy`.`user`=#{user} AND yancao.`userbuy`.`state`=#{state}")
    public List<UserBuy> selectUserBuyListWhereUserAndStateEQ0(@Param("user") String user,@Param("state") int state);
}
