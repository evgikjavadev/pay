package ru.vtb.msa.rfrm.processingDatabase.batch;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.util.Streamable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ActionEntPaymentTaskRepoImpl implements ActionEntPaymentTaskRepo {

    //private final NamedParameterJdbcTemplate jdbcTemplateName;

    private final JdbcTemplate jdbcTemplate;

    //private final String updateListUuids = "UPDATE ent_payment_task SET blocked = 1, blocked_at = CURRENT_TIMESTAMP where reward_id  in(:ids)";

    //private final String updateListUuidEqualZero = "UPDATE ent_payment_task SET blocked = 0, blocked_at = CURRENT_TIMESTAMP where reward_id  in(:ids)";
    private final String updateListUuidEqualZero = "UPDATE ent_payment_task SET blocked = 0, blocked_at = CURRENT_TIMESTAMP where reward_id = ?";
    private final String updateListUuidEqualOne = "UPDATE ent_payment_task SET blocked = 1, blocked_at = CURRENT_TIMESTAMP where reward_id = ?";

    @Override
    @Transactional
    public void updateBlockByUUIDEqualOne(List<UUID> entities) {

        for (UUID elem: entities) {
            jdbcTemplate.update(updateListUuidEqualOne, elem);
        }
    }


//    @Override
//    @Transactional
//    public void updateBlockByUUIDEqualOne(List<UUID> entities){
//        SqlParameterSource[] batch = getBatch(entities);
//        this.jdbcTemplateName.batchUpdate(updateListUuids, batch);
//    }

    @Override
    @Transactional
    public void updateBlockByUUIDEqualZero(List<UUID> entities){
//        SqlParameterSource[] batch = getBatch(entities);
//        this.jdbcTemplateName.batchUpdate(updateListUuidEqualZero, batch);

        for (UUID elem: entities) {
            jdbcTemplate.update(updateListUuidEqualZero, elem);
        }

    }

    @NotNull
    private static SqlParameterSource[] getBatch(List<UUID> entities) {
        Map<String, List<UUID>> param = new HashMap<>();
        param.put("ids", entities);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(param);
        return batch;
    }

    @NotNull
    private static SqlParameterSource[] getBatch(Iterable<EntPaymentTask> entities) {
        List<EntPaymentTask> list = Streamable.of(entities).stream().collect(Collectors.toList());
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(list.toArray());
        return batch;
    }


}
