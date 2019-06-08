package cn.mldn.microcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import cn.mldn.vo.Dept;

@RestController
public class ConsumerDeptController {
    public static final String DEPT_GET_URL = "http://dept-8001.com:8001/dept/get/";
    public static final String DEPT_LIST_URL = "http://dept-8001.com:8001/dept/list/";
    public static final String DEPT_ADD_URL = "http://dept-8001.com:8001/dept/add";
    //使用@Resource注解注入RestTemplate以后控制器就可以调用其他微服务了
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;
    
    @Autowired
    protected HttpServletRequest request;
    
    @RequestMapping(value = "/consumer/dept/get")
    public Object getDept(long id) {
        Dept dept = this.restTemplate
                .exchange(DEPT_GET_URL + id, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), Dept.class)
                .getBody();
        return dept;
    }
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/consumer/dept/list")
    public Object listDept() {
        List<Dept> allDepts = this.restTemplate
                .exchange(DEPT_LIST_URL, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), List.class)
                .getBody();
        return allDepts;
    }
    @RequestMapping(value = "/consumer/dept/add")
    public Object addDept(Dept dept) throws Exception {
        Boolean flag = this.restTemplate.exchange(DEPT_ADD_URL, HttpMethod.POST,
                new HttpEntity<Object>(dept, this.headers), Boolean.class)
                .getBody();
        return flag;
    }
    
    
    /*@RequestMapping(value = "/consumer/dept/get")
    public Object getDept(long id) {
        Dept dept = this.restTemplate.getForObject(DEPT_GET_URL + id,
                Dept.class);
        return dept;
    }
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/consumer/dept/list")
    public Object listDept() {
        List<Dept> allDepts = this.restTemplate.getForObject(DEPT_LIST_URL,
                List.class); 
        return allDepts;
    }
    @RequestMapping(value = "/consumer/dept/add")
    public Object addDept(Dept dept) {
        Boolean flag = this.restTemplate.postForObject(DEPT_ADD_URL, dept,
                Boolean.class);
        return flag;
    }*/
}