package com.example.carwashapp;

import android.graphics.Path;

public class Users {
    String name,email,phone,password,image;
    String center_name, Phone_no, Addess, Opening_hours, Pin_code;
    Users(String name, String email, String password, String phone,String image,String x){
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.image = image;
    }

    public Users() {
    }

    Users(String center_name, String Phone_no, String Addess, String Opening_hours, String Pin_code){
        this.center_name = center_name;
        this.Phone_no = Phone_no;
        this.Addess = Addess;
        this.Opening_hours = Opening_hours;
        this.Pin_code = Pin_code;
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddess() {
        return Addess;
    }

    public String getCenter_name() {
        return center_name;
    }

    public String getOpening_hours() {
        return Opening_hours;
    }

    public String getPin_code() {
        return Pin_code;
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setAddess(String addess) {
        Addess = addess;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public void setOpening_hours(String opening_hours) {
        Opening_hours = opening_hours;
    }

    public void setPin_code(String Pin_code) {
        Pin_code = Pin_code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
