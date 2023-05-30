package ru.vtb.msa.rfrm.integration.checking.dto;

public enum DecisionType {

    /**
     * Разрешено.
     */
    ALLOW,

    /**
     * Отказано.
     */
    DENY,

    /**
     * Требует подтверждения.
     */
    ARBITRATION,

    /**
     * Требует подтверждения.
     */
    DENY_ARB,

    /**
     * Требует подтверждения.
     */
    ARBITRATION_DENY_ARB
}
