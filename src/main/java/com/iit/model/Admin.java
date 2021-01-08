package com.iit.model;

public class Admin {
    private String user_name;
    private String user_password;
    private String fullname;
    private String email;

    public Admin() {
    }

    public Admin(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public Admin(String user_name, String user_password, String fullname, String email) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.fullname = fullname;
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
