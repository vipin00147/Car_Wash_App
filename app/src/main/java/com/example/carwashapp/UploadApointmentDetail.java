package com.example.carwashapp;

public class UploadApointmentDetail {
    String name,mobile,email,amount,Appointment_date, center_name, center_image,status_code;

    public UploadApointmentDetail(String name, String mobile, String email, String amount, String appointment_date, String center_name, String center_image,String status_code) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.amount = amount;
        Appointment_date = appointment_date;
        this.center_name = center_name;
        this.center_image = center_image;
        this.status_code = status_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAppointment_date() {
        return Appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        Appointment_date = appointment_date;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getCenter_image() {
        return center_image;
    }

    public void setCenter_image(String center_image) {
        this.center_image = center_image;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }
}
