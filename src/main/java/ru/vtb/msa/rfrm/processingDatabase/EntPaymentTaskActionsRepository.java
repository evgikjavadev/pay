//package ru.vtb.msa.rfrm.processingDatabase;
//
//import org.springframework.data.jdbc.repository.query.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
//
//public interface EntPaymentTaskActionsRepository extends CrudRepository<EntPaymentTask, Integer> {
//
//    @Query("UPDATE ent_payment_task SET " +
//            "account =: accountNumber, account_system =: accountSystem, status =: status, account_branch =: accountBranch, blocked =: blocked WHERE mdm_id =: mdmId")
//    void updateAccountNumber(@Param("accountNumber") String accountNumber,
//                             @Param("accountSystem") String accountSystem,
//                             @Param("status") Integer status,
//                             @Param("accountBranch") String accountBranch,
//                             @Param("mdmId") Long mdmId,
//                             @Param("blocked") Integer blocked);
//
//}