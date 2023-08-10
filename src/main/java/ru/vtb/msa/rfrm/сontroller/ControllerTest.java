package ru.vtb.msa.rfrm.сontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    //private final KafkaTemplate<Object, byte[]> kafkaTemplate;
    private static final String TOPIC = "RewardReq";

    @GetMapping("/getaccounts")
    public String getAccounts() throws JSONException {

        serviceAccounts.getClientAccounts();
        return "Accounts for clients are received !";
    }

//    private final RewardSerializer rewardSerializer;

    /*** Тест получения объекта из топика RewardReq кафка */
    @PostMapping("/publish")
    public String publishMessage() {

        QuestionnairesKafkaModel questionnairesKafkaModel = QuestionnairesKafkaModel
                .builder()
                .rewardId(UUID.randomUUID())
                .mdmId("5000015297")
                .questionnaireId(UUID.randomUUID())
                .recipientType(3)
                .source_qs("sourceQS")
                .amount(690.00)
                .build();

        String bootstrapServers = "127.0.0.1:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<String, QuestionnairesKafkaModel> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 50; i++) {
            ProducerRecord<String, QuestionnairesKafkaModel> producerRecord;

            producerRecord = new ProducerRecord<>(TOPIC, questionnairesKafkaModel);

            log.info("sent i = " + i);
            // send data - asynchronous
            producer.send(producerRecord);
        }

        // flush data - synchronous
        producer.flush();

        // flush and close producer
        producer.close();

        return "Object published in topic successfully!";
    }

    /** Тест сохранения в БД нового задания через JDBC */
//    @GetMapping("/savetask")
//    public void saveNewTask() {
//        serviceAccounts.saveNewTaskToPayPaymentTask();
//    }

}
