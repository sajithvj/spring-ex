package com.example.exxtest.controller;


import com.example.exxtest.exception.BetaServiceException;
import com.example.exxtest.service.BetaExamServiceImpl;
import com.example.exxtest.service.KafkaService;
import com.example.exxtest.vo.EmployeeVO;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/test")
public class BetaExamApplicationController {
    private  BetaExamServiceImpl betaExamService;
    private KafkaService kafkaService;

    @Autowired
    public void setBetaExamServiceImpl(BetaExamServiceImpl betaExamService){
        this.betaExamService = betaExamService;
    }
    @Autowired
    public void setKafkaService(KafkaService kafkaService){this.kafkaService=kafkaService;}

    @GetMapping("/getEmployeeDetails")
    @ResponseBody
    public ResponseEntity<List<EmployeeVO>> getEmployeeDetails(){
        List<EmployeeVO> employees  = betaExamService.getEmployeeDetails();
        if(employees.size()==0){

            throw new BetaServiceException("Record not found");
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/saveEmployeeDetails")
    @ResponseBody
    public ResponseEntity<String> saveEmployeeDetails (@Valid @RequestBody EmployeeVO employeeVO){
        String message= betaExamService.saveOrUpdateEmployeeDetails(employeeVO);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping("/getEmployee/{input}")
    @ResponseBody
    public ResponseEntity<List<EmployeeVO>> getEmployeeDetailsByInputParameter(@PathVariable String input){
        boolean valid = EmailValidator.getInstance().isValid(input);
        List<EmployeeVO> employees ;
        if(valid) {
            employees  = betaExamService.getEmployeeDetailsByEmail(input);
        }
        else {
            employees = betaExamService.getEmployeeDetailsByName(input);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/getEmployeeByName")
    @ResponseBody
    public ResponseEntity<List<EmployeeVO>> getEmployeeDetailsByName(@Valid @RequestParam String name){

        List<EmployeeVO> employees ;

            employees = betaExamService.getEmployeeDetailsByName(name);

        if(employees.size()==0){

            throw new BetaServiceException("Record not found");
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping(value = "/producer")
    public String producer(@RequestBody EmployeeVO message) {
        kafkaService.send(message);
        return "Message sent to the Kafka Topic java_in_use_topic Successfully";
    }

   @GetMapping(value = "/consumer")
   public ResponseEntity<EmployeeVO> consumer(){
       return new ResponseEntity<>(kafkaService.getEmployeeVOList().get(0), HttpStatus.OK);
   }
}
