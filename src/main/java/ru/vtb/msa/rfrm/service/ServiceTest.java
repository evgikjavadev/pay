package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.entitytodatabase.TaskStatusHistory;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;

import ru.vtb.msa.rfrm.repository.TaskStatusHistoryRepository;
import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTest {
    private final PersonClientAccounts personClientAccounts;

    private final TaskStatusHistoryRepository repository;

    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")
    public void test() {

          try {
              personClientAccounts.getPersonAccounts(sendRequestListAccounts(Collections.singletonList("ACCOUNT")));
          } catch (HttpStatusException e) {
              e.getStatus();
              sendObjectToTaskStatusHistory();
          }

    }

    private void sendObjectToTaskStatusHistory() {
        TaskStatusHistory taskStatusHistory = getTaskStatusHistory();
        repository.save(taskStatusHistory);
    }

    /** Метод собирает объект для сохранения в БД */
    private static TaskStatusHistory getTaskStatusHistory() {
        TaskStatusHistory taskStatusHistory = TaskStatusHistory
                .builder()
                //.taskStatus(e.getStatus().value())
                .errorDetails(101)
                .statusUpdatedAt(LocalDateTime.now())
                .build();
        return taskStatusHistory;
    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
