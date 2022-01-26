package com.example.exxtest.service;


import com.example.exxtest.repository.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.exxtest.entity.EmployeeRepository;
import com.example.exxtest.exception.BetaServiceException;
import com.example.exxtest.util.BetaServiceCommonUtil;
import com.example.exxtest.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BetaExamServiceImpl implements BetaExamService{

    private final EmployeeRepository employeeRepository;
    private List<EmployeeVO> employeeVOList;
    @Autowired
    public BetaExamServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;

    }
    @Override
    public List<EmployeeVO> getEmployeeDetails() {
        getAllEmployeeDetails();
        return employeeVOList;
    }

    @Override
    public String saveOrUpdateEmployeeDetails(EmployeeVO em) {
        StringBuilder message = new StringBuilder();

        try {
            Employee employee = new Employee();
            if (em.getId()==null || em.getId().isEmpty()){
                employee.setId(BetaServiceCommonUtil.generateGuID());
            }
            else{
                employee.setId(em.getId());
            }
            employee.setName(em.getName());
            employee.setEmail(em.getEmail());
            employee.setAddressLine1(em.getAddressLine1());

            if(em.getCity()!=null || em.getState()!=null){
                if(!em.getCity().isEmpty() && em.getAddressLine1().isEmpty())
                    message.append("Address Line should not be blank");
                else if(!em.getState().isEmpty() && em.getCity().isEmpty() && em.getAddressLine1().isEmpty())
                    message.append("Address Line and City should not be blank");
            }
            employee.setCity(em.getCity());
            employee.setState(em.getState());
            Date date = new Date();
            employee.setCreatedDate(date);
            if(message.toString().length() == 0) {
                employeeRepository.save(employee);
               message.append(BetaServiceCommonUtil.getJsoneString(employee,new ObjectMapper()));
            }
        }
        catch (BetaServiceException exception){
            message.append(exception);
        }
        return message.toString();
    }

    @Override
    public List<EmployeeVO> getEmployeeDetailsByEmail(String id) {
        getAllEmployeeDetails();
        return employeeVOList.stream().filter(e ->e.getEmail().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeVO> getEmployeeDetailsByName(String id) {
        List<Employee> employeeList = new ArrayList<>();
        List<EmployeeVO> employeeVOList1 =new ArrayList<>();
        employeeRepository.findEmployeeByNameIsLike("%"+id+"%").forEach(employeeList::add);
        employeeVOList1 = employeeList.stream().map(
                em -> EmployeeVO.builder()
                        .id(em.getId())
                        .name(em.getName())
                        .email(em.getEmail())
                        .addressLine1(em.getAddressLine1())
                        .city(em.getCity())
                        .state(em.getState())
                        .creationDate(BetaServiceCommonUtil.getCurrentTime(em.getCreatedDate()))
                        .build()
        ).collect(Collectors.toList());

        return employeeVOList1;
    }

    private void getAllEmployeeDetails(){
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);
         employeeVOList = employeeList.stream().map(
             em -> EmployeeVO.builder()
                     .id(em.getId())
                     .name(em.getName())
                     .email(em.getEmail())
                     .addressLine1(em.getAddressLine1())
                     .city(em.getCity())
                     .state(em.getState())
                     .creationDate(BetaServiceCommonUtil.getCurrentTime(em.getCreatedDate()))
                     .build()
        ).collect(Collectors.toList());

    }


}
