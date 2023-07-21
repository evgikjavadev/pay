package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionProperty {

    @Value("spring.datasource.url")
    private static String URL;
    @Value("spring.datasource.username")
    private static String USERNAME;
    @Value("spring.datasource.password")
    private static String PASSWORD;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


}
