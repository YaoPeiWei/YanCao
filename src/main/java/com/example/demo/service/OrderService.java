package com.example.demo.service;

import com.example.demo.pojo.Order;

import java.util.List;

public interface OrderService {
    public List<Order> SelectOrder(String order, int idx, int sl,int state);
    public List<Order> SelectOrders(String order, int idx, int sl);
    public int AddOrder(Order order);
    public Order SelectLastOrder();
    public int UpdateCigaretteNumber(Order order);
    public int DeleteOrderCigarette(String order, String cigarette);
}
