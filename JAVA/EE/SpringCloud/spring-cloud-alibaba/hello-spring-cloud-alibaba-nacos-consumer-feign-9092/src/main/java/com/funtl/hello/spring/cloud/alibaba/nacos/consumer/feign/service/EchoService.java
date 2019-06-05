package com.funtl.hello.spring.cloud.alibaba.nacos.consumer.feign.service;

import com.funtl.hello.spring.cloud.alibaba.nacos.consumer.feign.service.fallback.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 通过 @FeignClient("服务名") 注解来指定调用哪个服务。代码如下：
@FeignClient(value = "nacos-provider", fallback = EchoServiceFallback.class)
// 在 Service 中增加 fallback 指定类,实现熔断器的功能
public interface EchoService {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable("message") String message);
}