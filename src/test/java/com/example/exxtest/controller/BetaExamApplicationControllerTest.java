package com.example.exxtest.controller;

import com.example.exxtest.entity.EmployeeRepository;
import com.example.exxtest.service.BetaExamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BetaExamApplicationControllerTest {
    @MockBean
    EmployeeRepository employeeRepository;
    @MockBean
    BetaExamServiceImpl betaExamService;
    @Autowired
    BetaExamApplicationController betaExamApplicationController;
    @Autowired
    MockMvc mockMvc;
    @Test
    public void testGetEmployeeDetails() throws Exception {
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/test/getEmployeeDetails")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(result.getResponse().getStatus(),202);
    }

    @Test
    public void testGetEmployeeDetailsByEmail() throws Exception {
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/test/getEmployee/test@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(result.getResponse().getStatus(),200);
    }


    @Test
    public void testGetEmployeeDetailsByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/getEmployee/test")
                .contentType(MediaType.APPLICATION_JSON)).andExpectAll(status().is2xxSuccessful(),content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testSaveEmployeeDetails() throws Exception {
        String addEmployee = "{\"name\": \"testname\", \"email\": \"test@gmail.com\", \"addressLine1\": \"testname\", \"city\" : \"Geelong\", \"state\" : \"Victoria\"}";

        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/test/saveEmployeeDetails")
                .content(addEmployee)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();

        assertEquals(result.getResponse().getStatus(),200);


    }
}
