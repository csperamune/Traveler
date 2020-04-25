package com.example.traveler;

public class UserProfile {
    public String user_name;
    public String name;
    public String email;
    public String phone_no;
    public String password;

    public UserProfile(String user_name, String name, String email, String phone_no, String password) {
        this.user_name = user_name;
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.password = password;
    }
}
