module com.map_generation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.map_generation to javafx.fxml;
    exports com.map_generation;
}