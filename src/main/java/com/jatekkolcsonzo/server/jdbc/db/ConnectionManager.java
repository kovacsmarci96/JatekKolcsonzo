package com.jatekkolcsonzo.server.jdbc.db;

import java.sql.*;

/**
 * @author Marton Kovacs
 * @since 2019-10-28
 */
public class ConnectionManager {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dev_jatekkolcsonzo";
    private static final String USER = "root";
    private static final String PASS = "Misike96";

    private Connection connection;

    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        }
        return connection;
    }
}
