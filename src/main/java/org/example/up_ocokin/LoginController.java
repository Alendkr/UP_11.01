package org.example.up_ocokin;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.up_ocokin.database.DatabaseConnection;
import org.example.up_ocokin.MainApplication;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Проверка логина и пароля в базе данных
        if (isValidCredentials(username, password)) {
            // Если логин и пароль правильные, переход к основному окну
            System.out.println("Успешный вход!");

                MainApplication.setRoot("menu-view", "Главное меню");

            // Здесь можно закрыть окно авторизации и открыть основное окно
        } else {
            // Если ошибка, показываем сообщение об ошибке
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ошибка авторизации");
            alert.setHeaderText(null);
            alert.setContentText("Неверный логин или пароль.");
            alert.showAndWait();
        }
    }

    private boolean isValidCredentials(String username, String password) {
        String query = "SELECT COUNT(*) FROM Users WHERE Username = ? AND Password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Если count > 0, то пользователь найден
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Если ошибка или нет таких данных
    }
}