package com.employee.emp.Employee;

import java.time.LocalDate;

public class ProjectPOJO {
    private String pname;
    private String client_name;
    private int assigned_by;
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public int getAssigned_by() {
        return assigned_by;
    }
    public void setAssigned_by(int assigned_by) {
        this.assigned_by = assigned_by;
    }
    public LocalDate getStart_date() {
        return start_date;
    }
    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }
    public LocalDate getEnd_date() {
        return end_date;
    }
    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getHr_man() {
        return hr_man;
    }
    public void setHr_man(int hr_man) {
        this.hr_man = hr_man;
    }
    public int getPm_man() {
        return pm_man;
    }
    public void setPm_man(int pm_man) {
        this.pm_man = pm_man;
    }
    private LocalDate start_date;
    private LocalDate end_date;
    private int duration;
    private int hr_man;
    private int pm_man;

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    
    
}
