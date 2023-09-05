package ru.vtb.msa.rfrm.integration.rfrmkafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionnairesServiceImpl implements ProcessQuestionnairesService {

    private final EntPaymentTaskActions entPaymentTaskActions;
    private final QuestionnairesMapper mapper;

    @Override
    public void validateFieldsAndSaveTaskToDB(QuestionnairesKafkaModel model) {

        if(checkRequiredFields(model)) {

            // если поля в входящем объекте ок, то обогащаем объект дополнит. полями
            EntPaymentTask entPaymentTask = mapper.quesKafkaToQuesModel(model);

            // проверяем есть ли в pay_payment_task объект с таким rewardId и сохраняем его
            checkAndInsertNewTaskToEntPaymentTask(entPaymentTask);

        }
    }

    private boolean checkRequiredFields(QuestionnairesKafkaModel model) {

        List<Object> checkList = new ArrayList<>();
        checkList.add(model.getRewardId());
        checkList.add(model.getMdmId());
        checkList.add(model.getAmount());
        checkList.add(model.getQuestionnaireId());
        checkList.add(model.getRecipientType());
        checkList.add(model.getSource_qs());
        List<Object> objectListNulls = checkList.stream().filter(Objects::isNull).collect(Collectors.toList());

        if (objectListNulls.size() != 0) {
            log.warn("В задании на оплату не заполнены обязательные поля: {}", objectListNulls);
            return false;
        }
        return true;
    }

    // метод сохраняет объект в pay_payment_task если совпадений по rewardId не найдено
    private void checkAndInsertNewTaskToEntPaymentTask(EntPaymentTask entPaymentTask) {

        if (entPaymentTaskActions.getPaymentTaskByRewardId(entPaymentTask.getRewardId()).size() == 0) {
            entPaymentTaskActions.insertPaymentTaskInDB(entPaymentTask);
        } else {
            log.warn("Задание с таким reward_id уже существует");
        }

    }

}
