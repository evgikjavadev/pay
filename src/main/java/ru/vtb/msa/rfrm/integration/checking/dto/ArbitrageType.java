package ru.vtb.msa.rfrm.integration.checking.dto;

public enum ArbitrageType {

    /**
     * «Требуется дополнительное согласование» (автоматический арбитраж)
     */
    ARB,

    /**
     * «Отказ с условием» (ручной арбитраж)
     */
    DENY_ARB,

    /**
     * "Дополнительное согласование и отказ с условием" (ручной арбитраж)
     */
    ARB_DENY_ARB,

    /**
     * ???
     */
    ARB_SL_DECISION
}