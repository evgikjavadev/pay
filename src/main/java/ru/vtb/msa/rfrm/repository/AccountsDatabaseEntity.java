package ru.vtb.msa.rfrm.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class AccountsDatabaseEntity {

    private String paymentTask;
}
