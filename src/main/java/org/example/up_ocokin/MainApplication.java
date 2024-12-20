package org.example.up_ocokin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication extends Application {

    private static Stage primaryStage; // Переменная для хранения основного окна приложения

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Logger.getLogger("javafx.fxml").setLevel(Level.SEVERE);
        setRoot("login-view", "Авторизация"); // Устанавливаем начальный FXML и заголовок
        primaryStage.show();
    }

    // Метод для переключения сцен с заголовком
    public static void setRoot(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Если используется CSS-стилизация
        String cssPath = MainApplication.class.getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        primaryStage.setScene(scene);
        primaryStage.setTitle(title); // Устанавливаем заголовок окна
    }

    public static void main(String[] args) {
        launch();
    }
}
