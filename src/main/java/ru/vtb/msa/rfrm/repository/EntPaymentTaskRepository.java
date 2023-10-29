package ru.vtb.msa.rfrm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface EntPaymentTaskRepository extends CrudRepository<EntPaymentTask, Long> {
//    @Query("UPDATE ent_payment_task SET blocked = :block, blocked_at = :time where questionnaire_id  in(:ids)")
//    void updateBlockedByRewardId(@Param("block")Integer block, @Param("time") Timestamp time, @Param("ids") List<UUID> rewardIdList);

    @Query("SELECT * FROM ent_payment_task where reward_id =:rewardId")
    EntPaymentTask findByRewardId(@Param("rewardId") Long rewardId);

    @Query("SELECT * FROM ent_payment_task where status = :status and blocked = 0 ORDER BY blocked_at ASC limit :size")
    List<EntPaymentTask> findByStatus(@Param("status") Integer status, @Param("size") Integer size);

//    @Query("UPDATE ent_payment_task SET blocked =:block and blocked_at =:time where reward_id  in (:ids)")
//    void updateListUuids(@Param("block")Integer block, @Param("time") Timestamp time, @Param("ids") List<UUID> idList);

    @Query("SELECT * FROM ent_payment_task WHERE status in (:status) AND processed = false AND blocked = 0 ORDER BY blocked_at ASC limit :size")
    List<EntPaymentTask> getRewardIdsByProcessAndBlocked(@Param("status") List<Integer> status, @Param("size") Integer size);

    @Query("SELECT * FROM ent_payment_task where questionnaire_id =: questionnaireId")
    EntPaymentTask findByQuestionnaireId(@Param("rewardId") UUID questionnaireId);
}
