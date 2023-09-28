package ru.vtb.msa.rfrm.integration.rfrmkafka.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.util.Assert;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapperImpl;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class QuestionnairesServiceImplTest {
    List<QuestionnairesKafkaModel> questionnairesKafkaModelsList = new ArrayList<>();
    List<EntPaymentTask> entPaymentTaskList = new ArrayList<>();
    @Mock
    private EntPaymentTaskActions entPaymentTaskActions;

    @InjectMocks
    private QuestionnairesServiceImpl service;

    @Mock
    private QuestionnairesMapper mapper;

    @Mock
    private EntPaymentTaskRepository entPaymentTaskRepository;


    @BeforeEach
    void setUp() {
        mapper = new QuestionnairesMapperImpl();
        questionnairesKafkaModelsList = createList();
        entPaymentTaskList = mapper.quesKafkaToQuesModel(questionnairesKafkaModelsList);
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

    /** все поля у всех объектов аполнены корректно -> сохраняем в БД */
    @Test
    void validateFieldsAndSaveTaskToDB() throws SQLException {



//        when(mapper.quesKafkaToQuesModel(questionnairesKafkaModelsList)).thenReturn(anyList());
        //when(entPaymentTaskRepository.findByRewardId(UUID.randomUUID()).getRewardId()).thenReturn(null);



        // Act
        //service.validateFieldsAndSaveTaskToDB(questionnairesKafkaModelsList);

        //entPaymentTaskList = mapper.quesKafkaToQuesModel(questionnairesKafkaModelsList);

        //assertEquals(20, entPaymentTaskList.size());

//        verify(mapper, times(1)).quesKafkaToQuesModel(anyList());
//        verify(entPaymentTaskActions, times(1)).insertPaymentTaskInDB(any(EntPaymentTask.class));



    }


}