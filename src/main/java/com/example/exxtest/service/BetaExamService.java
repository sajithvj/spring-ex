package com.example.exxtest.service;

import com.example.exxtest.vo.EmployeeVO;

import java.util.List;

public interface BetaExamService {
   public List<EmployeeVO> getEmployeeDetails();
   public String saveOrUpdateEmployeeDetails(EmployeeVO employeeVO);
   public List<EmployeeVO> getEmployeeDetailsByEmail(String id);
   public List<EmployeeVO> getEmployeeDetailsByName(String id);

}
