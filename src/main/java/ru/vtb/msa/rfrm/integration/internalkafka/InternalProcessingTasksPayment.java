package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.util.enums.Statuses;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class InternalProcessingTasksPayment {
    private final KafkaInternalConfigProperties kafkaInternalConfigProperties;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final ServiceAccounts serviceAccounts;
    @Value("${process.platform.kafka.topic.rfrm_pay_function_result_reward}")
    private String RFRM_PAY_FUNCTION_RESULT_REWARD;

    @Transactional
    @Scheduled(fixedRate = 900*10, initialDelay = 5*1000)
    public void processInternalKafkaTasksPayment() {

            Consumer<String, InternalMessageModel> consumer = new KafkaConsumer<>(kafkaInternalConfigProperties.setInternalConsumerProperties());
            consumer.subscribe(Collections.singletonList(RFRM_PAY_FUNCTION_RESULT_REWARD));
            ConsumerRecords<String, InternalMessageModel> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                        log.info("Received message: " + record.value());
                    });
            consumer.close();

            // Осуществить поиск задач в таблице paymentTask, у которых status=10 (Новая)
            List<String> mdmIdList = entPaymentTaskActions.getEntPaymentTaskByStatus(10);

            Set<String> set = new HashSet<>(mdmIdList);
            List<String> uniqueMdmIdsList = new ArrayList<>(set);

            for (String mdmId: uniqueMdmIdsList ) {
                // Вызов метода /portfolio/active 1503 и обработка ответа
                serviceAccounts.getClientAccounts(mdmId);
            }

    }

    public void sendMessage() {

        // создание объекта для отправки в internal топик rfrm_pay_function_result_reward
        InternalMessageModel internalMessageModel = getInternalMessageModel();

        KafkaProducer<String, InternalMessageModel> producer = new KafkaProducer<>(kafkaInternalConfigProperties.setInternalProducerProperties());
        ProducerRecord<String, InternalMessageModel> record = new ProducerRecord<>(RFRM_PAY_FUNCTION_RESULT_REWARD, internalMessageModel);
        producer.send(record);
        producer.flush();
        producer.close();

        processInternalKafkaTasksPayment();
    }

    private static InternalMessageModel getInternalMessageModel() {
        return InternalMessageModel
                .builder()
                .functionName("function_status_update_reward")
                .status(Statuses.COMPLETED.name())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
