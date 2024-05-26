package com.managmnet.staffie.employee.modles;

public class Attendance_model {

    String a_date,a_status;

    public Attendance_model(String a_date, String a_status) {
        this.a_date = a_date;
        this.a_status = a_status;
    }

    public Attendance_model(){

    }

    public String getA_date() {
        return a_date;
    }

    public void setA_date(String a_date) {
        this.a_date = a_date;
    }

    public String getA_status() {
        return a_status;
    }

    public void setA_status(String a_status) {
        this.a_status = a_status;
    }
}
