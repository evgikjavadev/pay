package ru.vtb.msa.rfrm.integration.arbitration.dto.enums;

public enum StageArbitrage {
    NEW("Новый"),
    CANCEL("Отменен"),
    ON_REVIEW("Передан на рассмотрение"),
    IN_WORK("В работе"),
    REVISION("На доработке"),
    APPROVAL_HEAD("Согласование руководителя"),
    COMPLETED("Завершен"),
    IN_PROCESSING("В обработке"),
    ERROR("Ошибка");
    StageArbitrage(String str) {
    }
}
