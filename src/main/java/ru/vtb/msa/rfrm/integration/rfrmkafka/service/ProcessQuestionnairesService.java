package ru.vtb.msa.rfrm.integration.rfrmkafka.service;

import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

import java.sql.SQLException;

public interface ProcessQuestionnairesService {
    /**
     * Валидируем поля в сообщении от кафка.
     * @param kafkaModel
     */
    void validateFieldsAndSaveTaskToDB(QuestionnairesKafkaModel kafkaModel) throws SQLException;
}
