package com.example.rohit.testapplication.Model;

public class UserProfile {

    String name;
    String gender;
    String email;
    String age;
    String dob;
    String imgUrl;
    String phone;
    String cell;
    String location;

    public UserProfile(String name, String gender, String email, String age, String dob, String imgUrl, String phone, String cell, String location) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.dob = dob;
        this.imgUrl = imgUrl;
        this.phone = phone;
        this.cell = cell;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
