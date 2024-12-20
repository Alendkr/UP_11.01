module org.example.up_ocokin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;

    opens org.example.up_ocokin to javafx.fxml; // Открытие пакета для FXML
    opens org.example.up_ocokin.database to javafx.base;

    exports org.example.up_ocokin;
    exports org.example.up_ocokin.database;
}
