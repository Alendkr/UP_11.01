package org.example.up_ocokin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.up_ocokin.database.DatabaseConnection;
import org.example.up_ocokin.database.Actor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ActorsController {

    @FXML
    private TableView<Actor> actorsTable;

    @FXML
    private TableColumn<Actor, String> firstNameColumn;

    @FXML
    private TableColumn<Actor, String> lastNameColumn;

    @FXML
    public void initialize() {
        // Настраиваем колонки таблицы, исключаем actorID
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Загружаем данные
        loadActors();
    }

    private void loadActors() {
        ObservableList<Actor> actors = FXCollections.observableArrayList();
        String query = "SELECT ActorID, FirstName, LastName FROM Actors";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int actorID = rs.getInt("ActorID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");

                actors.add(new Actor(actorID, firstName, lastName));
            }

            actorsTable.setItems(actors);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onAddButtonClick() {
        // Создаём диалог для ввода имени актёра
        TextInputDialog firstNameDialog = new TextInputDialog();
        firstNameDialog.setTitle("Добавление актёра");
        firstNameDialog.setHeaderText(null);
        firstNameDialog.setContentText("Введите имя:");

        Optional<String> firstNameResult = firstNameDialog.showAndWait();
        if (firstNameResult.isEmpty()) {
            showAlert("Ошибка", "Имя не может быть пустым!");
            return;
        }
        String firstName = firstNameResult.get();

        // Создаём диалог для ввода фамилии актёра
        TextInputDialog lastNameDialog = new TextInputDialog();
        lastNameDialog.setTitle("Добавление актёра");
        lastNameDialog.setHeaderText(null);
        lastNameDialog.setContentText("Введите фамилию:");

        Optional<String> lastNameResult = lastNameDialog.showAndWait();
        if (lastNameResult.isEmpty()) {
            showAlert("Ошибка", "Фамилия не может быть пустой!");
            return;
        }
        String lastName = lastNameResult.get();

        // Добавляем введённые данные в базу данных
        String query = "INSERT INTO Actors (FirstName, LastName) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.executeUpdate();

            // Обновляем таблицу
            loadActors();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось добавить запись.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onEditButtonClick() {
        // Получаем выбранного актера из таблицы
        Actor selectedActor = actorsTable.getSelectionModel().getSelectedItem();
        if (selectedActor == null) {
            showAlert("Ошибка", "Выберите актера для редактирования.");
            return;
        }

        // Создаём диалог для редактирования имени актера
        TextInputDialog firstNameDialog = new TextInputDialog(selectedActor.getFirstName());
        firstNameDialog.setTitle("Редактирование актера");
        firstNameDialog.setHeaderText(null);
        firstNameDialog.setContentText("Введите имя:");

        Optional<String> firstNameResult = firstNameDialog.showAndWait();
        if (firstNameResult.isEmpty()) {
            return;
        }
        String newFirstName = firstNameResult.get();

        // Создаём диалог для редактирования фамилии актера
        TextInputDialog lastNameDialog = new TextInputDialog(selectedActor.getLastName());
        lastNameDialog.setTitle("Редактирование актера");
        lastNameDialog.setHeaderText(null);
        lastNameDialog.setContentText("Введите фамилию:");

        Optional<String> lastNameResult = lastNameDialog.showAndWait();
        if (lastNameResult.isEmpty()) {
            return;
        }
        String newLastName = lastNameResult.get();

        // Обновляем данные в базе данных
        String query = "UPDATE Actors SET FirstName = ?, LastName = ? WHERE ActorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setInt(3, selectedActor.getActorID());
            stmt.executeUpdate();

            // Обновляем таблицу
            loadActors();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось обновить данные актера.");
        }
    }


    @FXML
    private void onDeleteButtonClick() {
        Actor selectedActor = actorsTable.getSelectionModel().getSelectedItem();
        if (selectedActor == null) {
            showAlert("Ошибка", "Выберите актёра для удаления.");
            return;
        }

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Подтверждение удаления");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Вы уверены, что хотите удалить запись?");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String query = "DELETE FROM Actors WHERE ActorID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, selectedActor.getActorID());
                stmt.executeUpdate();

                // Обновляем таблицу
                loadActors();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }





    @FXML
    private void onBackButtonClick() throws Exception {
        // Переход назад в главное меню
        MainApplication.setRoot("menu-view","Главное меню");
    }
}
