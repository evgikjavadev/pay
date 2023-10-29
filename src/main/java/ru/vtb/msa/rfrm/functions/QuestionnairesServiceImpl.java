package ru.vtb.msa.rfrm.functions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;
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
    public void validateFieldsAndSaveTaskToDB(List<CorePayKafkaModel> modelList) {
        List<CorePayKafkaModel> validList = new ArrayList<>();

        for (CorePayKafkaModel elem: modelList) {
            if (checkRequiredFields(elem)) {
                validList.add(elem);
            }
        }

        // если поля в входящем объекте ок, то обогащаем объект дополнит. полями
        List<EntPaymentTask> entPaymentTasks = mapper.quesKafkaToQuesModel(validList);

        // проверяем есть ли в ent_payment_task объект с таким rewardId и сохраняем его
        checkAndInsertNewTaskToEntPaymentTask(entPaymentTasks);

    }

    private boolean checkRequiredFields(CorePayKafkaModel elem) {

        List<Object> checkList = new ArrayList<>();

        checkList.add(elem.getRewardId());
        checkList.add(elem.getQuestionnaireId());
        checkList.add(elem.getMdmId());
        checkList.add(elem.getAmountReward());
        checkList.add(elem.getRecipientTypeId());
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

            if (taskFromDb == null) {
                entPaymentTaskActions.insertPaymentTaskInDB(elem);
            } else {
                log.warn("Task with rewardId {} has in DB", elem.getRewardId());
                log.warn("Задание с таким reward_id уже существует");
            }

        }
    }

}
