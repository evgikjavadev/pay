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

import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaResultRewardProducer;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ControllerTest {
    private final ServiceAccounts serviceAccounts;
    private final KafkaResultRewardProducer kafkaResultRewardProducer;
//    private final InternalProcessingTasksStatuses internalProcessingTasksStatuses;
//    private final InternalProcessingTasksPayment internalProcessingTasksPayment;
    @Value("${core.kafka.topic}")
    private String rfrm_core_payment_order;
    @Value("${core.kafka.bootstrap.server}")
    private String bootstrapServers;
    private static final UUID questionnaireId = UUID.randomUUID();

    @GetMapping("/getaccounts")
    public String getAccounts() {

        serviceAccounts.getClientAccounts(5000015297L);
        return "Accounts for clients are received !";
    }


    /**
     * Отправляем тестовый объект в топик rfrm_core_payment_order (Core), тестируем consumer в Pay
     * */
    @GetMapping("/publish")
    public String publishMessage() {

        // создадим тестовый объект-заглушку кот приходит из кафка топика rfrm_core_payment_order
        CorePayKafkaModel testCorePayKafkaModel = getTestQuestionnairesKafkaModel();
        CorePayKafkaModel testCorePayKafkaModel1 = getTestQuestionnairesKafkaModel();

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<String, CorePayKafkaModel> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, CorePayKafkaModel> producerRecord =
                new ProducerRecord<>(rfrm_core_payment_order, testCorePayKafkaModel);
        log.info("Object: {} WAS SENT TO TOPIC: rfrm_core_payment_order ", producerRecord.value());

        ProducerRecord<String, CorePayKafkaModel> producerRecord1 =
                new ProducerRecord<>(rfrm_core_payment_order, testCorePayKafkaModel1);
        log.info("Object: {} WAS SENT TO TOPIC: rfrm_core_payment_order ", producerRecord.value());

        producer.send(producerRecord);
        producer.send(producerRecord1);

        producer.flush();
        producer.close();

        return "Object published in topic rfrm_core_payment_order successfully!";
    }

    private CorePayKafkaModel getTestQuestionnairesKafkaModel() {
        Random rand = new Random();

            Integer value = rand.nextInt();
            CorePayKafkaModel build = CorePayKafkaModel
                    .builder()
                    .rewardId(value)
                    .mdmId(5000015297L)
                    .questionnaireId(questionnaireId)
                    .recipientType(3)
                    .sourceQs("CC")
                    .amount(BigDecimal.valueOf(69000.00))
                    .createDate(LocalDateTime.now())
                    .build();

        return build;
    }

    //сохранение заданий в табл начальное заполнение
    @GetMapping("savetask")
    public String saveTasksInDatabase() {


        return "Test tasks saved successfully";
    }

    /** Собираем и отправляем тестовый объект в топик rfrm_pay_result_reward */
    @GetMapping("/sendtotopicresultreward")
    public String sendObjectFromPayToCore() {
        PayCoreKafkaModel payCoreKafkaModelTest = PayCoreKafkaModel
                .builder()
                .rewardId(21564864)
                .status(30)
                .statusDescription("Описание статуса для 30")
                .build();

        kafkaResultRewardProducer.sendToResultReward(payCoreKafkaModelTest);

        return "Object sent successfully!";
    }

}
