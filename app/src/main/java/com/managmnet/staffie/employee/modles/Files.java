package com.managmnet.staffie.employee.modles;

public class Files {

    String file_name,file_url,task_name,task_start_date,task_due_date,task_desc,task_priority;
    boolean is_submitted=false;

    public Files(String file_name, String file_url) {
        this.file_name = file_name;
        this.file_url = file_url;
    }
    public Files()
    {}

    public boolean isIs_submitted() {
        return is_submitted;
    }

    public void setIs_submitted(boolean is_submitted) {
        this.is_submitted = is_submitted;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_start_date() {
        return task_start_date;
    }

    public String getTask_due_date() {
        return task_due_date;
    }

    public void setTask_due_date(String task_due_date) {
        this.task_due_date = task_due_date;
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

    public void setTask_start_date(String task_start_date) {
        this.task_start_date = task_start_date;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
