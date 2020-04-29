package com.example.traveler;

public class UserProfile {
    public String user_name;
    public String name;
    public String email;
    public String phone_no;
    public String password;

    public UserProfile(){

    }

    public UserProfile(String user_name, String name, String email, String phone_no, String password) {
        this.user_name = user_name;
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
