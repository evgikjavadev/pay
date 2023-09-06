package ru.vtb.msa.rfrm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.vtb.msa.rfrm.integration.internalkafka.InternalProcessingStatuses;
import ru.vtb.msa.rfrm.integration.internalkafka.InternalProcessingTasksPayment;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.util.Properties;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ControllerTest {
    private final ServiceAccounts serviceAccounts;
    private final InternalProcessingStatuses internalProcessingStatuses;
    private final InternalProcessingTasksPayment internalProcessingTasksPayment;
    @Value("${process.platform.kafka.topic.questionnaires}")
    private String topic;
    @Value("${process.platform.kafka.bootstrap.server}")
    private String bootstrapServers;
    private static final UUID questionnaireId = UUID.randomUUID();

    @GetMapping("/getaccounts")
    public String getAccounts() {
        //todo   решить по какому событию тащим номер счета клиента
        serviceAccounts.getClientAccounts("5000015297");
        return "Accounts for clients are received !";
    }


    /***
     * Тест отправки объекта в топик rewardReq кафка
     * */
    @GetMapping("/publish")
    public String publishMessage() throws InterruptedException {

        // создадим тестовый объект-заглушку кот приходит из кафка топика rewardReq
        QuestionnairesKafkaModel testQuestionnairesKafkaModel = getTestQuestionnairesKafkaModel();

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<String, QuestionnairesKafkaModel> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 1; i++) {
            ProducerRecord<String, QuestionnairesKafkaModel> producerRecord =
                    new ProducerRecord<>(topic, testQuestionnairesKafkaModel);
            log.info("sent object with frequency i = " + i + " " + producerRecord.value());

            producer.send(producerRecord);

        }

        producer.flush();
        producer.close();


//        KafkaProducer<String, String> producerInternal = new KafkaProducer<>(properties);
//        while (true) {
//            ProducerRecord<String, String> recordInternal = new ProducerRecord<>(topicName, message);
//            System.out.println("message sent ... ");
//            producerInternal.send(recordInternal);
//            Thread.sleep(2000);
//        }

        internalProcessingStatuses.processInternalKafkaStatus();   //todo   решить как делать начальный запуск
        internalProcessingTasksPayment.sendMessage();

        return "Object published in topic rewardReq successfully!";
    }

    private static QuestionnairesKafkaModel getTestQuestionnairesKafkaModel() {
        UUID rewardId = UUID.fromString("5f286023-f0ee-4fec-a51c-a16ea4912c5c");
        return QuestionnairesKafkaModel
                .builder()
                    .rewardId(rewardId)
                    .mdmId("5000015297")
                    .questionnaireId(questionnaireId)
                    .recipientType(3)
                    .source_qs("sourceQS")
                    .amount(69000.00)
                .build();
    }

}
