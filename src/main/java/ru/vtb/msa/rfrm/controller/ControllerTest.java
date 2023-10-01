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

import ru.vtb.msa.rfrm.integration.internalkafka.InternalProcessingTasksStatuses;
import ru.vtb.msa.rfrm.integration.internalkafka.InternalProcessingTasksPayment;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ControllerTest {
    private final ServiceAccounts serviceAccounts;
//    private final InternalProcessingTasksStatuses internalProcessingTasksStatuses;
//    private final InternalProcessingTasksPayment internalProcessingTasksPayment;
    @Value("${process.platformpay.kafka.topic.rfrm_core_payment_order}")
    private String rfrm_core_payment_order;
    @Value("${process.platformpay.kafka.bootstrap.server}")
    private String bootstrapServers;
    private static final UUID questionnaireId = UUID.randomUUID();

    @GetMapping("/getaccounts")
    public String getAccounts() {

        serviceAccounts.getClientAccounts(5000015297L);
        return "Accounts for clients are received !";
    }


    /**
     * Тест отправки объекта в топик rfrm_core_payment_order (Core)
     * */
    @GetMapping("/publish")
    public String publishMessage() {

        // создадим тестовый объект-заглушку кот приходит из кафка топика rfrm_core_payment_order
        List<QuestionnairesKafkaModel> testQuestionnairesKafkaModel = getTestQuestionnairesKafkaModel();

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<String, List<QuestionnairesKafkaModel>> producer = new KafkaProducer<>(properties);

            ProducerRecord<String, List<QuestionnairesKafkaModel>> producerRecord =
                    new ProducerRecord<>(rfrm_core_payment_order, testQuestionnairesKafkaModel);
            log.info("Object: {} WAS SENT TO TOPIC: rfrm_core_payment_order ", producerRecord.value());

            producer.send(producerRecord);

        producer.flush();
        producer.close();

        return "Object published in topic rfrm_core_payment_order successfully!";
    }

    private List<QuestionnairesKafkaModel> getTestQuestionnairesKafkaModel() {
        Random rand = new Random();
        List<QuestionnairesKafkaModel> messageList = new ArrayList<>();

        //for (int i = 0; i < 1; i++) {
            Integer value = rand.nextInt();
            QuestionnairesKafkaModel build = QuestionnairesKafkaModel
                    .builder()
                    .rewardId(value)
                    .mdmId(5000015297L)
                    .questionnaireId(questionnaireId)
                    .recipientType(3)
                    .sourceQs("CC")
                    .amount(BigDecimal.valueOf(69000.00))
                    .createDate(LocalDateTime.now())
                    .build();

            messageList.add(build);
        //}

        return messageList;
    }

    //сохранение заданий в табл начальное заполнение
    @GetMapping("savetask")
    public String saveTasksInDatabase() {


        return "Test tasks saved successfully";
    }

}
