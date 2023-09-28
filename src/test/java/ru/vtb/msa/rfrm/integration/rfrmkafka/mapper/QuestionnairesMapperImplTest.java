package ru.vtb.msa.rfrm.integration.rfrmkafka.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
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

    List<QuestionnairesKafkaModel> questionnairesKafkaModelsList = new ArrayList<>();
    List<EntPaymentTask> entPaymentTaskList = new ArrayList<>();
    private QuestionnairesMapper mapper;

    @BeforeEach()
    void setUp() {
        questionnairesKafkaModelsList = createList();
        mapper = new QuestionnairesMapperImpl();
    }

    List<QuestionnairesKafkaModel> createList() {
        for (int i = 0; i < 20; i++) {
            QuestionnairesKafkaModel questionnairesKafkaModelObject = createQuestionnairesKafkaModelObject();
            questionnairesKafkaModelsList.add(questionnairesKafkaModelObject);
        }
        return questionnairesKafkaModelsList;
    }

    QuestionnairesKafkaModel createQuestionnairesKafkaModelObject() {
        Random random = new Random();

        return QuestionnairesKafkaModel
                .builder()
                .rewardId(UUID.randomUUID())
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
        entPaymentTaskList = mapper.quesKafkaToQuesModel(questionnairesKafkaModelsList);
        assertEquals(questionnairesKafkaModelsList.size(), entPaymentTaskList.size());

    }

}