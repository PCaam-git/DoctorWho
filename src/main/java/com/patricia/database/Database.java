package com.patricia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/aa_doctorwho", "root", "tu_contraseña");
    }

    public void close() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}