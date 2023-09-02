package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreLinkModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaProcessingProducerCore;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class InternalProcessingStatuses {

    private final String RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD = "rfrm_pay_function_status_update_reward";
    private final String RFRM_PAY_RESULT_REWARD = "rfrm_pay_result_reward";
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final KafkaProcessingProducerCore kafkaProcessingProducerCore;
    private final KafkaInternalConfigProperties kafkaInternalProperties;

    //private final KafkaTemplate kafkaTemplate;

    @Transactional
    @Scheduled(fixedRate = 1000*10, initialDelay = 5*1000)
    public void processInternalKafkaStatus() {

        // Выбрать из таблицы ent_payment_task задания с processed=false
        List<EntPaymentTask> rewardIdList = entPaymentTaskActions.getEntPaymentTaskByProcessed(false);

        for (EntPaymentTask elem: rewardIdList) {

            // и update ent_payment_task.processed=true
            entPaymentTaskActions.updateProcessedBPaymentTaskByRewardId(elem.getRewardId());

            // Соберем объект для отправки в топик rfrm_pay_result_reward, указав reward_id = ent_payment_task.reward_id, status=ent_payment_task.status
            PayCoreLinkModel coreLinkModel = getPayCoreLinkModel(elem.getRewardId(), elem.getStatus());

            // Отправить сообщение в топик rfrm_pay_result_reward (Core service)    //todo    проконтролить доп св-вa продюсера
            KafkaProducer<String, PayCoreLinkModel> producerResultPaymentReward = new KafkaProducer<>(kafkaProcessingProducerCore.setPropertiesProducer());
            ProducerRecord<String, PayCoreLinkModel> record = new ProducerRecord<>(RFRM_PAY_RESULT_REWARD, coreLinkModel);
            producerResultPaymentReward.send(record);
            producerResultPaymentReward.flush();
            producerResultPaymentReward.close();

        }

        // соберем объект для отправки в internal топик rfrm_pay_function_status_update_reward для запуска задания на обработку
        InternalMessageModel internalMessageModel = getInternalMessageModel();
        sendMessage(internalMessageModel);


        //kafkaTemplate.send(topicStatusUpdate, "bla ... ");



    }

    private void sendMessage(InternalMessageModel internalMessageModel) {
        KafkaProducer<String, InternalMessageModel> producer = new KafkaProducer<>(kafkaInternalProperties.setInternalProducerProperties());
        ProducerRecord<String, InternalMessageModel> record = new ProducerRecord<>(RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD, internalMessageModel);
        producer.send(record);
        log.info("object {} sent successfully to topic {}", internalMessageModel, RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD);
        producer.flush();
        producer.close();
    }

    private static PayCoreLinkModel getPayCoreLinkModel(UUID rewardId, Integer status) {

        // status_description = Если ent_payment_task.status = 30, то привести к строке (SELECT description FROM dct_status_details WHERE status_details_code = 203),
        // иначе - поле не отправлять

        if (status == 30) {
            String description = DctStatusDetails.ERR_REQUIREMENTS.getDescription();
            return PayCoreLinkModel
                    .builder()
                    .rewardId(rewardId)
                    .status(status)
                    .statusDescription(description)
                    .build();
        } else {
            return PayCoreLinkModel
                    .builder()
                    .rewardId(rewardId)
                    .status(status)
                    .build();
        }

    }

    private static InternalMessageModel getInternalMessageModel() {
        return InternalMessageModel
                .builder()
                .functionName("function_status_update_reward")
                .status("completed")
                .timeStamp(LocalDateTime.now())
                .build();
    }

}
