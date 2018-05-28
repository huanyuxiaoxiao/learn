package space.fengzheng.cloud;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@SpringCloudApplication
public class TestConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestConsumerApplication.class).web(true).run(args);
    }
}
