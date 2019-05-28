package com.example.demo.pojo;

/**
 * Created by IMChiLe on 2019/4/8 11:04
 */
public class BackAdmin {
    private String account;//账户ID
    private String name;//真实姓名
    private String password;//账户密码
    private int sex;//性别
    private String phone;//电话
    private int location;//工作地区
    private String img;//头像图片
    private String entrytime;//入职时间
    private int service;//是否在职

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(String entrytime) {
        this.entrytime = entrytime;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

}
