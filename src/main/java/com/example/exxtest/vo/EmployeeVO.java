package com.example.exxtest.vo;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String email;
    private String addressLine1;
    private String city;
    private String state;
    private String creationDate;
   @Override
    public String toString(){
       return "Employee [ id: " + id +" name: " +name +
               " email: " +email+
               " addressLine1 :" +addressLine1+
               " city : " +city +
               " state : " +state+
               " creationDate: "+ creationDate +"]";
   }
}
