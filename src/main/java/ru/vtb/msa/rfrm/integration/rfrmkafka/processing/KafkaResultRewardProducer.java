package ru.vtb.msa.rfrm.integration.rfrmkafka.processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;

@Slf4j
@RequiredArgsConstructor
public class KafkaResultRewardProducer {
    private final KafkaTemplate<String, PayCoreKafkaModel> template;

    public void sendToResultReward(PayCoreKafkaModel obj) {
        template.sendDefault(obj);
        template.flush();
    }

//    public void sendListHistory(List<PayCoreLinkModel> obj) {
//        obj.stream().map(e -> template.sendDefault(obj)).count();
//        template.flush();
//    }
}
