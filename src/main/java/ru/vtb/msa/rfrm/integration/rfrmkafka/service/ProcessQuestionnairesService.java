package ru.vtb.msa.rfrm.integration.rfrmkafka.service;

import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;

import java.sql.SQLException;
import java.util.List;

public interface ProcessQuestionnairesService {
    /**
     * Валидируем поля в сообщении от кафка и сохранияем в БД
     * @param kafkaModel
     */
    void validateFieldsAndSaveTaskToDB(List<QuestionnairesKafkaModel> kafkaModel) throws SQLException;
}
