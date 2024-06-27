package com.employee.emp;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.employee.emp.Employee.EmployeePOJO;
import com.employee.emp.Employee.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCase2 {
    @Autowired
    private MockMvc mockmvc;
    
    @Autowired
    private ObjectMapper objectmapr;
    
    @MockBean
    private EmployeeService es;
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testGreet() throws Exception
    {
        mockmvc.perform(MockMvcRequestBuilders.get("/emp/hello"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Hello all"));
    }

    @Test
   public void testInsert() throws Exception
   {
    EmployeePOJO e=new EmployeePOJO();
    e.setFname("abc");
    e.setLname("xyz");
    e.setDept("playing");
    e.setGmailId("abcxyz@gmail.com");
    e.setPincode(522344);
    e.setPhone(652456544);
    e.setSalary(4645);
    e.setWork_loc("MCity");
    e.setAdd_empid(1);
	e.setPassword("Ad@bu2051");
    {
    when(es.insertEmployee(any(EmployeePOJO.class))).thenReturn(1);
    mockmvc.perform(MockMvcRequestBuilders.post("/emp/insert")
            .contentType("application/json")
            .content(objectmapr.writeValueAsString(e)))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andExpect(MockMvcResultMatchers.content().string("Employee Added Successfully"));
    System.out.println("in 1 executed");
    }
    {
    when(es.insertEmployee(any(EmployeePOJO.class))).thenReturn(0);
    mockmvc.perform(MockMvcRequestBuilders.post("/emp/insert")
            .contentType("application/json")
            .content(objectmapr.writeValueAsString(e)))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andExpect(MockMvcResultMatchers.content().string("Employee not added"));
    System.out.println("in 0 executed");
    }
   }

   @Test
   public void testUpdate() throws Exception
   {
    EmployeePOJO e=new EmployeePOJO();
    e.setFname("abc");
    e.setGmailId("abcxyz@gmail.com");
    {
    when(es.updateEmployee(any(EmployeePOJO.class))).thenReturn(1);
    mockmvc.perform(MockMvcRequestBuilders.put("/emp/eup")
            .contentType("application/json")
            .content(objectmapr.writeValueAsString(e)))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andExpect(MockMvcResultMatchers.content().string("Employee details updated successfully"));
    System.out.println("up 1 executed");
    }
    {
    when(es.updateEmployee(any(EmployeePOJO.class))).thenReturn(0);
    mockmvc.perform(MockMvcRequestBuilders.put("/emp/eup")
            .contentType("application/json")
            .content(objectmapr.writeValueAsString(e)))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andExpect(MockMvcResultMatchers.content().string("Details not updated"));
    System.out.println("up 0 executed");
    }
   }

   @Test
   public void testDelete() throws Exception
   {
    {
    when(es.deleteEmployee(any(String.class))).thenReturn(1);
    mockmvc.perform(MockMvcRequestBuilders.delete("/emp/ede")
            .contentType(MediaType.TEXT_PLAIN)
            .content("axyz@mss.com"))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andExpect(MockMvcResultMatchers.content().string("Employee details deleted successfully"));
    System.out.println("1.executed");
    }
    {
    when(es.deleteEmployee(any(String.class))).thenReturn(0);
    mockmvc.perform(MockMvcRequestBuilders.delete("/emp/ede")
            .contentType(MediaType.TEXT_PLAIN)
            .content("axyz@mss.com"))
    .andExpect(MockMvcResultMatchers.status().isOk())
    .andExpect(MockMvcResultMatchers.content().string("Details not deleted"));
    System.out.println("0.executed");
    }
   }

    @Test
    public void testShowEmployees() throws Exception {
        EmployeePOJO e=new EmployeePOJO();
        List<Map<String, Object>> mockData = prepareMockDataFromQuery();
//        System.out.println(mockData);
        when(jdbcTemplate.queryForList(anyString())).thenReturn(mockData);
        MvcResult result = mockmvc.perform(MockMvcRequestBuilders.post("/emp/fetch")
                .contentType("application/json")
                .content(objectmapr.writeValueAsString(e)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        List<Map<String, Object>> actualData = es.fetchEmployee(null); 
        System.out.println("actualdata:"+" "+actualData);
        assertEquals(actualData.size(), mockData.size());

        for (int i = 0; i < mockData.size(); i++) {
            Map<String, Object> expectedRow = mockData.get(i);
            Map<String, Object> actualRow = actualData.get(i);

            assertEquals(expectedRow.get("Employee_Id"), actualRow.get("Employee_Id"));
            assertEquals(expectedRow.get("First_Name"), actualRow.get("First_Name"));
            assertEquals(expectedRow.get("Last_Name"), actualRow.get("Last_Name"));
        }
    }
    private List<Map<String, Object>> prepareMockDataFromQuery() {
        String sqlQuery = "SELECT * FROM Employees WHERE 1=1";
//        System.out.println(jdbcTemplate.queryForList(sqlQuery));
        return jdbcTemplate.queryForList(sqlQuery);
    }
}
