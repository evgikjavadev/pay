package ru.vtb.msa.rfrm.functions;

import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;

import java.sql.SQLException;
import java.util.List;

/** Функция ПЗ. Получение заданий на оплату и сохранение их в БД */
public interface ProcessQuestionnairesService {
    /**
     * Валидируем поля в сообщении от кафка и сохранияем в БД
     * @param kafkaModel
     */
    void validateFieldsAndSaveTaskToDB(List<CorePayKafkaModel> kafkaModel) throws SQLException;
}
