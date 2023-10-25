package ru.vtb.msa.rfrm.functions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.integration.kafkainternal.KafkaInternalProducer;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaResultRewardProducer;
import ru.vtb.msa.rfrm.integration.util.enums.Statuses;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.batch.ActionEntPaymentTaskRepo;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FunctionPSImpl implements FunctionPS {
    @Value("${function.kafka.topic.rfrm_pay_function_status_update_reward}")
    private String rfrm_pay_function_status_update_reward;
    @Value("${findSizeApplication}")
    private Integer findSizeApplication;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final KafkaResultRewardProducer kafkaResultRewardProducer;
    private final EntPaymentTaskRepository entPaymentTaskRepository;
    private final ActionEntPaymentTaskRepo actionEntPaymentTaskRepo;
    private final KafkaInternalProducer kafkaInternalProducer;

    @PostConstruct
    public void init() {
        startProcessFunctionPS();
    }

    @Override
    public void startProcessFunctionPS() {
        log.info("Initial start function PS. Transfer event hand change status. Передача события о ручной смене статуса задания на оплату");

        // Выбрать из таблицы ent_payment_task N (количество настраивается в конфиге) заданий с processed=false и blocked=0, с сортировкой по blocked_at по возрастанию
        List<EntPaymentTask> paymentTaskList = entPaymentTaskRepository.getRewardIdsByProcessAndBlocked(findSizeApplication);

        // передаем в обработку задачи
        handleTasksList(paymentTaskList);

        // соберем объект для отправки в internal топик rfrm_pay_function_status_update_reward для запуска задания на обработку
        sendRunningMessageInternalTopic();

    }

    private void handleTasksList(List<EntPaymentTask> paymentTaskList) {

        List<Long> setRewardIdList = paymentTaskList.stream().map(EntPaymentTask::getRewardId).distinct().collect(Collectors.toList());

        // Установить для задачи blocked=1 и blocked_at=now()
        actionEntPaymentTaskRepo.updateBlockByRewardIdEqualOne(setRewardIdList);

        for (EntPaymentTask task: paymentTaskList) {

            // Установить для задачи ent_payment_task.processed=true
            entPaymentTaskActions.updateProcessedBPaymentTaskByRewardId(task.getRewardId());

            // Соберем объект для отправки в топик rfrm_pay_result_reward (Core service), указав reward_id = ent_payment_task.reward_id, status=ent_payment_task.status
            PayCoreKafkaModel coreLinkModel = getPayCoreLinkModel(task.getRewardId(), task.getStatus());

            // Отправить сообщение в топик rfrm_pay_result_reward (Core service)
            // добавил условие что задания с статусом 10, 40, 50 не пишутся в топик
            if (coreLinkModel.getStatus() == 20 || coreLinkModel.getStatus() == 30) {
                kafkaResultRewardProducer.sendToResultReward(coreLinkModel);
            }

            // Установить для задачи blocked=0
            actionEntPaymentTaskRepo.updateBlockByRewardIdEqualZero(setRewardIdList);
        }
        //Thread.sleep(sleepMs);
    }
    private void sendRunningMessageInternalTopic() {

        InternalMessageModel internalMessageModel = InternalMessageModel
                .builder()
                .functionName("function_result_reward")
                .status(Statuses.COMPLETED.name())
                .timeStamp(LocalDateTime.now())
                .build();

        kafkaInternalProducer.sendObjectToInternalKafka(rfrm_pay_function_status_update_reward, internalMessageModel);
    }
    private static PayCoreKafkaModel getPayCoreLinkModel(Long rewardId, Integer status) {

        // status_description = Если ent_payment_task.status = 30, то привести к строке (SELECT description FROM dct_status_details WHERE status_details_code = 203),
        // иначе - поле не отправлять
        if (status.equals(DctTaskStatuses.STATUS_REJECTED.getStatus())) {
            String description = DctStatusDetails.ERR_REQUIREMENTS.getDescription();
            return PayCoreKafkaModel
                    .builder()
                    .rewardId(rewardId)
                    .status(status)
                    .statusDescription(description)
                    .build();
        } else {
            return PayCoreKafkaModel
                    .builder()
                    .rewardId(rewardId)
                    .status(status)
                    .build();
        }
    }
}
