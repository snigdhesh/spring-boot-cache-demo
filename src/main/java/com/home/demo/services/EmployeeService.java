package com.home.demo.services;

import com.home.demo.models.Employee;

import java.util.List;

public interface EmployeeService {
  List<Employee> getEmployees();
  Employee getEmployeeById(int employeeId);
}
