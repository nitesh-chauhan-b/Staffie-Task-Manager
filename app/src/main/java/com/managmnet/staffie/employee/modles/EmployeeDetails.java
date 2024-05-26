package com.managmnet.staffie.employee.modles;

public class EmployeeDetails {
    java.lang.String task_name,task_due_date,task_type,task_by,assigned_task_emp_name,task_start_Date,task_desc,task_priority;

    public EmployeeDetails(java.lang.String task_name, java.lang.String task_due_date, java.lang.String task_type, java.lang.String task_by) {
        this.task_name = task_name;
        this.task_due_date = task_due_date;
        this.task_type = task_type;
        this.task_by = task_by;
    }
    public EmployeeDetails(java.lang.String assigned_task_emp_name){
        this.assigned_task_emp_name=assigned_task_emp_name;
    }
    public EmployeeDetails(){

    }

    public String getTask_start_Date() {
        return task_start_Date;
    }

    public void setTask_start_Date(String task_start_Date) {
        this.task_start_Date = task_start_Date;
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

    public java.lang.String getAssigned_task_emp_name() {
        return assigned_task_emp_name;
    }

    public void setAssigned_task_emp_name(java.lang.String assigned_task_emp_name) {
        this.assigned_task_emp_name = assigned_task_emp_name;
    }

    public java.lang.String getTask_name() {
        return task_name;
    }

    public void setTask_name(java.lang.String task_name) {
        this.task_name = task_name;
    }

    public java.lang.String getTask_due_date() {
        return task_due_date;
    }

    public void setTask_due_date(java.lang.String task_due_date) {
        this.task_due_date = task_due_date;
    }

    public java.lang.String getTask_type() {
        return task_type;
    }

    public void setTask_type(java.lang.String task_type) {
        this.task_type = task_type;
    }

    public java.lang.String getTask_by() {
        return task_by;
    }

    public void setTask_by(java.lang.String task_by) {
        this.task_by = task_by;
    }
}
