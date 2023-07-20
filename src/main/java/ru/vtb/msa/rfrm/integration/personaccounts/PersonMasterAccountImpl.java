package ru.vtb.msa.rfrm.integration.personaccounts;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Класс для поиска полей в MASTER_ACCOUNT и их обработки */
@Component
public class PersonMasterAccountImpl implements PersonMasterAccount {

    private static final String MAIN_TEMPLATE_MASTER_ACCOUNT = "\".*MASTER_ACCOUNT-\\d+.*:\\s*\\s*[\\w{}а-яА-Я\\.\"\\,\\s*:\\_\\d-]+_ACCOUNT-\\d";
    private static final String TEMPLATE_ENTITY_TYPE = "\"entityType\"\\s*:\\s*\"MASTER_ACCOUNT\"";
    private static final String TEMPLATE_BALANCE_RUB = "\"balance\"\\s*:\\s*\\{\\s*\"currency\"\\s*:\\s*\"RUB\"";
    private static final String TEMPLATE_IS_ARRESTED = "\"isArrested\"\\s*:\\s*false";
    private static final String TEMPLATE_NUMBER = "\"number\"\\s*:\\s*\"(.*?)\"";
    private static final String TEMPLATE_NUMBER_ACCOUNT = "\\d+";


    /*** в методе получаем данные счета MASTER_ACCOUNT клиента */


    public void getMainStringAccounts(String accounts, String mdmId) {

        String group = getRequestField(new StringBuilder(accounts), MAIN_TEMPLATE_MASTER_ACCOUNT);
        String mainString = getRequestField(new StringBuilder(group), TEMPLATE_ENTITY_TYPE);
        String mainString1 = getRequestField(new StringBuilder(group), TEMPLATE_BALANCE_RUB);
        String mainString2 = getRequestField(new StringBuilder(group), TEMPLATE_IS_ARRESTED);
        String numberAccount = getRequestField(new StringBuilder(group), TEMPLATE_NUMBER);
        String numberEntityAccount = getRequestField(new StringBuilder(numberAccount), TEMPLATE_NUMBER_ACCOUNT);





//        Pattern multilinePattern = Pattern.compile(MAIN_TEMPLATE_MASTER_ACCOUNT, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = multilinePattern.matcher(accounts);

//        while (matcher.find()) {
//            String group = matcher.group();
//            String mainString = getRequestField(new StringBuilder(group), TEMPLATE_ENTITY_TYPE);
//            String mainString1 = getRequestField(new StringBuilder(group), TEMPLATE_BALANCE_RUB);
//            String mainString2 = getRequestField(new StringBuilder(group), TEMPLATE_IS_ARRESTED);
//            String numberAccount = getRequestField(new StringBuilder(group), TEMPLATE_NUMBER);
//            String numberEntityAccount = getRequestField(new StringBuilder(numberAccount), TEMPLATE_NUMBER_ACCOUNT);
//
//            //в условии проверяем нахождение полей entityType и balance и дополнит проверяем isArrested = false
//            if (!mainString.isEmpty() && !mainString1.isEmpty() && !mainString2.isEmpty()) {
//
//                // достать из счета account number
//
//                // найти запись mdmId нужного клиента
//                updateFieldAccountInDB(numberEntityAccount, mdmId);
//
//                //Сохранить в БД account.number (в поле paymentTask.account), account.entitySubSystems (в поле paymentTask.account_system)
//
//                System.out.println("8632 save in DB account.number ...");   //todo
//            }

//        }

    }

//    /** метод обновляет поле account в БД в таблице payment_task */
//    private void updateFieldAccountInDB(String accountNumber, String mdmId) {
//        repository.updateFieldAccount(mdmId, accountNumber);
//    }


    /** метод ищет нужное поле */
    @Override
    public String getRequestField(StringBuilder str, String strPattern) {
        Pattern multilinePattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }


}
