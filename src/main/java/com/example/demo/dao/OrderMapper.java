package com.example.demo.dao;

import com.example.demo.pojo.Order;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO yancao.`order`(yancao.`order`.`order`,yancao.`order`.`cigarette`,yancao.`order`.`number`) VALUES(#{order.order},#{order.cigarette},#{order.number})")
    public int  OrderAdd(@Param("order") Order order);
    //@Select("SELECT * FROM yancao.`order` WHERE yancao.`order`.`order`=#{order}")

    @Select("SELECT o.`order`,c.`name`,o.`number`,o.cigarette,c.`price` FROM yancao.`order` o, yancao.cigarette c,yancao.userbuy u " +
            "WHERE o.`order`=#{order} AND c.`account`=o.`cigarette` AND o.`order`=u.`order` AND u.`state`=#{state} limit #{idx},#{sl}")
    public List<Order> SelectOrder(@Param("order") String order, @Param("idx") int idx, @Param("sl") int sl, @Param("state") int state);

    @Select("SELECT o.`order`,c.`name`,o.`number`,o.cigarette,c.`price` FROM yancao.`order` o, yancao.cigarette c\n" +
            "WHERE o.`order`=#{order} AND c.`account`=o.`cigarette` LIMIT #{idx},#{sl}")
    public List<Order> SelectOrders(@Param("order") String order, @Param("idx") int idx, @Param("sl") int sl);
//    @Select("SELECT o.`order`,c.`name`,o.`number`,o.cigarette,c.`price` FROM yancao.`order` o, yancao.cigarette c,yancao.userbuy u " +
//            "WHERE o.`order`=#{order} AND c.`account`=o.`cigarette` AND o.`order`=u.`order` AND u.`state`=0 limit #{idx},#{sl}")
//    public List<Order> SelectOrders(@Param("order") String order, @Param("idx") int idx, @Param("sl") int sl);

    @Select("SELECT * FROM yancao.`order` WHERE yancao.`order`.`order` ORDER BY yancao.`order`.`id` DESC LIMIT 1")
    public Order SelectLastOrder();//用于查询订单最后一条记录

    @Update("UPDATE yancao.`order` SET yancao.`order`.`number`=#{order.number} WHERE yancao.`order`.`order`=#{order.order} AND  yancao.`order`.`cigarette`=#{order.cigarette}")
    public int UpdateCigaretteNumber(@Param("order") Order order);

    @Delete("DELETE FROM yancao.`order` WHERE yancao.`order`.`order`=#{order} AND yancao.`order`.`cigarette`=#{cigarette}")
    public int DeleteOrderCigarette(@Param("order") String order, @Param("cigarette") String cigarette);
}
