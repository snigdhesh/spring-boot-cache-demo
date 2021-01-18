package com.home.demo.controllers;

import com.home.demo.models.Employee;
import com.home.demo.schedulers.SpringCacheScheduler;
import com.home.demo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
public class EmployeeController {
  private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

  @Autowired
  private CacheManager cacheManager;

  @Autowired
  SpringCacheScheduler springCacheScheduler;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private Environment environment;

  @RequestMapping(value = "/employees", method = RequestMethod.GET)
  public List<Employee> getEmployees() {
    return employeeService.getEmployees();
  }

  @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
  public Employee getEmployeeById(@PathVariable int employeeId) {
    return employeeService.getEmployeeById(employeeId);
  }

  @RequestMapping(value = "/cache", method = RequestMethod.GET)
  public Cache getCacheInfo() {
    Cache cache = cacheManager.getCache("employees");
    return cache;
  }

  @RequestMapping(value = "/cache/clear", method = RequestMethod.DELETE)
  public void evictAllEmployeesFromCache(HttpServletResponse httpServletResponse) {
    try {
      springCacheScheduler.evictAllEmployees();
      httpServletResponse.sendRedirect("/cache");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

/*
  @RequestMapping(value = "/environment", method = RequestMethod.GET)
  public String getEnv() {
    InetAddress inetAddress= null;
    try {
      inetAddress = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return inetAddress.getHostAddress();
  }
*/

}
