package com.employee.emp.Employee;

public class EmployeePOJO {
    private String fname;
    private String lname;
    private String dept;
    private double phone;
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public double getPhone() {
        return phone;
    }
    public void setPhone(double phone) {
        this.phone = phone;
    }
    public String getGmailId() {
        return gmailId;
    }
    public void setGmailId(String gmailId) {
        this.gmailId = gmailId;
    }
    private String gmailId;
    private String work_loc;
    private double pincode;
    public String getWork_loc() {
        return work_loc;
    }
    public void setWork_loc(String work_loc) {
        this.work_loc = work_loc;
    }
    public double getPincode() {
        return pincode;
    }
    public void setPincode(double pincode) {
        this.pincode = pincode;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    private double salary;
    // private String username;
    // public String getUsername() {
    //     return username;
    // }
    // public void setUsername(String username) {
    //     this.username = username;
    // }
    private int add_empid;
    public int getAdd_empid() {
        return add_empid;
    }
    public void setAdd_empid(int add_empid) {
        this.add_empid = add_empid;
    }
    private String role_name;
    public String getRole_name() {
        return role_name;
    }
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
