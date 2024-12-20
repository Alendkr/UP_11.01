package org.example.up_ocokin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.up_ocokin.database.DatabaseConnection;
import org.example.up_ocokin.database.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoviesController {
    @FXML
    private TableView<Movie> moviesTable;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, Integer> releaseYearColumn;

    @FXML
    private TableColumn<Movie, Integer> runtimeColumn;

    @FXML
    private TableColumn<Movie, String> genresColumn;

    @FXML
    private TableColumn<Movie, String> countriesColumn;

    @FXML
    private TableColumn<Movie, String> reviewsColumn;

    @FXML
    public void initialize() {
        // Настроим колонки таблицы
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        runtimeColumn.setCellValueFactory(new PropertyValueFactory<>("runtime"));
        genresColumn.setCellValueFactory(new PropertyValueFactory<>("genres"));
        countriesColumn.setCellValueFactory(new PropertyValueFactory<>("countries"));
        reviewsColumn.setCellValueFactory(new PropertyValueFactory<>("reviews"));

        // Загружаем данные
        loadMovies();
    }

    private void loadMovies() {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        String query = "SELECT \n" +
                "    m.MovieID, \n" +
                "    m.Title, \n" +
                "    m.ReleaseYear, \n" +
                "    m.Runtime,\n" +
                "    -- Получение уникальных жанров\n" +
                "    (\n" +
                "        SELECT STRING_AGG(g.GenreName, ', ') \n" +
                "        FROM MovieGenres mg\n" +
                "        INNER JOIN Genres g ON mg.GenreID = g.GenreID\n" +
                "        WHERE mg.MovieID = m.MovieID\n" +
                "    ) AS Genres,\n" +
                "    -- Получение уникальных стран\n" +
                "    (\n" +
                "        SELECT STRING_AGG(c.CountryName, ', ') \n" +
                "        FROM MovieCountries mc\n" +
                "        INNER JOIN Countries c ON mc.CountryID = c.CountryID\n" +
                "        WHERE mc.MovieID = m.MovieID\n" +
                "    ) AS Countries,\n" +
                "    -- Получение всех отзывов\n" +
                "    (\n" +
                "        SELECT STRING_AGG(r.ReviewText, ', ') \n" +
                "        FROM Reviews r\n" +
                "        WHERE r.MovieID = m.MovieID\n" +
                "    ) AS Reviews\n" +
                "FROM Movies m;\n";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int movieID = rs.getInt("MovieID");
                String title = rs.getString("Title");
                int releaseYear = rs.getInt("ReleaseYear");
                int runtime = rs.getInt("Runtime");
                String genres = rs.getString("Genres");
                String countries = rs.getString("Countries");
                String reviews = rs.getString("Reviews");

                movies.add(new Movie(movieID, title, releaseYear, runtime, genres, countries, reviews));
            }

            moviesTable.setItems(movies);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onBackButtonClick() throws Exception {
        // Переход назад в главное меню
        MainApplication.setRoot("menu-view","Главное меню");
    }
}
