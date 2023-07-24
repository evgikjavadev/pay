package ru.vtb.msa.rfrm.modeldatabase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayStatusDetails {

    @Column(name = "code")
    private Integer code;

    @Column(name = "description")
    private String description;


}
