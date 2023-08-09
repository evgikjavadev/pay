package ru.vtb.msa.rfrm.integration.rfrmcore.consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListener {
    private final UUID exampleRewardIdFromKafka = UUID.randomUUID();
    private final String mdmId = "5000015297";
    private final UUID questionnaireId = UUID.randomUUID();
    private final Integer recipientType = 3;
    private final Double amount = 6600.00;

    private final EntPaymentTaskActions entPaymentTaskActions;

    @SneakyThrows
    @org.springframework.kafka.annotation.KafkaListener(topics = "RewardReq", groupId = "group_id")
    public void consume(@Payload String message) {

        // тестовый объект который приходит из топика RewardReq
        ObjectRewardReq testObjectRewardreq = ObjectRewardReq
                .builder()
                .rewardId(exampleRewardIdFromKafka)
                .mdmId(mdmId)
                .questionnaireId(questionnaireId)
                .recipientType(recipientType)
                .amount(amount)
                .build();

        // проверяем наличие всех заполненных полей из топика rewardReq
        if (testObjectRewardreq.getRewardId().equals("")
            || testObjectRewardreq.getMdmId().equals("")
            || testObjectRewardreq.getQuestionnaireId().equals("")
            || testObjectRewardreq.getAmount().equals("")
            || testObjectRewardreq.getRecipientType().equals("")) {

            log.info("В задании на оплату не заполнены обязательные поля: "
                + testObjectRewardreq.getRewardId() + ", "
                + testObjectRewardreq.getMdmId() + ", "
                + testObjectRewardreq.getQuestionnaireId() + ", "
                + testObjectRewardreq.getAmount() + ", "
                + testObjectRewardreq.getRecipientType() + ", "
            );


        }

        //ищем есть ли ранее созданное задание в БД по rewardId
        List<EntPaymentTask> paymentTaskByRewardId = entPaymentTaskActions.getPaymentTaskByRewardId(testObjectRewardreq.getRewardId());

        if (paymentTaskByRewardId != null) {
            log.info("Задание с таким reward_id уже существует");
            throw new IOException("Задание с таким reward_id уже существует");
        } else {
            //создадим объект с дозаполненными полями
            EntPaymentTask entPaymentTaskDB = EntPaymentTask
                    .builder()
                    .rewardId(testObjectRewardreq.getRewardId())
                    .mdmId(testObjectRewardreq.getMdmId())
                    .questionnaireId(testObjectRewardreq.getQuestionnaireId())
                    .recipientType(testObjectRewardreq.getRecipientType())
                    .amount(testObjectRewardreq.getAmount())
                    .status(1)
                    .accountSystem(null)
                    .account(null)
                    .createdAt(LocalDateTime.now())
                    .build();

            // сохраняем объект в БД
            entPaymentTaskActions.insertPaymentTaskInDB(entPaymentTaskDB);

        }

    }
}
