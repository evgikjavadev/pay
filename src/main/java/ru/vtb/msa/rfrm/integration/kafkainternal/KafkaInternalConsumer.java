package ru.vtb.msa.rfrm.integration.kafkainternal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.service.ProcessQuestionnairesService;
import ru.vtb.msa.rfrm.integration.util.enums.Statuses;
import ru.vtb.msa.rfrm.processingDatabase.batch.ActionEntPaymentTaskRepo;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class KafkaInternalConsumer {
    @Value("${findSizeApplication}")
    private Integer findSizeApplication;
    @Value("${sleep-task-payment}")
    private Integer sleepMs;
    @Value("function.kafka.topic.rfrm_pay_function_result_reward")
    private String rfrm_pay_function_result_reward;
    private final EntPaymentTaskRepository entPaymentTaskRepository;
    private final ActionEntPaymentTaskRepo actionEntPaymentTaskRepo;
    private final ServiceAccounts serviceAccounts;
    private final KafkaInternalProducer kafkaInternalProducer;

    @SneakyThrows
//    @KafkaListener(id = "${function.kafka.consumer.group-id}",
//            topics = "${function.kafka.topic.rfrm_pay_function_result_reward}",
//            containerFactory = "kafkaListenerInternalResult")
    public void listenResultRewardInternal(@Payload InternalMessageModel message, Acknowledgment ack,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                           @Header(KafkaHeaders.OFFSET) int offsets) {
        log.info("Start rfrm-pay processing topic = {}, partition = {}, messages = {}", topic, partition, message);
        ack.acknowledge();

        // Осуществить поиск N заданий в таблице paymentTask, у которых status=10 (Новая) и blocked=0, с сортировкой по blocked_at возрастанию
        List<EntPaymentTask> entPaymentTaskList = entPaymentTaskRepository.findByStatus(DctTaskStatuses.STATUS_NEW.getStatus(), findSizeApplication);

        // передаем в обработку List задач
        handleMdmIdList(entPaymentTaskList);

        sendRunningMessageInternalTopic();

        log.info("Finish rfrm-pay processing topic = {} partition = {}", topic, partition);

    }

    @SneakyThrows
    private void handleMdmIdList(List<EntPaymentTask> entPaymentTaskList) {

        List<Integer> setRewardIdList = entPaymentTaskList.stream().map(EntPaymentTask::getRewardId).distinct().collect(Collectors.toList());

        //Установить для задачи blocked=1 и blocked_at=now()
        actionEntPaymentTaskRepo.updateBlockByRewardIdEqualOne(setRewardIdList);

        for (EntPaymentTask elem: entPaymentTaskList) {
            // получаем счета клиента из 1503 для каждого mdmId и rewardId
            serviceAccounts.getClientAccounts(elem.getMdmId(), elem.getRewardId());
        }

        // Установить для задачи blocked=0 и blocked_at=now()
        actionEntPaymentTaskRepo.updateBlockByRewardIdEqualZero(setRewardIdList);

        //Thread.sleep(sleepMs);
    }

    private void sendRunningMessageInternalTopic() {

        InternalMessageModel internalMessageModel = InternalMessageModel
                .builder()
                .functionName("function_result_reward")
                .status(Statuses.COMPLETED.name())
                .timeStamp(LocalDateTime.now())
                .build();

        kafkaInternalProducer.sendObjectToInternalKafka(rfrm_pay_function_result_reward, internalMessageModel);
    }
}
