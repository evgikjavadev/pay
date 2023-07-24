//package ru.vtb.msa.rfrm.integration.rfrmcore.consumer;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import ru.vtb.msa.rfrm.entitytodatabase.PayPaymentTask;
//import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;
//import ru.vtb.msa.rfrm.repository.PaymentTaskRepository;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class KafkaListener {
//
//    //private final PaymentTaskRepository repository;
//
//    @org.springframework.kafka.annotation.KafkaListener(topics = "RewardReq", groupId = "group_id")
//    public void consume(@Payload String message) throws IOException, InterruptedException {
//
//        // тестовый объект который приходит из топика RewardReq
//        ObjectRewardReq testObjectRewardreq= ObjectRewardReq
//                .builder()
//                .id(String.valueOf(UUID.randomUUID()))
//                .mdmId("5000015297")
//                .requestId(String.valueOf(UUID.randomUUID()))
//                .recipientType(3)
//                .money(6600.00)
//                .productId("77")
//                .build();
//
//        //создадим объект с дозаполненными полями
//        PayPaymentTask payPaymentTaskDB = PayPaymentTask
//                .builder()
//                //.rewardId(UUID.randomUUID())
//                //.requestId(testObjectRewardreq.getRequestId())
//                .mdmId(testObjectRewardreq.getMdmId())
//                .recipientType(testObjectRewardreq.getRecipientType())
//                .amount(testObjectRewardreq.getMoney())
//                .status(1)    // хардкод: 1
//                .createdAt(LocalDateTime.now())
//                .product_id(testObjectRewardreq.getProductId())
//                .responseSent(false)   // хардкод: false
//                .build();
//
//        //ищем есть ли ранее созданное задание в БД по mdmId
//        Optional<PayPaymentTask> objectPaymentTaskByRewardReq = repository.findByMdmId(testObjectRewardreq.getMdmId());
//
//        // если нет то создаем объект для БД (добавляем недостающие поля)
//        if (objectPaymentTaskByRewardReq.isEmpty()) {
//            repository.save(payPaymentTaskDB);
//        }
//
//    }
//}
