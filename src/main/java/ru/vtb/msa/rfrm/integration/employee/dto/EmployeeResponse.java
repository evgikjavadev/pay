package ru.vtb.msa.rfrm.integration.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    private EmployeeDto Employee;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeDto {
        /**
         * Идентификатор сотрудника
         */
        private String id;

        /**
         * Имя сотрудника
         */
        private String firstName;

        /**
         * Отчетсво сотрудника
         */
        private String patronymic;

        /**
         * Фамилия сотрудника
         */
        private String lastName;

        /**
         * День рождения сотрудника в формате ГГГГ-ММ-ДД
         */
        private String birthDate;

        /**
         * Страховой номер индивидуального лицевого счета
         */
        private String snils;

        /**
         * Логин пользователя
         */
        private String adLogin;

        /**
         * Адрес электронной почты
         */
        private String email;

        /**
         * Распортные данные сотрудника
         */
        private PassportDto passport;

        /**
         * Код точки продажи
         */
        private String pointOfSale;

        /**
         * Данные о должности сотрудника
         */
        private PositionDto position;

        /**
         * Данные о подразделении, в котором числится сотрудник на текущий момент
         */
        private DepartamentDto department;

        /**
         * Табельный номер непосредственного руководителя сотрудника
         */
        private String chiefId;
    }
}