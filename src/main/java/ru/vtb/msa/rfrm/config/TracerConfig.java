package ru.vtb.msa.rfrm.config;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.MDCScopeManager;
import io.opentracing.contrib.java.spring.jaeger.starter.TracerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracerConfig {

    @Bean
    public ExpandByMDCScopeManager expandByMDCScopeManager() {
        return new ExpandByMDCScopeManager();
    }

    static class ExpandByMDCScopeManager implements TracerBuilderCustomizer {
        @Override
        public void customize(JaegerTracer.Builder builder) {
            MDCScopeManager mdcScopeManager = new MDCScopeManager.Builder().build();
            builder.withScopeManager(mdcScopeManager);
        }
    }
}
