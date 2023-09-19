package ru.vtb.msa.rfrm.integration.rfrmkafka.mapper;

import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionnairesMapperImpl implements QuestionnairesMapper {

    @Override
    public List<EntPaymentTask> quesKafkaToQuesModel(List<QuestionnairesKafkaModel> model) {

        List<EntPaymentTask> entPayTaskList = new ArrayList<>();

        for (QuestionnairesKafkaModel elem: model) {
            EntPaymentTask task = EntPaymentTask
                    .builder()
                    .rewardId(elem.getRewardId())
                    .mdmId(elem.getMdmId())
                    .questionnaireId(elem.getQuestionnaireId())
                    .recipientType(elem.getRecipientType())
                    .amount(elem.getAmount())
                    .status(DctTaskStatuses.STATUS_NEW.getStatus())
                    .sourceQs(elem.getSourceQs())
                    .accountSystem(null)
                    .account(null)
                    .createdAt(LocalDateTime.now())
                    .processed(false)
                    .blocked(0)
                    .blockedAt(null)
                    .build();

            entPayTaskList.add(task);
        }

        return entPayTaskList;
    }
}
