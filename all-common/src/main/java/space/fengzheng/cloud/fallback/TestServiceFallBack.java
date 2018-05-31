package space.fengzheng.cloud.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.fengzheng.cloud.service.TestService;

@Slf4j
@Service
public class TestServiceFallBack implements TestService {
    @Override
    public String testList() {
        return new String("查询异常!");
    }

    @Override
    public String exception() {
        return new String("查询异常!");
    }

    @Override
    public String timeout() {
        log.info("timeout");
        return new String("查询异常!");
    }
}
