package com.employee.emp;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.employee.emp.Employee.EmployeeController;
import com.employee.emp.Employee.EmployeePOJO;
import com.employee.emp.Employee.EmployeeService;

public class TestCase1 {
	DataSource dataSource = createDataSource();
    EmployeeService ec = new EmployeeService(dataSource);
    JdbcTemplate jdbc = new JdbcTemplate(dataSource);     
	EmployeeController ec1 = new EmployeeController();
	
	@Test
    public void test_insert_emp()
    {
        EmployeePOJO e = new EmployeePOJO();
        e.setFname("xyz");
        e.setLname("yzx");
        e.setDept("swtrainee");
        e.setGmailId("xyz@gmail.com");
        e.setPincode(522007);
        e.setPhone(12398745);
        e.setSalary(4645);
        e.setWork_loc("vizag");
		e.setRole_name("HR");
		e.setAdd_empid(1);
		e.setPassword("Ad@bu2051");
        int result = ec.insertEmployee(e);
        assertEquals(1, result);
    }

	// @Test
    // public void test_update_emp()
    // {
    //     EmployeePOJO e = new EmployeePOJO();
    //     e.setFname("xyz");
    //     e.setGmailId("xy@gmail.com");
    //     int result = ec.updateEmployee(e);
    //     assertTrue(result==0 || result==1);
    // }

	// @Test
    // public void test_delete_emp()
    // {
    //     int result = ec.deleteEmployee("xyz");
    //     assertEquals(1, result);
    // }


	@Test
    public void test_show_emp()
    {
        EmployeePOJO e = new EmployeePOJO();
        e.setFname("Yvonne");
        e.setLname("Hill");
        e.setDept("Operations");
        e.setWork_loc("bhogapuram");
        List<Map<String,Object>> result = ec.fetchEmployee(e);
		System.out.println(result.get(0));
        String query="select * from Employee where 1=1 ";
        if(e.getFname()!=null)
        {
            query=query+" and fname=\""+e.getFname()+"\"";
        }
        if(e.getLname()!=null)
        {
            query=query+" and lname=\""+e.getLname()+"\"";
        }
        if(e.getDept()!=null)
        {
            query=query+" and dept=\""+e.getDept()+"\"";
        }
        if(e.getWork_loc()!=null)
        {
            query=query+" and work_loc like \'%"+e.getWork_loc()+"%\'";
        }
        List<Map<String,Object>> details=jdbc.queryForList(query);
        // List<Map<String,Object>> a=new ArrayList<Map<String,Object>>();
        // String keys[]={"eid","fname","lname","dept","phone","join_date","gmailId","work_loc",
        // "pincode","salary","username","roleid","add_empid"};
        // for(int i=0;i<details.size();i++)
        // {
        //     Collection<Object> values=details.get(i).values();
        //     Map<String,Object> dummy=new LinkedHashMap<>();
        //     for(int j=0;j<keys.length;j++)
        //     {
        //         dummy.put(keys[j],values.toArray()[j]);
        //     }
        //     a.add(dummy);
        // }
			assertEquals(result.size(), details.size());

        for (Map<String, Object> resultMap : result) {
            assertTrue(details.contains(resultMap));
        }
    }

	private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Itg166");
        dataSource.setUsername("root");
        dataSource.setPassword("M1racle@123");
        return dataSource;
    }
}
