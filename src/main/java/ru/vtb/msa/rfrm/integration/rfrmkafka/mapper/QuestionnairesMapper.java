package ru.vtb.msa.rfrm.integration.rfrmkafka.mapper;

import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

import java.util.List;

//@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuestionnairesMapper {

    List<EntPaymentTask> quesKafkaToQuesModel(List<QuestionnairesKafkaModel> model);

}
