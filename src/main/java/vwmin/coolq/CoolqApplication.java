package vwmin.coolq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CoolqApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CoolqApplication.class, args);
//        ConfigurableApplicationContext app = SpringApplication.run(CoolqApplication.class, args);
//        SpringUtil.setApplicationContext(app);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}
