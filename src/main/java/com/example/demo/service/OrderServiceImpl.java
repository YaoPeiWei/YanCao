package com.example.demo.service;

import com.example.demo.dao.OrderMapper;
import com.example.demo.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{

    @Resource
    private OrderMapper orderMapper;
    @Override
    public List<Order> SelectOrder(String order,int idx,int sl,int state) {
        return orderMapper.SelectOrder(order,idx,sl,state);
    }

    @Override
    public List<Order> SelectOrders(String order, int idx, int sl) {
        return orderMapper.SelectOrders(order,idx,sl);
    }

    @Override
    public int AddOrder(Order order) {
        return orderMapper.OrderAdd(order);
    }

    @Override
    public Order SelectLastOrder() {
        return orderMapper.SelectLastOrder();
    }

    @Override
    public int UpdateCigaretteNumber(Order order) {
        return orderMapper.UpdateCigaretteNumber(order);
    }

    @Override
    public int DeleteOrderCigarette(String order, String cigarette) {
        return orderMapper.DeleteOrderCigarette(order,cigarette);
    }
}
