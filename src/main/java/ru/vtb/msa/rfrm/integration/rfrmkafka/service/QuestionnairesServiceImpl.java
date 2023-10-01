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
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionnairesServiceImpl implements ProcessQuestionnairesService {

    private final EntPaymentTaskActions entPaymentTaskActions;
    private final QuestionnairesMapper mapper;

    private final EntPaymentTaskRepository entPaymentTaskRepository;

    @Override
    public void validateFieldsAndSaveTaskToDB(List<QuestionnairesKafkaModel> modelList) {
        List<QuestionnairesKafkaModel> validList = new ArrayList<>();

        for (QuestionnairesKafkaModel elem: modelList) {
            if (checkRequiredFields(elem)) {
                validList.add(elem);
            }
        }

        if (modelList.size() != 0) {
            // если поля в входящем объекте ок, то обогащаем объект дополнит. полями
            List<EntPaymentTask> entPaymentTasks = mapper.quesKafkaToQuesModel(modelList);

            // проверяем есть ли в pay_payment_task объект с таким rewardId и сохраняем его
            checkAndInsertNewTaskToEntPaymentTask(entPaymentTasks);
        }

    }

    private boolean checkRequiredFields(QuestionnairesKafkaModel elem) {

        List<Object> checkList = new ArrayList<>();

        checkList.add(elem.getRewardId());
        checkList.add(elem.getQuestionnaireId());
        checkList.add(elem.getMdmId());
        checkList.add(elem.getAmount());
        checkList.add(elem.getRecipientType());
        checkList.add(elem.getSourceQs());
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

            EntPaymentTask taskFromDb = entPaymentTaskRepository.findByRewardId(elem.getRewardId());

            if (taskFromDb != null) {
                log.warn("Задание с таким reward_id уже существует");

            } else {
                entPaymentTaskActions.insertPaymentTaskInDB(elem);
            }

        }

    }

}
