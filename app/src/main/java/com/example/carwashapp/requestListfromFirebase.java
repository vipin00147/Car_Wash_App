package com.example.carwashapp;

public class requestListfromFirebase {
    String center_name, amount, appointment_date, center_image, mobile, name;

    public requestListfromFirebase(String center_name, String amount, String appointment_date, String center_image, String mobile, String name) {
        this.center_name = center_name;
        this.amount = amount;
        this.appointment_date = appointment_date;
        this.center_image = center_image;
        this.mobile = mobile;
        this.name = name;
    }

    public requestListfromFirebase() {
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getCenter_image() {
        return center_image;
    }

    public void setCenter_image(String center_image) {
        this.center_image = center_image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
