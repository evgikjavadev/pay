package ru.vtb.msa.rfrm.integration.rfrmkafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionnairesServiceImpl implements ProcessQuestionnairesService {

    private final EntPaymentTaskActions entPaymentTaskActions;
    private final QuestionnairesMapper mapper;

    @Override
    public void validateFieldsAndSaveTaskToDB(QuestionnairesKafkaModel model) {

        if(requiredFields(model)) {

            // если поля в входящем объекте ок, то обогащаем объект дополнит. полями
            EntPaymentTask entPaymentTask = mapper.quesKafkaToQuesModel(model);

            // сохраняем объект в БД
            entPaymentTaskActions.insertPaymentTaskInDB(entPaymentTask);

        }
    }

    private boolean requiredFields(QuestionnairesKafkaModel model) {

        if (ObjectUtils.anyNull(model.getRewardId(), model.getMdmId(), model.getAmount(), model.getQuestionnaireId(),
                model.getRecipientType(), model.getSource_qs())) {
            log.warn("Не все обязательные поля заполнены");
            return false;
        }
        return true;
    }

}
