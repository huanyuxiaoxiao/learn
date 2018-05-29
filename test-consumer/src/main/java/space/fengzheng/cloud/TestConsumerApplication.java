package space.fengzheng.cloud;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringCloudApplication
public class TestConsumerApplication {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = new SpringApplicationBuilder(TestConsumerApplication.class).web(true).run(args);
        final FeignClientsConfiguration bean = run.getBean(FeignClientsConfiguration.class);
        System.out.println(bean);
    }
}
