package com.map_generation.View;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Map;

/**
 * The TerraformApplication class is responsible for launching the terraforming application.
 * It extends the Application class from JavaFX and provides the start() method to initialize and display the application.
 *
 * @author danieldibella
 */
public class TerraformApplication extends Application {

    /**
     * This method starts the terraforming application.
     * The inputs from the users are read from the stage which was already initialized.
     * Model and Controller are both initialized and scene is generated
     * @param stage the stage, cannot be null
     */
    @Override
    public void start(Stage stage) {

        final int WIDTH = (int) stage.getWidth();
        final int HEIGHT = (int) stage.getHeight();

        Map<String, Double> params = (Map<String, Double>) stage.getUserData();

        int octaves = params.get("octaves").intValue();
        double persistence = params.get("persistence");

        Model model = new Model(WIDTH, HEIGHT, octaves, persistence);
        MainWindow mainWindow = new MainWindow(WIDTH, HEIGHT);

        Controller controller = new Controller(model, mainWindow);

        Scene scene = new Scene(mainWindow, WIDTH, HEIGHT);
        stage.setTitle("Terraforming");
        stage.setScene(scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();
    }

    /**
     * stops the currently running application
     * @param t
     * @param <T>
     */
    private <T extends Event> void closeWindowEvent(T t) {
        try {
            this.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
