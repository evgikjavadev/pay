//package ru.vtb.msa.rfrm.audit;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import ru.vtb.omni.audit.lib.api.event.AuditLibEventModel;
//import ru.vtb.omni.audit.lib.api.event.audit.EventInitiator;
//import ru.vtb.omni.audit.lib.api.resolver.EventInitiatorResolver;
//import ru.vtb.omni.jwt.servlet.service.ServletJwtService;
//
//@Slf4j
//@RequiredArgsConstructor
//public class CustomResolver implements EventInitiatorResolver {
//
//    private final ServletJwtService jwtService;
//
//    @Override
//    public EventInitiator resolveEventInitiator(AuditLibEventModel auditLibEventModel, Object o) {
//
//        String sub = "rfrmSub";
//        String channel = "rfrmChannel";
//
//        if (jwtService.extractTokenPayloadDto() != null) {
//            sub = jwtService.extractTokenPayloadDto().getSub();
//            channel = jwtService.extractTokenPayloadDto().getChannel();
//        }
//        return new EventInitiator(sub, channel);
//    }
//}