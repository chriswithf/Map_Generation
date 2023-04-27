package com.map_generation.View;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This Application class will launch the actual terraforming window/s
 *
 * @author danieldibella
 */
public class TerraformApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        final int WIDTH = (int) stage.getWidth();
        final int HEIGHT = (int) stage.getHeight();

        Model model = new Model(WIDTH,HEIGHT);
        MainWindow mainWindow = new MainWindow(WIDTH,HEIGHT);

        Controller controller = new Controller(model, mainWindow);

        Scene scene = new Scene(mainWindow,WIDTH,HEIGHT);
        stage.setTitle("Terraforming");
        stage.setScene(scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();
    }

    private <T extends Event> void closeWindowEvent(T t) {
        try {
            this.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
