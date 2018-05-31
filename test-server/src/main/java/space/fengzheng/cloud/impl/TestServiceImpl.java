package space.fengzheng.cloud.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import space.fengzheng.cloud.exception.PromptException;
import space.fengzheng.cloud.service.TestService;
import space.fengzheng.cloud.vo.UserVo;

@Slf4j
@RestController
public class TestServiceImpl implements TestService {

    @Override
    public UserVo testList() {
        log.info("receive");
        return new UserVo();
    }

    @Override
    public UserVo exception() {
        if (true) {
            throw new PromptException("查询异常");
        }
        log.info("receive");
        return new UserVo();
    }

    @Override
    public UserVo timeout() {
        if (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("receive");
        return new UserVo();
    }
}
