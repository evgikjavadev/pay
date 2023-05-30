package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.match;

import lombok.Data;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.FaultMessage;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.Segment;

import java.util.List;

@Data
public class MatchPersonData {

    /**
     * Информация о найденных клиентах
     */
    private List<Person> person;

    /**
     * Сообщение об ошибке
     */
    private FaultMessage faultMessage;

    @Data
    public static class Person {
        /**
         * Фамилия.
         */
        protected String surName;
        /**
         * Имя.
         */
        protected String firstName;
        /**
         * Отчество.
         */
        protected String middleName;
        /**
         * Дата рождения (в формате MM/DD/YYYY).
         */
        protected String birthDate;
        /**
         * ИНН.
         */
        protected String tin;
        /**
         * Глобальный идентификатор записи Customer HUB.
         * ИД клиента в МДМ (УИК).
         */
        private String partyUId;
        /**
         * Числовая оценка степени похожести кандидата.
         */
        private String matchScore;
        /**
         * Тип клиента.
         */
        private String typePerson;
        /**
         * ОМС.
         */
        private String OMS;
        /**
         * Дата изменения в системе источнике  (в формате MM/DD/YYYY HH24:MI:SS ).
         */
        private String updateDate;

        /**
         * Место рождения.
         */
        private String birthPlace;

        /**
         * Код гражданства.
         */
        private String citizenship;

        /**
         * Код пола.
         */
        private String mf;

        /**
         * Родительный ФИО (в русскоязычном формате).
         */
        private String lfmGenitive;

        /**
         * Дательный ФИО (в русскоязычном формате).
         */
        private String lfmDative;

        /**
         * Творительный ФИО (в русскоязычном формате).
         */
        private String lfmAblative;

        /**
         * Дата создания карточки в системе источнике (в формате MM/DD/YYYY HH24:MI:SS ).
         */
        private String createdExternalSystem;

        /**
         * Код семейного положения.
         */
        private String maritalStatus;

        /**
         * Код типа сотрудника.
         */
        private String employerFlag;

        /**
         * Информация по адресам.
         */
        private List<Address> personalAddress;

        /**
         * Документы клиента.
         */
        private List<IdentityDoc> contactIdentity;

        /**
         * Телефоны клиента.
         */
        private List<Phone> phoneCommunication;

        /**
         * Сегмент.
         */
        private List<Segment> segment;

        /**
         * Сегмент "Чёрные списки".
         */
        private List<BlackListItem> blackList;
    }
}
