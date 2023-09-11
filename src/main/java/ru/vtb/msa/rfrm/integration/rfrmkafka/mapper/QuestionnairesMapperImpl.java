package ru.vtb.msa.rfrm.integration.rfrmkafka.mapper;

import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

import java.time.LocalDateTime;

@Component
public class QuestionnairesMapperImpl implements QuestionnairesMapper {
    @Override
    public EntPaymentTask quesKafkaToQuesModel(QuestionnairesKafkaModel model) {

        //создадим объект с дозаполненными полями
        return EntPaymentTask
                .builder()
                .rewardId(model.getRewardId())
                .mdmId(model.getMdmId())
                .questionnaireId(model.getQuestionnaireId())
                .recipientType(model.getRecipientType())
                .amount(model.getAmount())
                .status(10)
                .sourceQs(model.getSource_qs())
                .accountSystem(null)
                .account(null)
                .createdAt(LocalDateTime.now())
                .processed(false)
                .blocked(0)
                .blockedAt(null)
                .build();
    }
}
