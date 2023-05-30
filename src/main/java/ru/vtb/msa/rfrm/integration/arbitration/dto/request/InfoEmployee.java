package ru.vtb.msa.rfrm.integration.arbitration.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Данные, полученные из 1777 Карточка сотрудника. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoEmployee {

    /** Идентификатор сотрудника-запустившего продукт: AD-логин. */
    private String id_Employee;

    /** Фамилия сотрудника. */
    private String surname;

    /** Имя сотрудника. */
    private String name;

    /** Отчество сотрудника (при наличии). */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String patronymic;

    /** Наименование ТП. */
    @JsonProperty("TP")
    private String tp;

    /** Подразделение Employee.department.fullName. */
    private String division;

    /** Код подразделения Employee.department.id. */
    private String id_division;
    private String email;

    /** Должность сотрудника. */
    private String position;

}
