package ru.vtb.msa.rfrm.integration.rfrmkafka.mapper;

import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

//@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuestionnairesMapper {

    EntPaymentTask quesKafkaToQuesModel(QuestionnairesKafkaModel model);

}
