package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;

import java.sql.*;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ChangeStatusInfoLogImpl implements ChangeStatusInfoLog {
    private final JdbcTemplate jdbcTemplate;
    private final HikariDataSource hikariDataSource;

    @SneakyThrows
    @Override
    public void updateEnumStatuses(Integer status) {
        Connection connection = null;

        String sql = "SELECT status_business_description FROM dct_task_statuses";

        connection = hikariDataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet =
                statement.executeQuery("SELECT * FROM dct_task_statuses WHERE status = " + status);

            while (resultSet.next()) {
                int statusFromDb = resultSet.getInt(1);
                String descriptionFromDb = resultSet.getString(2);
                String systemNameFromDb = resultSet.getString(3);

                DctTaskStatuses.STATUS_NEW.setStatus(statusFromDb);

            }


    }
}
