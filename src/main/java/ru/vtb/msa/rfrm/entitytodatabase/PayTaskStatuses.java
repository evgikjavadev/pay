package ru.vtb.msa.rfrm.entitytodatabase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayTaskStatuses {

    @Column(name = "status")
    private Integer status;

    @Column(name = "description")
    private String description;


}
