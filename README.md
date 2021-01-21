# spring-boot-cache-demo
This application uses spring boot caching mechanism to fetch data


Add mysql connector dependency and spring boot cache dependency

```
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-cache -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
    <version>2.4.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>2.4.2</version>
</dependency>

```


I have mysql setup on my local with 10 users.

### sources
https://www.youtube.com/watch?v=Yf-zH5Q9FQM&ab_channel=GreenLearner 

Points to demo on video:  

- We don't need `spring-boot-starter-cache` dependency
- Spring context offers `CacheManager` and `Cache` classes by default from sprint-context.
- Data won't be save in cache when you call a local method, with `@Cacheable` annotation.  
`Example`
```
# GroupSevice.java

public void getUser(String userName){
    List<String> userGroups=getAllGroupsbyUserName(userName);
}

@Cacheable(value="groups", key="#root.method.name")
public List<String> getAllGroupsbyUserName(String userName){
    //statements to be executed
    return groups;
}

```
- This issue can be fixed by implementing @Cacheable method in different service and calling it, like shown below.
```
# GroupSevice.java

@Autowired
private UserGroupService userGroupService;
public void getUser(String userName){
    List<String> userGroups=userGroupService.getAllGroupsbyUserName(userName);
}

# UserGroupService.java

@Cacheable(value="groups", key="#root.method.name")
public List<String> getAllGroupsbyUserName(String userName){
    //statements to be executed
    return groups;
}
```
- we can expose endpoints to clear cache and also to see what's in cache, like shown below.
```
# CacheController.java

@Autowired
private CacheManager cacheManager;
@RequestMapping("/cache/{cacheName}")
public Cache getCacheInfo(@PathVariable String cacheName){
    return cacheManager.getCache(cacheName);
}

@RequestMapping("/cache/{cacheName})
public void clearCache(@PathVariable String cacheName){
    Cache cache=getCacheInfo(cacheName);
    if(null!=cache)
        cache.clear();
}
```