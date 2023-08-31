package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreLinkModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaProcessingProducerCore;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.processingDatabase.model.EntTaskStatusHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class InternalProcessingStatuses {

    private String topicName = "rfrm_pay_function_result_reward";
    @Value(("${process.platform.kafka.topic.payresultreward}"))
    private String topicPayResultReward;
    private final KafkaProcessingProducerCore kafkaProcessingProducerCore;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final EntTaskStatusHistoryActions entTaskStatusHistoryActions;
    private final KafkaInternalConfigProperties kafkaInternalProperties;

    @Transactional
    public void processInternalKafkaStatus() {

        // Выбрать из таблицы ent_payment_task задания с processed=false
        // и сложить rewardId в List
        List<UUID> rewardIdList = entPaymentTaskActions.getEntPaymentTaskByProcessed(false);

        for (UUID rewardId: rewardIdList) {

            // и update ent_payment_task.processed=true
            entPaymentTaskActions.updateProcessedBPaymentTaskByRewardId(rewardId);

            EntTaskStatusHistory entTaskStatusHistory = createEntTaskStatusHistory(rewardId);

            entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            // Соберем объект для отправки в топик rfrm_pay_result_reward, указав reward_id и status=20
            PayCoreLinkModel coreLinkModel = getPayCoreLinkModel(rewardId);

            // Отправить сообщение в топик rfrm_pay_result_reward
            KafkaProducer<String, PayCoreLinkModel> producerResultPaymentReward = new KafkaProducer<>(kafkaProcessingProducerCore.setPropertiesProducer());
            ProducerRecord<String, PayCoreLinkModel> record = new ProducerRecord<>(topicPayResultReward, coreLinkModel);
            producerResultPaymentReward.send(record);
            producerResultPaymentReward.flush();
            producerResultPaymentReward.close();
        }

        // соберем объект для отправки в топик rfrm_pay_function_result_reward для запуска задания на обработку
        InternalMessageModel internalMessageModel = getInternalMessageModel();

        KafkaProducer<String, InternalMessageModel> producer = new KafkaProducer<>(kafkaInternalProperties.setInternalProducerProperties());
            ProducerRecord<String, InternalMessageModel> record = new ProducerRecord<>(topicName, internalMessageModel);
            producer.send(record);
            log.info("object {} sent successfully to topic {}", internalMessageModel, topicName);
            producer.flush();
            producer.close();

    }

    private static PayCoreLinkModel getPayCoreLinkModel(UUID rewardIdElem) {
        return PayCoreLinkModel
                .builder()
                .rewardId(rewardIdElem)
                .status(20)
                .statusDescription("")   //todo   уточнить что писать
                .build();
    }

    private static InternalMessageModel getInternalMessageModel() {
        return InternalMessageModel
                .builder()
                .functionName("function_status_update_reward")
                .status("completed")
                .timeStamp(LocalDateTime.now())
                .build();
    }

    private static EntTaskStatusHistory createEntTaskStatusHistory(UUID rewardId) {
        return EntTaskStatusHistory
                .builder()
                .rewardId(rewardId)
                .statusDetailsCode(null)
                .taskStatus(2)
                .statusUpdatedAt(LocalDateTime.now())
                .build();
    }

}
