package com.managmnet.staffie.employee.modles;

public class User_Employee {
    String emp_id,emp_name,emp_attendance_date;
    int emp_pic;

    public User_Employee(String emp_id, String emp_name, int emp_pic, String emp_attendance_date) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_pic = emp_pic;
        this.emp_attendance_date=emp_attendance_date;
    }

    public User_Employee(){

    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_attendance_date() {
        return emp_attendance_date;
    }

    public void setEmp_attendance_date(String emp_attendance_date) {
        this.emp_attendance_date = emp_attendance_date;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public int getEmp_pic() {
        return emp_pic;
    }

    public void setEmp_pic(int emp_pic) {
        this.emp_pic = emp_pic;
    }
}
