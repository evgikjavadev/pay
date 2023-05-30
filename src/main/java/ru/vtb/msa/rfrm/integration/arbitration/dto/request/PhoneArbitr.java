package ru.vtb.msa.rfrm.integration.arbitration.dto.request;

import lombok.Builder;
import lombok.Data;

/** Данные, полученные из 1442 Карточка ФЛ. */
@Data
@Builder
public class PhoneArbitr {
    private String type;
    private String number;
}
