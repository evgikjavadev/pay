package ru.vtb.msa.rfrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.quartz.QuartzEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import ru.vtb.msa.rfrm.integration.internalkafka.BootstrapKafkaProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class, QuartzEndpointAutoConfiguration.class,
        DataSourceAutoConfiguration.class })
@ComponentScan("ru.vtb.msa")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableRetry
@EnableScheduling
//@EnableConfigurationProperties(value = {BootstrapKafkaProperties.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}