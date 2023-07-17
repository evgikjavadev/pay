package ru.vtb.msa.rfrm.integration.personaccounts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Класс для поиска полей в MASTER_ACCOUNT и их обработки */
public class SearchMasterAccount {

    private static final String MAIN_TEMPLATE_MASTER_ACCOUNT = "\".*MASTER_ACCOUNT-\\d+.*:\\s*\\s*[\\w{}а-яА-Я\\.\"\\,\\s*:\\_\\d-]+_ACCOUNT-\\d";
    private static final String TEMPLATE_ENTITY_TYPE = "\"entityType\"\\s*:\\s*\"MASTER_ACCOUNT\"";
    private static final String TEMPLATE_BALANCE_RUB = "\"balance\"\\s*:\\s*\\{\\s*\"currency\"\\s*:\\s*\"RUB\"";
    private static final String TEMPLATE_IS_ARRESTED = "\"isArrested\"\\s*:\\s*false";

    /*** в методе получаем данные счета MASTER_ACCOUNT клиента */
    public static void getMainStringAccounts(String accounts) {

        Pattern multilinePattern = Pattern.compile(MAIN_TEMPLATE_MASTER_ACCOUNT, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(accounts);

        while (matcher.find()) {
            String group = matcher.group();
            String mainString = getEntityTypeField(new StringBuilder(group), TEMPLATE_ENTITY_TYPE);
            String mainString1 = getBalanceField(new StringBuilder(group), TEMPLATE_BALANCE_RUB);
            String mainString2 = getIsArrestedField(new StringBuilder(group), TEMPLATE_IS_ARRESTED);

            //в условии проверяем нахождение полей entityType и balance и дополнит проверяем isArrested = false
            if (!mainString.isEmpty() && !mainString1.isEmpty() && !mainString2.isEmpty()) {
                //Сохранить в БД account.number (в поле paymentTask.account), account.entitySubSystems (в поле paymentTask.account_system)
                System.out.println("8632 save in DB account.number ...");   //todo
            }

        }

    }

    /** В методе ищем в данных счета поле entityType: MASTER_ACCOUNT */
    private static String getEntityTypeField(StringBuilder str, String strPattern) {
        Pattern multilinePattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /** В методе ищем в данных счета поле balance: RUB */
    private static String getBalanceField(StringBuilder str, String strPattern) {
        Pattern multilinePattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /** В методе ищем в данных счета поле isArrested: false */
    private static String getIsArrestedField(StringBuilder str, String strPattern) {
        Pattern multilinePattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

}
