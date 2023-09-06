package ru.vtb.msa.rfrm.processingDatabase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChangeStatusInfoLogImpl implements ChangeStatusInfoLog {
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    @Override
    public String getStatusSystemName(Integer statusId) {

        String sql = "SELECT status_system_name FROM dct_task_statuses WHERE status = ?";
        RowMapper<String> rowMapper = (rs, rowNum) -> rs.getString("status_system_name");
        String statusSystemName = jdbcTemplate.queryForObject(sql, new Object[]{statusId}, rowMapper);

        return statusSystemName;
    }

}
