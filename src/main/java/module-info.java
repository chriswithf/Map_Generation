module com.map_generation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;


    opens com.map_generation to javafx.fxml;
    exports com.map_generation;
    exports com.map_generation.View;
    opens com.map_generation.View to javafx.fxml;
}