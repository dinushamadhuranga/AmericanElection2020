package com.iit.model;

import java.sql.Date;

public class Voter {
    private int voter_id;
    private String name ;
    private String address;
    private Date dob;
    private String email;
    private String nic;
    private String gender;
    private String province;
    private String city;
    private Boolean isvoted;

    public Voter() {
    }

    public Voter(String name, String address, Date dob, String email, String nic, String gender, String province, String city) {
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.nic = nic;
        this.gender = gender;
        this.province = province;
        this.city = city;
    }

    public Voter(int voter_id, String name, String address, Date dob, String email, String nic, String gender, String province, String city) {
        this.voter_id = voter_id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.nic = nic;
        this.gender = gender;
        this.province = province;
        this.city = city;
    }

    public Voter(int voter_id, String name, String address, Date dob, String email, String nic, String gender, String province, String city, Boolean isvoted) {
        this.voter_id = voter_id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.nic = nic;
        this.gender = gender;
        this.province = province;
        this.city = city;
        this.isvoted = isvoted;
    }

    public int getVoter_id() {
        return voter_id;
    }

    public void setVoter_id(int voter_id) {
        this.voter_id = voter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIsvoted() {
        return isvoted;
    }

    public void setIsvoted(Boolean isvoted) {
        this.isvoted = isvoted;
    }
}
