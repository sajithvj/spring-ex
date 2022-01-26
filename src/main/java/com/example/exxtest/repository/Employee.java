package com.example.exxtest.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    private String id;
    @NotBlank
    @Max(40)
    @Column(name = "name", nullable = false ,length = 40)
    private String name;
    @NotBlank()
    @Email
    @Column(name = "email", nullable = false,length = 40)
    private String email;
    @Column(name = "address_line1")
    private String addressLine1;
    @Max(40)
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Column(name = "createddate")
    private Date createdDate;

    @Override
    public String toString(){
        return "Employee [ id: " + id +" name: " +name +
                " email: " +email+
                " addressLine1 :" +addressLine1+
                " city : " +city +
                " state : " +state+
                " creationDate: "+ createdDate +"]";
    }

}
