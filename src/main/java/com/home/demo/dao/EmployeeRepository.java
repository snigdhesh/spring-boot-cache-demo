package com.home.demo.dao;

import com.home.demo.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
  List<Employee> findAll();
  Employee findById(int employeeId);
}
