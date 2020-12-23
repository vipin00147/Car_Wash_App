package com.example.carwashapp;

public class CurrentUser {
    public static String phone;
    public static String center_name;
    public static String center_image;

    public CurrentUser(String phone) {
        this.phone = phone;
    }
    public CurrentUser() {
        this.phone = phone;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        CurrentUser.phone = phone;
    }

    public static String getCenter_name() {
        return center_name;
    }

    public static void setCenter_name(String center_name) {
        CurrentUser.center_name = center_name;
    }

    public static String getCenter_image() {
        return center_image;
    }

    public static void setCenter_image(String center_image) {
        CurrentUser.center_image = center_image;
    }
}
