package com.employee.emp.Employee;

import java.time.LocalDate;

public class TaskPOJO {
    private String taskname;
    private int created_by;
    private int assigned_to;
    private LocalDate assigned_date;
    private String descript;
    private LocalDate start_date;
    private int duration;
    private String statuzz;

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(int assigned_to) {
        this.assigned_to = assigned_to;
    }

    public LocalDate getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(LocalDate assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatuzz() {
        return statuzz;
    }

    public void setStatuzz(String statuzz) {
        this.statuzz = statuzz;
    }
}
