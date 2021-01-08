package com.iit.model;

import java.sql.Date;

public class Candidate {
    private int candidate_ID;
    private String first_name;
    private String second_name;
    private Date dob;
    private String email;
    private String nic;
    private String religion;
    private String gender;
    private String party;
    private int votes;
    private int contact;

    public Candidate() {
    }

    public Candidate( String first_name, String second_name, Date dob, String email, String nic, String religion, String gender, String party, int contact) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.dob = dob;
        this.email = email;
        this.nic = nic;
        this.religion = religion;
        this.gender = gender;
        this.party = party;
        this.contact = contact;
    }

    public Candidate(int candidate_ID , String first_name, String second_name, Date dob, String email, String nic, String religion, String gender, String party, int contact) {
        this.candidate_ID = candidate_ID;
        this.first_name = first_name;
        this.second_name = second_name;
        this.dob = dob;
        this.email = email;
        this.nic = nic;
        this.religion = religion;
        this.gender = gender;
        this.party = party;
        this.contact = contact;
    }

    public Candidate(int candidate_ID, String first_name, String second_name, Date dob, String email, String nic, String religion, String gender, String party, int votes, int contact) {
        this.candidate_ID = candidate_ID;
        this.first_name = first_name;
        this.second_name = second_name;
        this.dob = dob;
        this.email = email;
        this.nic = nic;
        this.religion = religion;
        this.gender = gender;
        this.party = party;
        this.votes = votes;
        this.contact = contact;
    }

    public int getCandidate_ID() {
        return candidate_ID;
    }

    public void setCandidate_ID(int candidate_ID) {
        this.candidate_ID = candidate_ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
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

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }
}
