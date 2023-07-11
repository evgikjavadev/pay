package ru.vtb.msa.rfrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.entitytodatabase.PaymentTask;

import java.util.UUID;

@Repository
public interface PaymentTaskRepository extends JpaRepository<PaymentTask, UUID> {
//    @Query("UPDATE AccountsDatabaseEntity f SET f.paymentTask f WHERE LOWER(f.name) = LOWER(:name)")
//    PaymentTask retrieveByName(@Param("name") String name);



}
