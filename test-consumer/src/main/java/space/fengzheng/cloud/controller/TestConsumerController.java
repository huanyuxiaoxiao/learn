package space.fengzheng.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.fengzheng.cloud.exception.PromptException;
import space.fengzheng.cloud.service.TestService;

import javax.annotation.Resource;

@Slf4j
@RestController
public class TestConsumerController {
    @Resource
    private TestService testService;

    @GetMapping("list")
    public String list(){
        log.info("list receive");
        return testService.testList();
    }
    @GetMapping("exceptionServer")
    public String exceptionServer(){
        log.info("exceptionServer receive");
        return testService.exception();
    }

    @GetMapping("exception")
    public String exception(){
        log.info("exception receive");
        if(true){
            throw new PromptException("异常信息");
        }
        return testService.exception();
    }

    @GetMapping("timeout")
    public String timeout(){
        log.info("timeout receive");
        return testService.timeout();
    }
}
