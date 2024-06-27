package com.employee.emp.Employee;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EmployeeService {
    private final JdbcTemplate jdbct;

    public EmployeeService(DataSource ds)
    {
        this.jdbct=new JdbcTemplate(ds);
    }

    public int insertEmployee(@RequestBody EmployeePOJO e) {
        String fname = e.getFname();
        String lname = e.getLname();
        String dept = e.getDept();
        double phone = e.getPhone();
        String gmailId = e.getGmailId();
        double pincode = e.getPincode();
        double salary = e.getSalary();
        String work_loc = e.getWork_loc();
        // char x1 = fname.charAt(0);
        // String x2 = lname.toLowerCase();
        String username = (fname.charAt(0)+lname).toLowerCase();
        // String x2 = lname.toLowerCase();
        String rolename = e.getRole_name();
        int add_empid = e.getAdd_empid();
        String pass = e.getPassword();
        String sql1 = "select roleid from Roles where role_name = ?";
        String sql2 = "select role_name from Roles where roleid in (select roleid from Employee where eid = ?)";
        String sql3 = "select password from Emp_Extend where eid = ?";
        try {
                List<Map<String,Object>> l1 = jdbct.queryForList(sql2,add_empid);
                Object x1 = l1.get(0).get("role_name");
                List<Map<String,Object>> l2 = jdbct.queryForList(sql3,add_empid);
                Object x2 = l2.get(0).get("password");

                String ro = (String) x1;
                // System.out.println(ro);
                String o = (String) x2;
                // System.out.println(o + " "+ ro);
                if(o.equals(pass)) {
                try {
                    if(ro.equals("Admin") && o.equals(pass)) {  
                        List<Map<String, Object>> l = jdbct.queryForList(sql1,rolename);
                        Object x = l.get(0).get("roleid");
                        // System.out.println(x);
                        int roleid  = (Integer) x;
                        try {
                            String sql = "insert into Employee (fname,lname,dept,phone,gmailId,work_loc,username,pincode,salary,roleid,add_empid) values (?,?,?,?,?,?,?,?,?,?,?)";
                            int i = jdbct.update(sql,fname,lname,dept,phone,gmailId,work_loc,username,pincode,salary,roleid,add_empid);
                            return i;
                        } catch (Exception ec) {
                            ec.printStackTrace();
                            return 0;
                        }
                    }
                    else {
                        return 3;
                    }    
                } catch (Exception ec) {
                    ec.printStackTrace();
                    return 3;
                }
            }
            else {
                return 2;
            }    
        }
         catch (Exception ec) {
            return 2;
        }      
    }

    public int updateEmployee(EmployeePOJO e)
    {
    String query="update Employee set gmailId=? where fname=?";
    int i=jdbct.update(query,e.getGmailId(),e.getFname());
    System.out.println(i);
    return i;
    }


    public int deleteEmployee(String fname)
    {
    String query="delete from Employee where fname=?";
    int i=jdbct.update(query,fname);
    return i;
    }


    public List<Map<String,Object>> fetchEmployee (EmployeePOJO e){
        String fname = e.getFname();
        String lname = e.getLname();
        String dept = e.getDept();
        String work_loc = e.getWork_loc();
        String sql = "select * from Employee where 1=1";
        System.out.println("Hello");
        if(fname!=null) {
            sql = sql + " and fname like \'%"+fname+"%\'";
        }
        if(lname!=null) {
            sql = sql + " and lname like \'%"+lname+"%\'";
        }
        if(dept!=null) {
            sql = sql + " and dept like \'%"+dept+"%\'";
        }
        if(work_loc!=null) {
            sql = sql + " and work_loc like \'%"+work_loc+"%\'";
        }
        try {
            return jdbct.queryForList(sql);
        } catch (Exception ec) {
            ec.printStackTrace();
            return Collections.emptyList();
        }
    }


    public List<Map<String,Object>> projectDetails(ProjectPOJO p) {
        String pname = p.getPname();
        String sql = "select pid from project where pname = \""+pname+"\"";
        int pid = jdbct.queryForObject(sql,Integer.class); 
        
        // List<Map<String,Object>> l = jdbct.queryForList(sql,pname);
        // Object o = l.get(0).get("pid");
        // int pid = (Integer) o;


        // extracting details from project table
        String sql1 = "select pid,pname,client_name,assigned_by,start_date,end_date,hr_man,pm_man from project where pid =?";
        List<Map<String, Object>> l1 = jdbct.queryForList(sql1,pid);
        if (l1.isEmpty()) {
            // Return an empty list if no project details are found
            return l1;
        }


        // listing all the lead details of the project
        String sql3 = "select distinct(emp_lead) from ProjectEmployee where pid=?";
        List<Map<String,Object>> l3 = jdbct.queryForList(sql3,pid);
        List<Integer> employeeLeads = l3.stream().map(row -> (Integer) row.get("emp_lead")).collect(Collectors.toList());


        //getting details of each lead present in a project
        List<Map<String, Object>> employeeLeadDetails = new ArrayList<>();
        for (Integer eid : employeeLeads) {
            String sqlEmp = "select eid, fname, lname, gmailId, phone from Employee where eid = ?";
            List<Map<String, Object>> empLeadDetails = jdbct.queryForList(sqlEmp, eid);
            if (!empLeadDetails.isEmpty()) {
                employeeLeadDetails.add(empLeadDetails.get(0));
            }
        }
        

        //listing all the employee id details
        String sql2 = "select eid from ProjectEmployee where pid=?";
        List<Map<String, Object>> l2 = jdbct.queryForList(sql2,pid);
        List<Integer> employeeIds = l2.stream().map(row -> (Integer) row.get("eid")).collect(Collectors.toList());


        //getting details of each employee present in a project
        List<Map<String, Object>> employeeDetails = new ArrayList<>();
        for (Integer eid : employeeIds) {
            String sqlEmp = "select eid, fname, lname, gmailId, phone from Employee where eid = ?";
            List<Map<String, Object>> empDetails = jdbct.queryForList(sqlEmp, eid);
            if (!empDetails.isEmpty()) {
                employeeDetails.add(empDetails.get(0));
            }
        }


        // creating new map and adding all the above obtained results
        Map<String, Object> map = l1.get(0);
        map.put("emp_leads",employeeLeadDetails);           
        map.put("employee_details", employeeDetails);    
        return List.of(map);
    }



    public int insertProject(ProjectPOJO p) {
        String pname = p.getPname();
        String client_name = p.getClient_name();
        int assigned_by = p.getAssigned_by();
        LocalDate start_date = p.getStart_date();
        LocalDate end_date = p.getEnd_date();
        int duration = p.getDuration();
        int hr_man = p.getHr_man();
        int pm_man = p.getPm_man();
        String sql = "select role_name from Roles where roleid in (select roleid from Employee where eid = ?)";
        try {
            List<Map<String, Object>> l = jdbct.queryForList(sql,assigned_by);
            Object x = l.get(0).get("role_name");
            String ro = (String) x;
            // System.out.println(ro);
            if(ro.equals("Admin") || ro.equals("HR")) {
                String sql1 = "select role_name from Roles where roleid in (select roleid from Employee where eid = ?)";
                List<Map<String, Object>> l1 = jdbct.queryForList(sql1,hr_man);
                Object x1 = l1.get(0).get("role_name");
                String ro1 = (String) x1;
                String count1 = "select count(hr_man) as count1 from project where hr_man = ?";
                List<Map<String, Object>> c1 = jdbct.queryForList(count1,hr_man);
                Object m1 = c1.get(0).get("count1");
                Long ll1 = (Long) m1;
                int co1 = ll1.intValue();
                if(ro1.equals("HR") && co1== 0){
                    String sql2 = "select role_name from Roles where roleid in (select roleid from Employee where eid = ?)";
                    List<Map<String, Object>> l2 = jdbct.queryForList(sql2,pm_man);
                    Object x2 = l2.get(0).get("role_name");
                    String ro2 = (String) x2;
                    String count2 = "select count(pm_man) as count2 from project where pm_man = ?";
                    List<Map<String, Object>> c2 = jdbct.queryForList(count2,pm_man);
                    Object m2 = c2.get(0).get("count2");
                    Long ll2 = (Long) m2;
                    int co2 = ll2.intValue();
                    if(ro2.equals("Project Manager") && co2 == 0) {
                        if (duration==0){
                            long x3 = ChronoUnit.DAYS.between(start_date, end_date);
                            int duration1 = (int) x3;
                            // System.out.println(duration1);
                            String sql3 = "insert into project (pname, client_name, assigned_by, start_date, end_date, duration, hr_man, pm_man) values (?,?,?,?,?,?,?,?)";
                            try {
                                int add1 = jdbct.update(sql3,pname,client_name,assigned_by,start_date,end_date,duration1,hr_man,pm_man);
                                // System.out.println(add1);
                                return add1;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return 0;
                            }
                        }
                        else if (end_date == null) {
                            LocalDate end_date1 = start_date.plusDays(duration);
                            String sql3 = "insert into project (pname, client_name, assigned_by, start_date, end_date, duration, hr_man, pm_man) values (?,?,?,?,?,?,?,?)";
                            try {
                                int add2 = jdbct.update(sql3,pname,client_name,assigned_by,start_date,end_date1,duration,hr_man,pm_man);
                                return add2;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return 0;
                            }
                        }else {
                            return 0;
                        }}
                }
                else {
                    return 3;
                }
                return 0;
            }
            else {
                return  2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int ProjectEmployeeDetails(ProEmpPOJO p) {
        String Emp_name = p.getEmp_name();
        String Pro_name = p.getPro_name();
        int emp_lead = p.getEmp_lead();
        int assigned_by = p.getAssigned_by();
        try {
            String sql1 = "select eid from Employee where username = ? and roleid = 5";
            List<Map<String, Object>> l1 = jdbct.queryForList(sql1,Emp_name);
            Object Eid = l1.get(0).get("eid");
            int eid = (Integer) Eid;
            // System.out.println(eid);
            try {
                String sql2 = "select pid from project where pname = ?";
                List<Map<String, Object>> l2 = jdbct.queryForList(sql2,Pro_name);
                Object Pid = l2.get(0).get("pid");
                int pid = (Integer) Pid;
                // System.out.println(pid);
                try {
                    String sql3 = "select role_name from Roles where roleid in (select roleid from Employee where eid = ?)";
                    List<Map<String, Object>> l3 = jdbct.queryForList(sql3,emp_lead);
                    Object ll3 = l3.get(0).get("role_name");
                    String role_name = (String) ll3;
                    // System.out.print(role_name);
                    if(role_name.equals("Lead")) {
                        String sql4 = "select count(eid) as count1 from ProjectEmployee where eid = ?";
                        List<Map<String, Object>> l4 = jdbct.queryForList(sql4,eid);
                        Object co1 = l4.get(0).get("count1");
                        Long co2 = (Long) co1;
                        int count = co2.intValue();
                        // System.out.println(count);
                        if(count==0) {
                            try {
                                String sql5 = "select role_name from Roles where roleid in (select roleid from Employee where eid = ?)";
                                List<Map<String, Object>> lo1 = jdbct.queryForList(sql5,assigned_by);
                                Object x1 = lo1.get(0).get("role_name");
                                String ro1 = (String) x1;
                                String sql7 = "select hr_man, pm_man from project where pid= ?";
                                List<Map<String, Object>> lo2 = jdbct.queryForList(sql7,pid);
                                Object obj1 = lo2.get(0).get("hr_man");
                                Object obj2 = lo2.get(0).get("pm_man");
                                int hr_man = (Integer) obj1;
                                int pm_man = (Integer) obj2;
                                if((ro1.equals("Project Manager") || ro1.equals("HR")) && ((hr_man == assigned_by) || (pm_man == assigned_by))) {
                                    String sql6 = "insert into ProjectEmployee (eid,pid,emp_lead,assigned_by) values (?,?,?,?)";
                                    int add1 = jdbct.update(sql6,eid,pid,emp_lead,assigned_by);
                                    return add1;
                                }
                                else {
                                    return 8;
                                }
                            } catch (Exception e) {
                                return 7;
                            }

                        }
                        else {
                            return 6;
                        }
                    }
                    else {
                        return 5;
                    }
                }
                catch(Exception e) {
                    return 4;
                }
            } catch (Exception e) {
                return 3;
            } 
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }


    public int TaskList(TaskPOJO t) {
        String taskname = t.getTaskname();
        int created_by = t.getCreated_by();
        int assigned_to = t.getAssigned_to();
        LocalDate assigned_date = LocalDate.now();
        String descript = t.getDescript();
        LocalDate start_date = t.getStart_date();
        int duration = t.getDuration();
        String statuzz = t.getStatuzz();

        try {
            String sql = "select eid from ProjectEmployee where emp_lead = ?";
            List<Map<String, Object>> l = jdbct.queryForList(sql,created_by);
            List<Integer> eids = l.stream().map(row -> (Integer) row.get("eid")).collect(Collectors.toList());
            if(eids.contains(assigned_to)){
                if(start_date.isAfter(assigned_date)) {
                    double dur = Math.ceil(duration/8);
                    int duration1 = (int) dur;
                    LocalDate end_date = start_date.plusDays(duration1);
                    String sql2 = "insert into TaskList (taskname, created_by, assigned_to, assigned_date, descript, start_date, duration, statuzz,end_date) values (?,?,?,?,?,?,?,?,?)";
                    int add = jdbct.update(sql2,taskname,created_by,assigned_to, assigned_date, descript, start_date,duration,statuzz,end_date);
                    return add;
                }
                else {
                    return 4;
                }
            }
            else {
                return 3;
            }
        } catch (Exception e) {
            return 2;
        }

    }
}


