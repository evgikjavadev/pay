package ru.vtb.msa.rfrm.integration.employee.client;


import ru.vtb.msa.rfrm.integration.employee.dto.EmployeeResponse;

/**
 * Клиент для работы с MC 1777 Карточка сотрудника
 */
public interface EmployeeClient {
    EmployeeResponse getEmployee(String employeeNumber);
}