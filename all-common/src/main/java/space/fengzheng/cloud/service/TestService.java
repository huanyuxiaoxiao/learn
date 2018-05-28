package space.fengzheng.cloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import space.fengzheng.cloud.fallback.TestServiceFallBack;

@FeignClient(value = "TEST-SERVER",fallback = TestServiceFallBack.class)
public interface TestService {
    @GetMapping("/list")
    String testList();
}
