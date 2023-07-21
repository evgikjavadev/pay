//package ru.vtb.msa.rfrm.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import ru.vtb.msa.rfrm.entitytodatabase.PaymentTask;
//import ru.vtb.msa.rfrm.entitytodatabase.TaskStatusHistory;
//
//import java.util.UUID;
//
//@Repository
//public interface TaskStatusHistoryRepository extends JpaRepository<TaskStatusHistory, Long> {
////    @Query("UPDATE AccountsDatabaseEntity f SET f.paymentTask f WHERE LOWER(f.name) = LOWER(:name)")
////    PaymentTask retrieveByName(@Param("name") String name);
//
//
//    @Override
//    <S extends TaskStatusHistory> S save(S entity);
//}
