package ru.vtb.msa.rfrm.toggle;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FeatureToggleProperty.class)
public class FeatureToggleConfig {


    @Bean
    RoleModelToggle RoleModelToggle(FeatureToggleProperty property) {
        return property.getRoleModelToggle();
    }

}