package ru.vtb.msa.rfrm.integration.personaccounts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchMasterAccount {

    private static final String MAIN_TEMPLATE_MASTER_ACCOUNT = "\".*MASTER_ACCOUNT-\\d+.*:\\s*\\s*[\\w{}а-яА-Я\\.\"\\,\\s*:\\_\\d-]+_ACCOUNT-\\d";

    private static final String TEMPLATE_ENTITY_TYPE = "\"entityType\"\\s*:\\s*\"MASTER_ACCOUNT\"";
    private static final String TEMPLATE_BALANCE_RUB = "\"balance\"\\s*:\\s*\\{\\s*\"currency\"\\s*:\\s*\"RUB\"";
    private static final String TEMPLATE_IS_ARRESTED = "\"isArrested\"\\s*:\\s*false";
    private final String mainStringOfAccounts;

    public SearchMasterAccount(String mainStringOfAccounts) {
        this.mainStringOfAccounts = mainStringOfAccounts;
    }


    //private static final List<String> SEARCH_MASTER_ACCOUNT = List.of(TEMPLATE_MASTER_ACCOUNT, TEMPLATE_BALANCE_RUB);

//    private static StringBuilder maskFirstNameIfOtherFieldsIsNull_1(StringBuilder str) {
//
//        String mainString = getMainString(str, MAIN_TEMPLATE_MASTER_ACCOUNT);      //выделили текст соответв. паттерну неск строк
//        StringBuilder mainStringBuilder = new StringBuilder(mainString);
//        Pattern patternFirstName = Pattern.compile(PATTERN_FIELD_FIRST_NAME, Pattern.CASE_INSENSITIVE);
//        //Matcher matcherFirstName = patternFirstName.matcher(mainString);
//
//        while (matcherFirstName.find()) {
//            IntStream.rangeClosed(1, matcherFirstName.groupCount()).forEach(elem -> {
//                if (Objects.nonNull(matcherFirstName.group(elem))) {
//                    maskPayloadString(mainStringBuilder, matcherFirstName, elem, matcherFirstName.group(elem));
//                }
//            });
//        }
//
//        Pattern multilinePattern = Pattern.compile(String.join("|", MaskFieldsLogicLastName.FIRST_NAME_WITHOUT_FIELDS_1), Pattern.MULTILINE);
//        Matcher matcher = multilinePattern.matcher(str);
//
//        while (matcher.find()) {
//            str.replace(matcher.start(), matcher.end(), String.valueOf(mainStringBuilder));
//        }
//
//        return new StringBuilder(str);
//    }


    /** В методе ищем в данных счета поле entityType: MASTER_ACCOUNT */
    public static String getEntityTypeField(StringBuilder str, String strPattern) {
        Pattern multilinePattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /** В методе ищем в данных счета поле balance: RUB */
    public static String getBalanceField(StringBuilder str, String strPattern) {
        Pattern multilinePattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /** В методе ищем в данных счета поле isArrested: false */
    public static String getIsArrestedField(StringBuilder str, String strPattern) {
        Pattern multilinePattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /*** в методе получаем данные счета MASTER_ACCOUNT клиента */
    public static void getMainStringAccounts(String accounts) {

        Pattern multilinePattern = Pattern.compile(MAIN_TEMPLATE_MASTER_ACCOUNT, Pattern.CASE_INSENSITIVE);
        Matcher matcher = multilinePattern.matcher(accounts);

        while (matcher.find()) {
            String group = matcher.group();
            String mainString = getEntityTypeField(new StringBuilder(group), TEMPLATE_ENTITY_TYPE);
            String mainString1 = getBalanceField(new StringBuilder(group), TEMPLATE_BALANCE_RUB);
            String mainString2 = getIsArrestedField(new StringBuilder(group), TEMPLATE_IS_ARRESTED);

            //в условии проверяем нахождение полей entityType и balance и дополнит проверяем isArrested
            if (!mainString.isEmpty() && !mainString1.isEmpty() && !mainString2.isEmpty()) {
                //Сохранить в БД account.number (в поле paymentTask.account), account.entitySubSystems (в поле paymentTask.account_system)
                System.out.println("8632 save in DB account.number ...");
            }

        }



    }
}
