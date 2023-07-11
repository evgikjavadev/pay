package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.person;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Multicarta {
    private String id;
    private String name;
    private Boolean status;
    private Date startDate;
    private Date endDate;
    private String system;
    private String userId;
    private LocalDateTime timeStamp;
    private Double balance;
    private Long miles;
    private List<OptionsOfPackMult> options;

}
