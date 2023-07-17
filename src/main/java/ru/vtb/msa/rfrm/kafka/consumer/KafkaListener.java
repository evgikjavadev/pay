package ru.vtb.msa.rfrm.kafka.consumer;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReq;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReqDeser;
import ru.vtb.msa.rfrm.kafka.model.RewardDeSerializer;
import ru.vtb.msa.rfrm.repository.PaymentTaskRepository;

import java.io.IOException;
import java.util.UUID;

@Component
public class KafkaListener {

    private final RewardDeSerializer rewardDeSerializer;

    private final PaymentTaskRepository repository;

    public KafkaListener(RewardDeSerializer rewardDeSerializer, PaymentTaskRepository repository) {
        this.rewardDeSerializer = rewardDeSerializer;
        this.repository = repository;
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "RewardReq", groupId = "group_id")
    public void consume(@Payload String message) throws IOException, InterruptedException {

        // тестовый объект который приходит из топика RewardReq
        ObjectRewardReq testObjectRewardreq= ObjectRewardReq
                .builder()
                .id(UUID.fromString("6e49d42c-ea50-4a2d-9426-b77bae55b133"))
                .mdmId("5000015297")
                .requestId(UUID.fromString("6e49d42c-ea50-4a2d-9426-b77bae55b133"))
                .recipientType(3)
                .money(6600.00)
                .productId("77")
                .build();



        //ищем есть ли ранее созданное задание в БД по mdmId
        String mdmId = repository.findByMdmId(testObjectRewardreq.getMdmId());

        // если нет то создаем объект для БД (добавить недостающие поля)


        //сохранить задание в БД если оно не было создано



        System.out.println("my-message = " + mdmId );

    }
}
