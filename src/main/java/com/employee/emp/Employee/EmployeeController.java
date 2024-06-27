package com.employee.emp.Employee;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    EmployeeService es;
    @GetMapping("/hello")
    public String Hello() {
    	return "Hello all";
    }

    @PutMapping("/eup")
    public String update_emp(@RequestBody EmployeePOJO e)
    {
        int i=es.updateEmployee(e);
        if(i>0)
        {
            return "Employee details updated successfully";
        }
        return "Details not updated";
    }

    @DeleteMapping("/ede")
    public String delete_emp(@RequestBody String fname)
    {
        int i=es.deleteEmployee(fname);
        if(i>0)
        {
            return "Employee details deleted successfully";
        }
        return "Details not deleted";
    }


    @PostMapping("/insert")
    public String insertEmployee(@RequestBody EmployeePOJO e) {
        int add = es.insertEmployee(e);
        // System.out.println(add);
        // System.out.println("Hello");
        if(add==1) {
            return "Employee Added Successfully";
        }
        else if(add==2) {
            return "Employee ID or Password Mismatch";
        }
        else if(add == 3) {
            return "Only Admin need to be added";
        }
        else {
            return "Employee not added";
        }
    }
    
    @PostMapping("/fetch")
    public List<Map<String, Object>> fetchEmployee(@RequestBody EmployeePOJO e ){
        return es.fetchEmployee(e); 
    }

    @PostMapping("/project")
    public String insertProject(@RequestBody ProjectPOJO p) {
        int add = es.insertProject(p);
        if(add==1) {
            return "Project added Successfully";
        }else if (add==2) {
            return "Only Admin and HR can add projects";
        }else if(add==3) {
            return "one HR and one Project Manager should take part in one project only";
        }
        else {
            return "Project Not Assigned";
        }
    }

    @PostMapping("/procemp1")
    public String ProjectEmployeeDetails(@RequestBody ProEmpPOJO p1) {
        // System.out.println("Hello");
        // System.out.println(p1.getPro_name());
        int add = es.ProjectEmployeeDetails(p1);
        if(add==1) {
            return "Project Employee Details Added Successfully";
        }else {
            return "Not Added Successfully";
        }
    }

    @PostMapping("/procdetails")
    public List<Map<String,Object>> ProjectDetails(@RequestBody ProjectPOJO p) {
        try {
            
            List<Map<String, Object>> l = es.projectDetails(p);
            return l;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @PostMapping("/task")
    public String TaskList(@RequestBody TaskPOJO t){
        int add = es.TaskList(t);
        if(add==1) {
            return "Task SuccessFully added";
        }else {
            return "Not added";
        }
    }

}
