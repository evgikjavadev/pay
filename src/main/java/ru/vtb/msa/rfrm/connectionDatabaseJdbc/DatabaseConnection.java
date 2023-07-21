package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {


        public void save(Integer account) {
            try (Connection connection = ConnectionProperty.getConnection()) {
                String query = "INSERT INTO pay_payment_task (account) VALUES (?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, account);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


}
