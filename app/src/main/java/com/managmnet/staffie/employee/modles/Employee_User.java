package com.managmnet.staffie.employee.modles;

public class Employee_User {



    EmployeeDetails org_name;
    EmployeeDetails emp_id;
    EmployeeDetails emp_name;
    EmployeeDetails emp_phone;
    EmployeeDetails emp_email;
    EmployeeDetails emp_pass;
    int emp_pic;

    public Employee_User(EmployeeDetails org_name, EmployeeDetails emp_name, EmployeeDetails emp_phone, EmployeeDetails emp_email, EmployeeDetails emp_pass, int emp_pic) {
        this.org_name = org_name;
        this.emp_name = emp_name;
        this.emp_phone = emp_phone;
        this.emp_email = emp_email;
        this.emp_pass = emp_pass;
        this.emp_pic = emp_pic;
    }
    public Employee_User(){

    }

    //google
    public Employee_User(EmployeeDetails emp_id, EmployeeDetails emp_name, EmployeeDetails emp_phone, EmployeeDetails emp_email) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_phone = emp_phone;
        this.emp_email = emp_email;
    }

    //attendance

    public Employee_User(java.lang.String emp_name,int emp_pic)
    {

    }
//
//    public Employee_User(String emp_name, int emp_pic) {
//        this.emp_name = emp_name;
//        this.emp_pic = emp_pic;
//    }

    public Employee_User(java.lang.String uid, java.lang.String displayName, java.lang.String email, java.lang.String phoneNumber) {
    }



    public EmployeeDetails getOrg_name() {
        return org_name;
    }

    public void setOrg_name(EmployeeDetails org_name) {
        this.org_name = org_name;
    }

    public EmployeeDetails getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(EmployeeDetails emp_id) {
        this.emp_id = emp_id;
    }

    public EmployeeDetails getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(EmployeeDetails emp_name) {
        this.emp_name = emp_name;
    }

    public EmployeeDetails getEmp_phone() {
        return emp_phone;
    }

    public void setEmp_phone(EmployeeDetails emp_phone) {
        this.emp_phone = emp_phone;
    }

    public EmployeeDetails getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(EmployeeDetails emp_email) {
        this.emp_email = emp_email;
    }

    public EmployeeDetails getEmp_pass() {
        return emp_pass;
    }

    public void setEmp_pass(EmployeeDetails emp_pass) {
        this.emp_pass = emp_pass;
    }

    public int getEmp_pic() {
        return emp_pic;
    }

    public void setEmp_pic(int emp_pic) {
        this.emp_pic = emp_pic;
    }
}
