package ru.vtb.msa.rfrm.audit;

import ru.vtb.omni.audit.core.model.audit.AuditEventCodeModel;
import ru.vtb.omni.audit.core.model.audit.AuditEventModel;
import ru.vtb.omni.audit.core.registration.audit.AuditInMemoryEvents;
import ru.vtb.omni.audit.lib.api.enums.AudLibEventClass;
import ru.vtb.omni.audit.lib.memory.storage.service.audit.AuditEventCodeServiceInMemoryImpl;
import ru.vtb.omni.audit.lib.memory.storage.storage.audit.AuditInMemoryStorage;

import java.util.HashMap;
import java.util.Map;

import static ru.vtb.msa.rfrm.audit.constants.Constants.*;


public class AuditInMemoryRegisterServiceImpl extends AuditEventCodeServiceInMemoryImpl {


    public AuditInMemoryRegisterServiceImpl(AuditInMemoryStorage inMemoryStorage) {
        super(inMemoryStorage);
    }

    @Override
    public Map<AuditEventCodeModel, AuditInMemoryEvents> findAllEventCodes() {

        AuditEventCodeModel codeModel = AuditEventCodeModel.builder()
                    .eventCode(EXAMPLE_EVENT_CODE)
                .eventType(EXAMPLE_EVENT_TYPE)
                .businessObject(EXAMPLE_BUSINESS_OBJECT)
                .businessOperation(SEND_EXAMPLE_BUSINESS_OPERATION)
                .build();
        AuditInMemoryEvents auditInMemoryEvents = new AuditInMemoryEvents(
                AuditEventModel.builder()
                        .eventClass(AudLibEventClass.START)
                        .title(EXAMPLE_TITLE_START)
                        .eventCodeModel(codeModel)
                        .messageTemplate(EXAMPLE_TEMPLATE_START).build(),
                AuditEventModel.builder()
                        .eventClass(AudLibEventClass.SUCCESS)
                        .title(EXAMPLE_TITLE_SUCCESS)
                        .messageTemplate(EXAMPLE_TEMPLATE_SUCCESS).build(),
                AuditEventModel.builder()
                        .eventClass(AudLibEventClass.FAILURE)
                        .title(EXAMPLE_TITLE_FAILURE)
                        .messageTemplate(EXAMPLE_TEMPLATE_FAILURE).build());

        Map map = new HashMap<>();
        map.put(codeModel, auditInMemoryEvents);
        return map;
    }
}
