package space.fengzheng.cloud.fallback;

import org.springframework.stereotype.Service;
import space.fengzheng.cloud.service.TestService;

@Service
public class TestServiceFallBack implements TestService {
    @Override
    public String testList() {
        return new String("查询异常!");
    }
}
