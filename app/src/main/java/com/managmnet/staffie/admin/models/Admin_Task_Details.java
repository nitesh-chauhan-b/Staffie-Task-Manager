package com.managmnet.staffie.admin.models;

import java.util.ArrayList;

public class Admin_Task_Details {

    String task_name;
    String task_desc;
    String task_priority;
    String task_status;
    String start_date;
    String due_date;
    String task_assigned_to;
    ArrayList<String> assigned_mng;

    public Admin_Task_Details(){}
    public ArrayList<String> getAssigned_mng() {
        return assigned_mng;
    }

    public void setAssigned_mng(ArrayList<String> assigned_mng) {
        this.assigned_mng = assigned_mng;
    }

    public Admin_Task_Details(String task_name, String task_priority, String task_status) {
        this.task_name = task_name;
        this.task_priority = task_priority;
        this.task_status = task_status;
    }

    public Admin_Task_Details(String task_name, String task_desc, String task_priority, String task_status, String start_date, String due_date) {
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.task_priority = task_priority;
        this.task_status = task_status;
        this.start_date = start_date;
        this.due_date = due_date;
    }
    public Admin_Task_Details(String task_name, String task_desc, String task_priority, String task_status, String start_date, String due_date, ArrayList<String> assigned_mng) {
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.task_priority = task_priority;
        this.task_status = task_status;
        this.start_date = start_date;
        this.due_date = due_date;
        this.assigned_mng = assigned_mng;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getTask_priority() {
        return task_priority;
    }

    public void setTask_priority(String task_priority) {
        this.task_priority = task_priority;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTask_assigned_to() {
        return task_assigned_to;
    }

    public void setTask_assigned_to(String task_assigned_to) {
        this.task_assigned_to = task_assigned_to;
    }
}
