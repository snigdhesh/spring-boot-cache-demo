package com.home.demo.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringCacheScheduler {
  private static final Logger logger = LoggerFactory.getLogger(SpringCacheScheduler.class);

  @Autowired
  CacheManager cacheManager;

  @Scheduled(cron = "${schedulejob.cron.expression}")
  @CacheEvict(cacheNames = "employees", allEntries = true)
  //This variable comes from app.properties file, for instance > schedulejob.cron.expression =  0 10 * * * ? (this expression indicates some time, google it to find more such expressions)
  public void evictAllEmployees() {
    Cache cache = cacheManager.getCache("employees");
    logger.info("Cache={}", cache);
    logger.info("Evicted/removed all employees from spring-boot-cache.");
  }
}
