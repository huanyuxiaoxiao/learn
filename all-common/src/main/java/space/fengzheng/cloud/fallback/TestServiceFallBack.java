package space.fengzheng.cloud.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.fengzheng.cloud.service.TestService;
import space.fengzheng.cloud.vo.UserVo;

@Slf4j
@Service
public class TestServiceFallBack implements TestService {
    @Override
    public UserVo testList() {
        return new UserVo();
    }

    @Override
    public UserVo exception() {
        return new UserVo();
    }

    @Override
    public UserVo timeout() {
        log.info("timeout");
        return new UserVo();
    }
}
