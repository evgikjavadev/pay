package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreLinkModel;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.processingDatabase.model.EntTaskStatusHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InternalProcessing {
    @Value(("${process.platform.kafka.topic.cyclepayment}"))
    private String topicName;

    @Value(("${process.platform.kafka.topic.payresultreward}"))
    private String topicPayResultReward;

    @Value("${process.platform.kafka.bootstrap.server}")
    private String bootstrapServers;

    //private final KafkaProcessingProducerCore kafkaProcessingProducerCore;

    private final EntPaymentTaskActions entPaymentTaskActions;

    private final EntTaskStatusHistoryActions entTaskStatusHistoryActions;

    //private final KafkaProducerInternalConfig internalProperties;


    @Transactional
    public void processInternalKafkaStatusPayment() {

        Properties properties = getProperties();
        // Выбрать из таблицы ent_payment_task задания с processed=false
        // и сложить rewardId в List
        List<UUID> rewardIdList = entPaymentTaskActions.getEntPaymentTaskByProcessed(false);

        for (UUID rewardIdElem: rewardIdList) {

            // и update ent_payment_task.processed=true
            entPaymentTaskActions.updateProcessedBPaymentTaskByRewardId(rewardIdElem);

            EntTaskStatusHistory entTaskStatusHistory = createEntTaskStatusHistory(rewardIdElem);

            entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            // Соберем объект для отправки в кафка топик
            PayCoreLinkModel coreLinkModel = PayCoreLinkModel
                    .builder()
                    .rewardId(rewardIdElem)
                    .status(20)
                    .statusDescription("")   //todo   уточнить что писать
                    .build();

            // Отправить сообщение в топик Kafka rfrm_pay_result_reward, указав reward_id и status=20
            KafkaProducer<String, PayCoreLinkModel> producerResultPaymentReward = new KafkaProducer<>(properties);
            ProducerRecord<String, PayCoreLinkModel> record = new ProducerRecord<>(topicPayResultReward, coreLinkModel);
            producerResultPaymentReward.send(record);
            producerResultPaymentReward.flush();
            producerResultPaymentReward.close();

        }

        // соберем объект для запуска задания на обработку
        InternalMessageModel internalMessageModel = InternalMessageModel
                .builder()
                .functionName("function_status_update_reward")
                .status("completed")
                .timeStamp(LocalDateTime.now())
                .build();

        while (true) {
            KafkaProducer<String, InternalMessageModel> producer = new KafkaProducer<String, InternalMessageModel>(getProperties());
            ProducerRecord<String, InternalMessageModel> record = new ProducerRecord<>(topicName, internalMessageModel);
            producer.send(record);
            producer.flush();
            producer.close();
        }

    }

    @NotNull
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return properties;
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
