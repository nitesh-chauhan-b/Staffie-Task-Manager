package com.managmnet.staffie.admin.models;

public class Admin_User {
    String  org_name;
    String org_address;
    String org_country;
    String org_city;


    //Signup Constructor
    public Admin_User(String org_name, String org_address, String org_country,
                      String org_city, String org_pincode, String adm_name,
                      String adm_phone, String adm_email, String adm_pass) {
        this.org_name = org_name;
        this.org_address = org_address;
        this.org_country = org_country;
        this.org_city = org_city;
        this.org_pincode = org_pincode;
        this.adm_name = adm_name;
        this.adm_phone = adm_phone;
        this.adm_email = adm_email;
        this.adm_pass = adm_pass;
    }
    public Admin_User()
    {

    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_address() {
        return org_address;
    }

    public void setOrg_address(String org_address) {
        this.org_address = org_address;
    }

    public String getOrg_country() {
        return org_country;
    }

    public void setOrg_country(String org_country) {
        this.org_country = org_country;
    }

    public String getOrg_city() {
        return org_city;
    }

    public void setOrg_city(String org_city) {
        this.org_city = org_city;
    }

    public String getOrg_pincode() {
        return org_pincode;
    }

    public void setOrg_pincode(String org_pincode) {
        this.org_pincode = org_pincode;
    }

    public String getAdm_name() {
        return adm_name;
    }

    public void setAdm_name(String adm_name) {
        this.adm_name = adm_name;
    }

    public String getAdm_phone() {
        return adm_phone;
    }

    public void setAdm_phone(String adm_phone) {
        this.adm_phone = adm_phone;
    }

    public String getAdm_email() {
        return adm_email;
    }

    public void setAdm_email(String adm_email) {
        this.adm_email = adm_email;
    }

    public String getAdm_pass() {
        return adm_pass;
    }

    public void setAdm_pass(String adm_pass) {
        this.adm_pass = adm_pass;
    }

    public String getAdm_pic() {
        return adm_pic;
    }

    public void setAdm_pic(String adm_pic) {
        this.adm_pic = adm_pic;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    String org_pincode;
    String adm_name;
    String adm_phone;
    String adm_email;
    String adm_pass;

    String adm_pic,adminId;

    public Admin_User(String org_name, String org_address, String org_country, String org_city,
                      String org_pincode, String adm_name, String adm_phone, String adm_email,
                      String adm_pass, String adm_pic, String adminId) {
        this.org_name = org_name;
        this.org_address = org_address;
        this.org_country = org_country;
        this.org_city = org_city;
        this.org_pincode = org_pincode;
        this.adm_name = adm_name;
        this.adm_phone = adm_phone;
        this.adm_email = adm_email;
        this.adm_pass = adm_pass;
        this.adm_pic = adm_pic;
        this.adminId = adminId;
    }

    public Admin_User(String adminId,String adm_name, String adm_phone, String adm_email) {
        this.adm_name = adm_name;
        this.adminId = adminId;
        this.adm_phone = adm_phone;
        this.adm_email = adm_email;
    }
}
