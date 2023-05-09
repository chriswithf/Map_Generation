module com.map_generation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires org.fxyz3d.core;


    opens com.map_generation to javafx.fxml;
    exports com.map_generation;
    exports com.map_generation.View;
    opens com.map_generation.View to javafx.fxml;
}