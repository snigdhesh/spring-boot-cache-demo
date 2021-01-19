package com.home.demo.services;

import com.home.demo.dao.EmployeeRepository;
import com.home.demo.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  @Cacheable(cacheNames = "employees", key="#root.method.name")
  public List<Employee> getEmployees() {
    log.info("Fetching all employees from DB.."); //This log will not be printed to console, if data is fetched from cache. That's how we check where data is coming from.
    return employeeRepository.findAll();
  }


  @Override
  @Cacheable(cacheNames = "employees", key = "#employeeId")
  public Employee getEmployeeById(int employeeId) {
    log.info("Fetching employee by Id from DB.."); //This log will not be printed to console, if data is fetched from cache. That's how we check where data is coming from.
    return employeeRepository.findById(employeeId);
  }

}
