package ru.vtb.msa.rfrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@ComponentScan("ru.vtb.msa")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableRetry
public class RfrmRegistrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(RfrmRegistrationApplication.class, args);
    }
}