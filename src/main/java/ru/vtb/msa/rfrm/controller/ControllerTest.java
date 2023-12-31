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

import ru.vtb.msa.rfrm.functions.FunctionPD;
import ru.vtb.msa.rfrm.integration.kafkainternal.KafkaInternalProducer;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaResultRewardProducer;
import ru.vtb.msa.rfrm.integration.util.enums.Statuses;
import ru.vtb.msa.rfrm.service.ServiceAccounts;
import ru.vtb.msa.rfrm.service.ServiceAccountsInterface;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ControllerTest {
    private final ServiceAccounts serviceAccounts;
    private final KafkaResultRewardProducer kafkaResultRewardProducer;
    private final KafkaInternalProducer kafkaInternalProducer;
    private final ServiceAccountsInterface serviceAccountsInterface;
    private final FunctionPD functionPD;

    @Value("${core.kafka.topic}")
    private String rfrm_core_payment_order;
    @Value("function.kafka.topic.rfrm_pay_function_result_reward")
    private String rfrm_pay_function_result_reward;
    @Value("${core.kafka.bootstrap.server}")
    private String bootstrapServers;

    @GetMapping("/getaccounts")
    public String getAccounts() {

        serviceAccountsInterface.getClientAccounts(5000015293L, 12488L);

        return "Accounts for client are received !";
    }

    @GetMapping("/pd")
    public String callFunctionPD() {
        functionPD.startFunctionPD();
        return "Called Function ПД. Подготовка данных для выплаты вознаграждения участнику РФП successfully";
    }


    /**
     * Отправляем тестовый объект в топик rfrm_core_payment_order (Core), ТЕСТИРУЕМ CONSUMER в Pay
     * */
    @GetMapping("/send_to_rfrm_core_payment_order")
    public String publishMessage() {

        CorePayKafkaModel testCorePayKafkaModel = getTestQuestionnairesKafkaModel(111L);
        //CorePayKafkaModel testCorePayKafkaModel1 = getTestQuestionnairesKafkaModel(3225380L);

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<String, CorePayKafkaModel> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, CorePayKafkaModel> producerRecord =
                new ProducerRecord<>(rfrm_core_payment_order, testCorePayKafkaModel);
        log.info("Object: {} WAS SENT TO TOPIC: rfrm_core_payment_order ", producerRecord.value());

//        ProducerRecord<String, CorePayKafkaModel> producerRecord1 =
//                new ProducerRecord<>(rfrm_core_payment_order, testCorePayKafkaModel1);
//        log.info("Object: {} WAS SENT TO TOPIC: rfrm_core_payment_order ", producerRecord.value());

        producer.send(producerRecord);
        //producer.send(producerRecord1);

        producer.flush();
        producer.close();

        return "Object published in topic rfrm_core_payment_order successfully!";
    }

    /** send test object using app to topic for core */
    @GetMapping("/send_to_core_order_using_app")
    public String sendObjectToRfrmCorePaymentOrder() {
        PayCoreKafkaModel payCoreKafkaModel = PayCoreKafkaModel
                .builder()
                .status(20)
                .rewardId(124684648L)
                .statusDescription("Status description ... ")
                .build();

        kafkaResultRewardProducer.sendToResultReward(payCoreKafkaModel);

        return "Object to topic rfrm_core_payment_order sent! ";
    }

    private CorePayKafkaModel getTestQuestionnairesKafkaModel(Long rewardId) {

        return CorePayKafkaModel
                .builder()
                .rewardId(rewardId)
                .mdmId(2271833341L)
                .questionnaireId(UUID.fromString("9071eee-254d-44c1-9ded-c12bf4ada341"))
                .recipientTypeId(3)
                .rewardTypeId(1)
                .sourceQs("CC")
                .amountReward(BigDecimal.valueOf(5500.00))
                .createDate(LocalDateTime.now())
                .build();
    }

    //сохранение заданий в табл начальное заполнение
    @GetMapping("savetask")
    public String saveTasksInDatabase() {


        return "Test tasks saved successfully";
    }

    /** Собираем и отправляем тестовый объект в внутренний топик rfrm_pay_function_result_reward */
    @GetMapping("/send_to_rfrm_pay_function_result_reward")
    public String sendInternalMessageToFunctionResultReward() {
        InternalMessageModel functionResultReward = InternalMessageModel
                .builder()
                .status(Statuses.COMPLETED.name())
                .functionName("function_result_reward")
                .timeStamp(LocalDateTime.now())
                .build();

        kafkaInternalProducer.sendObjectToInternalKafka("rfrm_pay_function_result_reward", functionResultReward);

        return "Object sent to topic rfrm_pay_function_result_reward successfully!";
    }

    /** Проверка ПРОДЮСЕРА для отправки в топик rfrm_pay_function_status_update_reward */
    @GetMapping("/send_to_rfrm_pay_function_status_update_reward")
    public String sendInternalMessageToRfrmPayFunctionStatusUpdatereward() {
        InternalMessageModel functionResultReward = InternalMessageModel
                .builder()
                .status(Statuses.COMPLETED.name())
                .functionName("function_status_update_reward")
                .timeStamp(LocalDateTime.now())
                .build();

        kafkaInternalProducer.sendObjectToInternalKafka("rfrm_pay_function_status_update_reward", functionResultReward);

        return "Object sent to topic rfrm_pay_function_update_reward successfully!";
    }

    /** Тест ПРОДЮСЕРА отправки объекта в топик rfrm_pay_result_reward */
    @GetMapping("/send_to_rfrm_pay_result_reward")
    public String sendObjectToRfrmPayResultReward() {
        PayCoreKafkaModel object = PayCoreKafkaModel
                .builder()
                .rewardId(1234567L)
                .status(30)
                .statusDescription("Description status ... ")
                .build();

        kafkaResultRewardProducer.sendToResultReward(object);

        return "Object to topic rfrm_pay_result_reward sent successfully!";
    }

}
