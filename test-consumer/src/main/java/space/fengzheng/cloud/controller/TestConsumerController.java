package space.fengzheng.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.fengzheng.cloud.exception.PromptException;
import space.fengzheng.cloud.service.TestService;
import space.fengzheng.cloud.vo.ListVo;
import space.fengzheng.cloud.vo.UserVo;

import javax.annotation.Resource;
import java.util.ArrayList;


@Slf4j
@RestController
@Api(description = "测试")
public class TestConsumerController {
    @Resource
    private TestService testService;

    @GetMapping("list")
    @ApiOperation(value = "展示首页信息", notes = "测试接口")
    public UserVo list() {
        log.info("list receive");
        return testService.testList();
    }

    @GetMapping("exceptionServer")
    public UserVo exceptionServer() {
        log.info("exceptionServer receive");
        return testService.exception();
    }

    @GetMapping("exception")
    public UserVo exception() {
        log.info("exception receive");
        if (true) {
            throw new PromptException("异常信息");
        }
        return testService.exception();
    }

    @GetMapping("timeout")
    public UserVo timeout() {
        log.info("timeout receive");
        return testService.timeout();
    }

    @GetMapping("voidReturn")
    public void voidReturn() {
        log.info("voidReturn receive");
    }

    @GetMapping("jsonReturn")
    public ListVo jsonReturn() {
        return ListVo.buildList(new ArrayList<UserVo>());
    }
}
