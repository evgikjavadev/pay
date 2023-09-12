package ru.vtb.msa.rfrm.integration.rfrmkafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionnairesServiceImpl implements ProcessQuestionnairesService {

    private final EntPaymentTaskActions entPaymentTaskActions;
    private final QuestionnairesMapper mapper;

    private final EntPaymentTaskRepository entPaymentTaskRepository;

    @Override
    public void validateFieldsAndSaveTaskToDB(List<QuestionnairesKafkaModel> model) {

        if(checkRequiredFields(model)) {

            // если поля в входящем объекте ок, то обогащаем объект дополнит. полями
            List<EntPaymentTask> entPaymentTasks = mapper.quesKafkaToQuesModel(model);

            // проверяем есть ли в pay_payment_task объект с таким rewardId и сохраняем его
            checkAndInsertNewTaskToEntPaymentTask(entPaymentTasks);

        }
    }

    private boolean checkRequiredFields(List<QuestionnairesKafkaModel> model) {

        List<Object> checkList = new ArrayList<>();
        for (QuestionnairesKafkaModel elem: model) {
            checkList.add(elem.getRewardId());
            checkList.add(elem.getQuestionnaireId());
            checkList.add(elem.getMdmId());
            checkList.add(elem.getAmount());
            checkList.add(elem.getRecipientType());
            checkList.add(elem.getSource_qs());
        }

        List<Object> objectList = checkList.stream().filter(Objects::isNull).collect(Collectors.toList());

        if (objectList.size() != 0) {
            log.warn("В задании на оплату не заполнены обязательные поля: {}", objectList);
            return false;
        }

        return true;
    }

    // метод сохраняет объект в pay_payment_task если совпадений по rewardId не найдено
    private void checkAndInsertNewTaskToEntPaymentTask(List<EntPaymentTask> entPaymentTask) {

        for (EntPaymentTask elem: entPaymentTask ) {

            EntPaymentTask taskfromDb = entPaymentTaskRepository.findByRewardId(elem.getRewardId());

            if (taskfromDb != null) {
                log.warn("Задание с таким reward_id уже существует");

            } else {
                entPaymentTaskActions.insertPaymentTaskInDB(elem);
            }

        }

    }

}
