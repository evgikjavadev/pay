package ru.vtb.msa.rfrm.integration.rfrmkafka.service;

import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

public interface FirstProcessQuestionnairesService {
    /**
     * Валидируем поля в сообщении от кафка.
     * @param kafkaModel
     */
    void firstProcessQuestionnaires(QuestionnairesKafkaModel kafkaModel);
}
