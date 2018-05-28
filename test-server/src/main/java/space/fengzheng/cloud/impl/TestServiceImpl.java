package space.fengzheng.cloud.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import space.fengzheng.cloud.service.TestService;

@Slf4j
@RestController
public class TestServiceImpl implements TestService {

    @Override
    public String testList() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("receive");
        return "search from db:123123";
    }
}
