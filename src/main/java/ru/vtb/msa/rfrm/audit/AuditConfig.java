package ru.vtb.msa.rfrm.audit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vtb.omni.audit.lib.api.resolver.EventInitiatorResolver;
//import ru.vtb.omni.jwt.lib.service.ServletJwtService;
import ru.vtb.omni.jwt.servlet.service.ServletJwtService;

@Configuration
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AuditConfig {

    @Bean()
    @Qualifier("auditTemplateEventInitiatorResolver")
    public EventInitiatorResolver eventInitiatorResolver(ServletJwtService jwtService){
        return new CustomResolver(jwtService);
    }
}
