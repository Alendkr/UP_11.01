package org.example.up_ocokin;

import javafx.fxml.FXML;
import java.io.IOException;

public class MenuController {
    @FXML
    private void onMoviesButtonClick() throws IOException {
        MainApplication.setRoot("movies-view","Фильмы");
    }


    @FXML
    private void onActorsButtonClick() throws IOException {
        MainApplication.setRoot("actors-view","Актеры");
    }

    @FXML
    private void onLogoutButtonClick() throws IOException {
        // Логика для выхода из аккаунта (например, возвращение на экран авторизации)
        MainApplication.setRoot("login-view","Авторизация");  // Укажите правильный путь к вашему файлу авторизации
    }



}
