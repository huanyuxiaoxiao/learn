package space.fengzheng.cloud;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TestServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(
                TestServerApplication.class)
                .web(true).run(args);
    }
}
