package ru.vtb.msa.rfrm.processingDatabase.enumentity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@RequiredArgsConstructor
@Data
public class EntityDctStatusDetails {
    @Column("status_details_code")
    private final Integer statusDetailsCode;
    @Column("description")
    private final String description;
}
