package com.managmnet.staffie.manager.models;

public class Manager_User {

    String org_name,mng_id,mng_name,mng_phone,mng_email,mng_pass,mng_attendance_date;
    int mng_pic;

    public Manager_User(String org_name, String mng_name, String mng_phone, String mng_email, String mng_pass) {
        this.org_name = org_name;
        this.mng_name = mng_name;
        this.mng_phone = mng_phone;
        this.mng_email = mng_email;
        this.mng_pass = mng_pass;
    }

    public String getMng_attendance_date() {
        return mng_attendance_date;
    }

    public void setMng_attendance_date(String mng_attendance_date) {
        this.mng_attendance_date = mng_attendance_date;
    }

    //google
    public Manager_User(String mng_id,String mng_name, String mng_phone, String mng_email) {

        this.mng_name = mng_name;
        this.mng_id = mng_id;
        this.mng_phone = mng_phone;
        this.mng_email = mng_email;
    }

    // for admin attendace

    public Manager_User(String mng_id,String mng_name, int mng_pic,String mng_attendance_date) {
        this.mng_id = mng_id;
        this.mng_name = mng_name;
        this.mng_pic = mng_pic;
        this.mng_attendance_date=mng_attendance_date;
    }

    public Manager_User(){

    }



    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getMng_id() {
        return mng_id;
    }

    public void setMng_id(String mng_id) {
        this.mng_id = mng_id;
    }

    public String getMng_name() {
        return mng_name;
    }

    public void setMng_name(String mng_name) {
        this.mng_name = mng_name;
    }

    public String getMng_phone() {
        return mng_phone;
    }

    public void setMng_phone(String mng_phone) {
        this.mng_phone = mng_phone;
    }

    public String getMng_email() {
        return mng_email;
    }

    public void setMng_email(String mng_email) {
        this.mng_email = mng_email;
    }

    public String getMng_pass() {
        return mng_pass;
    }

    public void setMng_pass(String mng_pass) {
        this.mng_pass = mng_pass;
    }

    public int getMng_pic() {
        return mng_pic;
    }

    public void setMng_pic(int mng_pic) {
        this.mng_pic = mng_pic;
    }
}

