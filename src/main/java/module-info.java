module org.example.up_ocokin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.up_ocokin to javafx.fxml;
    exports org.example.up_ocokin;
}