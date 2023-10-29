package ru.vtb.msa.rfrm.integration.rfrmkafka.processing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.kafka.core.KafkaTemplate;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;

import javax.validation.constraints.AssertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class KafkaResultRewardProducerTest {

    @Mock
    private KafkaResultRewardProducer producer;

    @Test
    void createObject_whenSendToKafka_thenSendObject() {

        PayCoreKafkaModel payCoreKafkaModel = PayCoreKafkaModel
                .builder()
                .rewardId(1234567l)
                .status(30)
                .statusDescription("Description status ... ")
                .build();

        doNothing().when(producer).sendToResultReward(payCoreKafkaModel);

        producer.sendToResultReward(payCoreKafkaModel);

        verify(producer, times(1)).sendToResultReward(payCoreKafkaModel);
    }
}