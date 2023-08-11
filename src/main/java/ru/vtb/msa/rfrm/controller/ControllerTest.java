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
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.util.Properties;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ControllerTest {
    private final ServiceAccounts serviceAccounts;

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
     * Тест отправки объекта в топика RewardReq кафка
     * */
    @GetMapping("/publish")
    public String publishMessage() {

        // создадим тестовый объект-заглушку кот приходит из кафка топика rewardreq
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
            log.info("sent i = " + i + " " + producerRecord.value());
            // send data - asynchronous
            producer.send(producerRecord);

        }

        // flush data - synchronous
        producer.flush();

        // flush and close producer
        producer.close();

        return "Object published in topic successfully!";
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

    /** Тест сохранения в БД нового задания через JDBC */
//    @GetMapping("/savetask")
//    public void saveNewTask() {
//        serviceAccounts.saveNewTaskToPayPaymentTask(EntPaymentTask.builder().rewardId(UUID.randomUUID()).build());
//    }

}
