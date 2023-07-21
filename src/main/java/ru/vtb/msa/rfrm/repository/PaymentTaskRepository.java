//package ru.vtb.msa.rfrm.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import ru.vtb.msa.rfrm.entitytodatabase.PaymentTask;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public interface PaymentTaskRepository extends JpaRepository<PaymentTask, UUID> {
//
//    @Query("select b from PaymentTask b where b.mdmId=:mdm_id")
//    Optional<PaymentTask> findByMdmId(@Param("mdm_id")String mdm_id);
//
//    @Modifying
//    @Query("Update PaymentTask t SET t.account=:account WHERE t.mdmId=:mdm_id")
//    void updateFieldAccount(@Param("mdm_id") String mdm_id, @Param("account") String account);
//
//}
