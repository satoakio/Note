package com.luo.springcloud.service;

import com.luo.springcloud.entities.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

// 由于Feign是面向接口编程
// fallback: 倒退,回退. fallbackFactory: 回退工厂，调用失败的时候对应的回调工厂
@FeignClient(value = "my-microservicecloud-provider-dept", fallbackFactory = DeptClientServiceFallbackFactory.class)
public interface DeptClientService {

    @RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
    public Dept get(@PathVariable("id") long id);

    @RequestMapping(value = "/dept/list",method = RequestMethod.GET)
    public List<Dept> list();

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    public boolean add(Dept dept);

}
