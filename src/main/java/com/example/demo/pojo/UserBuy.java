package com.example.demo.pojo;

public class UserBuy {
    private String user;
    private String address;
    private String phone;
    private int state;
    private int location;
    private String order;

    public UserBuy() {
    }

    public UserBuy(String user, String address, String phone, int state, int location, String order) {
        this.user = user;
        this.address = address;
        this.phone = phone;
        this.state = state;
        this.location = location;
        this.order = order;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
