package ru.vtb.msa.rfrm.connectionDatabaseJdbc.model;


public enum DctTaskStatuses {

    STATUS_NEW(10, "Новое", "New"),
    STATUS_PAYED(20, "Выплачено", "Payed"),
    STATUS_REJECTED(30, "Отклонено", "Rejected"),
    STATUS_MANUAL_PROCESSING(40, "Ручной разбор", "Manual processing"),
    STATUS_READY_FOR_PAYMENT(50, "К выплате", "Ready for payment");

    private Integer status;
    private String statusBusinessDescription;
    private String statusSystemName;
    DctTaskStatuses(Integer status, String statusBusinessDescription, String statusSystemName) {
        this.status = status;
        this.statusBusinessDescription = statusBusinessDescription;
        this.statusSystemName = statusSystemName;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusBusinessDescription() {
        return statusBusinessDescription;
    }

    public String getStatusSystemName() {
        return statusSystemName;
    }

    public Integer setStatus(Integer status) {
        return status;
    }

    public String setStatusBusinessDescription() {
        return statusBusinessDescription;
    }

    public String setStatusSystemName(String statusSystemName) {
        return statusSystemName;
    }

    DctTaskStatuses() {
    };

}
