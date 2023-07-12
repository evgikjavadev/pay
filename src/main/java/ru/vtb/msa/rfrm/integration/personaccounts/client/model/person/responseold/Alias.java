package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responseold;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.Balance;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.Overdraft;

import java.util.Date;
import java.util.List;

public class Alias {

//    /** Пользовательское название продукта */
//    private String alias;

    private Date timestamp;

    private String state;

    private String entitySubSystems;

    private String number;
    private String entityType;
    private String entityId;
    private String entityLocalId;
    private String entityName;
    private String openDate;
    private Balance balance;
    private Boolean mayDebit;
    private Boolean mayCredit;
    private String userId;
    private String organisationUnit;
    private String branch;
    private String externalIdPart1;
    private String publicId;
    private String productId;
    private String favourite;
    private Boolean visible;
    private Boolean vtbPay;
    private Date version;
    private String detailAction;
    private String systemInstance;
    private String status;
    private Overdraft overdraft;
    private Boolean mir;
    private Double blockedAmount;
    private Boolean isArrested;
    private String maskedNumber;
    private Boolean inMigrationProcess;
    private String salePoint;
    private List<String> installmentPlan;










//    /** Блокированная сумма */
//    private Double blockedAmount;
//
//    /** Дата закрытия - pattern: ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ */
//    private Date dateOClose;
//
//    /** Номер счета (техническое поле - не использовать в бизнес-кейсах, нужно использовать поле number) */
//    private String entityId;
//
//    /** Дата последней транзакции */
//    private String entityLastTrans;
//
//    /**  Идентификатор в локальной системе */
//    private String entityLocalId;
//
//    /** Имя продукта */
//    private String entityName;
//
//    /** Планируемая дата закрытия (кредита/срока действия карты/депозита) - pattern: ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ */
//    private Date expireDate;
//
//    /** Внешний ID, чаще всего номер счет/карты (для интеграции с Минервой) */
//    private String externalIdPart1;
//
//    /** Флаг принадлежности к избранным продуктам */
//    private Boolean favourite;
//
//
//    /** признак "ВТБ Pay" */
//    private Boolean vtbPay;
//
//    /** Иконка */
//    private String icon;
//
//    /** Кредитование счета */
//    private Boolean mayCredit;
//
//    /** Дебетование счета */
//    private Boolean mayDebit;
//
//    /** Дата открытия. pattern: ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ */
//    private Date openDate;
//
//    /** Код филиала банка (передается cm.organisationUnit->{branch из справочных таблиц} в формате "00000")
//     готовится к удалению - использовать поле branch */
//    private String organisationUnit;
//
//    /** Код филиала банка (передается cm.organisationUnit->{branch из справочных таблиц} в формате "00000") */
//    private String branch;
//
//    /** Источник загрузки (технический атрибут) */
//    private Source source;
//
//    /** Дата идентификации продукта в Минерве */
//    private String Version;



}
