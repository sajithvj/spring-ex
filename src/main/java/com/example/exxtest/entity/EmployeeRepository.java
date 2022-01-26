package com.example.exxtest.entity;

import com.example.exxtest.repository.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    Iterable<Employee> findEmployeeByNameIsLike(String s);



}
