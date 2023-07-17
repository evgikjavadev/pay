package ru.vtb.msa.rfrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.entitytodatabase.PaymentTask;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReqDeser;

import java.util.UUID;

@Repository
public interface PaymentTaskRepository extends JpaRepository<PaymentTask, UUID> {
    @Query("SELECT mdm_id FROM payment_task f WHERE f.mdm_id = :mdm_id")
    String findByMdmId(@Param("mdmId") String str);






}
