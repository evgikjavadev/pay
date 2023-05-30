package ru.vtb.msa.rfrm.integration.checking.dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SubjectSegmentType {
    /**
     * ВИП-Клиенты (владельцы пакета «Прайм»).
     */
    VIP("VIP"),

    /**
     * Владельцы пакета «Привилегия» (New).
     */
    PRIVILEGED("Privileged"),

    /**
     * Владельцы пакета «Приоритет».
     */
    PRIORITY("Priority"),

    /**
     * Пенсионеры.
     */
    PENSIONER("Pensioner"),

    /**
     * Зарплатные клиенты пенсионеры.
     */
    PENSIONER_SALARY("PensionerSalary"),

    /**
     * Военные пенсионеры.
     */
    MIL_PENSIONER("milPensioner"),

    /**
     * Зарплатные клиенты военные пенсионеры.
     */
    MIL_PENSIONER_SALARY("MilPensionerSalary"),

    /**
     * Зарплатные Клиенты.
     */
    MASS_SALARY("MassSalary"),

    /**
     * Зарплатные Клиенты корпоративные.
     */
    CORP_SALARY("CorpSalary"),

    /**
     * Корпоративные Клиенты категории А, В, V, L..
     */
    CORPORATE("Corporate"),

    /**
     * Люди дела.
     */
    THIRTY_NINE("39"),

    /**
     * Клиенты категории С, Открытый рынок.
     */
    WALK_IN("WalkIn"),

    /**
     * Молодежь зарплатные.
     */
    YOUNG_SALARY("YoungSalary"),

    /**
     * Молодежь.
     */
    YOUNG("Young"),

    /**
     * Открытый рынок.
     */
    MASS("Mass");

    private final String type;

    SubjectSegmentType(String code) {
        this.type = code;
    }

    public String getType() {
        return type;
    }

    public static String getByPersonSegment(String personSegment) {
        for (SubjectSegmentType segmentType : SubjectSegmentType.values()) {
            if (segmentType.getType().equals(personSegment)) {
                return personSegment;
            }
        }
        return SubjectSegmentType.MASS.getType();
    }
}
