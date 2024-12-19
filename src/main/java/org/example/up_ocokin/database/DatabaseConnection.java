package org.example.up_ocokin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=VideoLibrary;encrypt=true;trustServerCertificate=true;useUnicode=true;characterEncoding=UTF-8";
    private static final String USER = "user"; // Ваш SQL Server пользователь
    private static final String PASSWORD = "123456"; // Пароль от пользователя
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при подключении к базе данных", e);
            throw e; // Рекомендуется повторно выбросить исключение
        }

    }
}