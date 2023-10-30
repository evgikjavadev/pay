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
import ru.vtb.msa.rfrm.functions.FunctionPD;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;
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
    private final EntPaymentTaskRepository entPaymentTaskRepository;
    private final ActionEntPaymentTaskRepo actionEntPaymentTaskRepo;
    private final ServiceAccounts serviceAccounts;
    private final KafkaInternalProducer kafkaInternalProducer;
    private final FunctionPD functionPD;

    @SneakyThrows
    @KafkaListener(id = "${function.kafka.consumer.group-id}",
            topics = "${function.kafka.topic.rfrm_pay_function_result_reward}",
            containerFactory = "kafkaListenerInternalResult")
    public void listenResultRewardInternal(@Payload InternalMessageModel message, Acknowledgment ack,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                           @Header(KafkaHeaders.OFFSET) int offsets) {
        log.info("Start processing topic = {}, partition = {}, messages = {}", topic, partition, message);

        try {
            functionPD.startFunctionPD();

            kafkaInternalProducer.sendObjectToInternalKafka("rfrm_pay_function_result_reward", createMessageToTopicInternal());

            ack.acknowledge();

        } catch (Exception e) {
        ack.nack(0,100);
            log.error(e.getMessage(), e.fillInStackTrace());
        }

        log.info("Finish processing topic = {} partition = {}", topic, partition);

    }

    private InternalMessageModel createMessageToTopicInternal() {

        return InternalMessageModel
                .builder()
                .functionName("function_result_reward")
                .status(Statuses.COMPLETED.name())
                .timeStamp(LocalDateTime.now())
                .build();
    }

}
