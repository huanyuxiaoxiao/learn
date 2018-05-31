package space.fengzheng.cloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import space.fengzheng.cloud.config.RetryerConfig;
import space.fengzheng.cloud.fallback.TestServiceFallBack;
import space.fengzheng.cloud.vo.UserVo;

@FeignClient(value = "TEST-SERVER", fallback = TestServiceFallBack.class, configuration = RetryerConfig.class)
public interface TestService {
    @GetMapping("/list")
    UserVo testList();

    @GetMapping("/exception")
    UserVo exception();

    @GetMapping("/timeout")
    UserVo timeout();
}
