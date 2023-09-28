package ru.vtb.msa.rfrm.integration.rfrmkafka.forcore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreLinkModel;

@Slf4j
@RequiredArgsConstructor
public class KafkaResultRewardProducer {
    private final KafkaTemplate template;

    public void sendToResultReward(PayCoreLinkModel obj) {
        template.sendDefault(obj);
        template.flush();
    }

//    public void sendListHistory(List<PayCoreLinkModel> obj) {       //todo решить будет ли использован List
//        obj.stream().map(e -> template.sendDefault(obj)).count();
//        template.flush();
//    }
}
