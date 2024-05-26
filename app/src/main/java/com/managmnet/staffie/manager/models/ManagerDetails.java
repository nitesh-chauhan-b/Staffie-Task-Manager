package com.managmnet.staffie.manager.models;

import java.util.ArrayList;

public class ManagerDetails {
    String task_name,task_due_date,task_start_date,task_type,task_by,task_desc,task_assigned_to,task_status,task_assigned_mng,task_priority;
    ArrayList<String> assigned_member;
    public String getTask_assigned_to() {
        return task_assigned_to;
    }

    public String getTask_status() {
        return task_status;
    }

    public String getTask_priority() {
        return task_priority;
    }

    public void setTask_priority(String task_priority) {
        this.task_priority = task_priority;
    }

    public ManagerDetails(String task_name, String task_start_date,String task_due_date, String task_type, String task_by, String task_desc) {
        this.task_name = task_name;
        this.task_start_date = task_start_date;
        this.task_due_date = task_due_date;
        this.task_type = task_type;
        this.task_by = task_by;
        this.task_desc = task_desc;
    }

    public ManagerDetails(String task_name, String task_desc,String task_due_date ) {
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.task_due_date = task_due_date;
    }

    public ManagerDetails(String task_assigned_mng) {
        this.task_assigned_mng = task_assigned_mng;
    }

    public ManagerDetails(String task_name, String task_due_date, String task_status, String task_assigned_to) {
        this.task_name = task_name;
        this.task_due_date = task_due_date;
        this.task_status = task_status;
        this.task_assigned_to = task_assigned_to;
    }


    public ManagerDetails(){}
    public String getTask_assigned_mng() {
        return task_assigned_mng;
    }
    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_due_date() {
        return task_due_date;
    }

    public void setTask_due_date(String task_due_date) {
        this.task_due_date = task_due_date;
    }

    public String getTask_start_date() {
        return task_start_date;
    }

    public void setTask_start_date(String task_start_date) {
        this.task_start_date = task_start_date;
    }

    public ArrayList<String> getAssigned_member() {
        return assigned_member;
    }

    public void setAssigned_member(ArrayList<String> assigned_member) {
        this.assigned_member = assigned_member;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getTask_by() {
        return task_by;
    }

    public void setTask_by(String task_by) {
        this.task_by = task_by;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }



    public void setTask_assigned_mng(String task_assigned_mng) {
        this.task_assigned_mng = task_assigned_mng;
    }

    public void setTask_assigned_to(String task_assigned_to) {
        this.task_assigned_to = task_assigned_to;
    }
}
