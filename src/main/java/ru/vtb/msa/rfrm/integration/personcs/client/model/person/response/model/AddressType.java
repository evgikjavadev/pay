//package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;
//
///**
// * Тип адреса.
// * <p>
// * Не указан	                    0
// * Постоянной регистрации	        1
// * Временной регистрации	        2
// * Фактический	                    3
// * Адрес регистрации работодателя	4
// * Фактический рабочий	            9
// * Почтовый	                        10
// */
//public enum AddressType {
//    /**
//     * Постоянной регистрации.
//     */
//    REGISTRATION("1"),
//
//    /**
//     * Фактический.
//     */
//    FACT("3");
//
//    private final String typeCode;
//
//    AddressType(String typeCode) {
//        this.typeCode = typeCode;
//    }
//
//    public static boolean isRegAddress(String code) {
//        return REGISTRATION.typeCode.equals(code);
//    }
//
//    public static boolean isFactAddress(String code) {
//        return FACT.typeCode.equals(code);
//    }
//
//    public String getTypeCode() {
//        return typeCode;
//    }
//}
