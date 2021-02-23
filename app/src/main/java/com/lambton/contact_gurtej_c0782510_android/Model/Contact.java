package com.lambton.contact_gurtej_c0782510_android.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int contact_id;
    private String fname;
    private String lname;
    private String email;
    private long number;
    private String address;

    public Contact(String fname, String lname, String email, long number, String address) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.number = number;
        this.address = address;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}