package ru.vtb.msa.rfrm.integration.rfrmkafka.mapper;

import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionnairesMapperImpl implements QuestionnairesMapper {

    @Override
    public List<EntPaymentTask> quesKafkaToQuesModel(List<CorePayKafkaModel> model) {

        List<EntPaymentTask> entPayTaskList = new ArrayList<>();

        for (CorePayKafkaModel elem: model) {
            EntPaymentTask task = EntPaymentTask
                    .builder()
                    .rewardId(elem.getRewardId())
                    .mdmId(elem.getMdmId())
                    .questionnaireId(elem.getQuestionnaireId())
                    .recipientType(elem.getRecipientTypeId())
                    .amount(elem.getAmountReward())
                    .status(DctTaskStatuses.STATUS_NEW.getStatus())
                    .sourceQs(elem.getSourceQs())
                    .accountSystem(null)
                    .account(null)
                    .createdAt(LocalDateTime.now())
                    .processed(null)
                    .blocked(0)
                    .blockedAt(LocalDateTime.now())
                    .rewardType(elem.getRewardTypeId())
                    .build();

            entPayTaskList.add(task);
        }

        return entPayTaskList;
    }
}
