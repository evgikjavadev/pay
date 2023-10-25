package ru.vtb.msa.rfrm.integration.kafkainternal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import ru.vtb.msa.rfrm.functions.FunctionPS;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;

/** Consumer для внутреннего топика rfrm_pay_function_status_update_reward
 * для записи инфо о ручной смене статусов выплат */
@Slf4j
@RequiredArgsConstructor
public class KafkaInternalConsumerTask {
    private final FunctionPS functionPS;

//    @SneakyThrows
//    @KafkaListener(id = "${function.kafka.consumer.group-id-res}",
//            topics = "${function.kafka.topic.rfrm_pay_function_status_update_reward}",
//            containerFactory = "kafkaListenerInternalResult")
    public void listenFunctionStatusUpdateReward(@Payload InternalMessageModel message, Acknowledgment ack,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                           @Header(KafkaHeaders.OFFSET) int offsets) {
        log.info("Start rfrm-pay processing topic = {}, partition = {}, messages = {}", topic, partition, message);

        functionPS.startProcessFunctionPS();

        ack.acknowledge();

        log.info("Finish rfrm-pay processing topic = {} partition = {}", topic, partition);
    }

}
