package ru.vtb.msa.rfrm.integration.rfrmkafka.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class QuestionnairesMapperImplTest {

    List<CorePayKafkaModel> corePayKafkaModelsList = new ArrayList<>();
    List<EntPaymentTask> entPaymentTaskList = new ArrayList<>();
    private QuestionnairesMapper mapper;

    @BeforeEach()
    void setUp() {
        corePayKafkaModelsList = createList();
        mapper = new QuestionnairesMapperImpl();
    }

    List<CorePayKafkaModel> createList() {
        for (int i = 0; i < 20; i++) {
            CorePayKafkaModel corePayKafkaModelObject = createQuestionnairesKafkaModelObject();
            corePayKafkaModelsList.add(corePayKafkaModelObject);
        }
        return corePayKafkaModelsList;
    }

    CorePayKafkaModel createQuestionnairesKafkaModelObject() {
        Random random = new Random();

        return CorePayKafkaModel
                .builder()
                .rewardId(random.nextInt())
                .mdmId(random.nextLong())
                .questionnaireId(UUID.randomUUID())
                .recipientType(3)
                .sourceQs("CC")
                .amount(BigDecimal.valueOf(random.nextDouble()))
                .createDate(LocalDateTime.now())
                .build();

    }

    @Test
    void validateFieldsAndSaveTaskToDB() {
        entPaymentTaskList = mapper.quesKafkaToQuesModel(corePayKafkaModelsList);
        assertEquals(corePayKafkaModelsList.size(), entPaymentTaskList.size());

    }

}