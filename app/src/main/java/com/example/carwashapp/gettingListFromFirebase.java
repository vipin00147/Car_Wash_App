package com.example.carwashapp;

public class gettingListFromFirebase {
    String center_name, addess, opening_hours, phone_no, pin_code, image;

    public gettingListFromFirebase() {

    }

    public gettingListFromFirebase(String center_name, String addess, String opening_hours, String phone_no, String pin_code, String image) {
        this.center_name = center_name;
        this.addess = addess;
        this.opening_hours = opening_hours;
        this.phone_no = phone_no;
        this.pin_code = pin_code;
        this.image = image;
    }

    public String getCenter_name() {
        return center_name;
    }

    public String getAddess() {
        return addess;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getPin_code() {
        return pin_code;
    }

    public String getImage() {
        return image;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public void setAddess(String addess) {
        this.addess = addess;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
