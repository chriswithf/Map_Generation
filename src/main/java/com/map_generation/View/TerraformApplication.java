package com.map_generation.View;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import com.map_generation.Model.Render;
import com.map_generation.Model.Generators.TerrainMaker;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Map;

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

        Map<String, Double> params = (Map<String, Double>) stage.getUserData();

        int octaves = params.get("octaves").intValue();
        double persistence = params.get("persistence");

        Model model = new Model(WIDTH, HEIGHT, octaves, persistence);
        MainWindow mainWindow = new MainWindow(WIDTH, HEIGHT);

        TerrainMaker terrainGenerator = new TerrainMaker(4, 4, WIDTH, HEIGHT);

        Render renderer = new Render(1000, 1000,terrainGenerator,model);


        Controller controller = new Controller(model, mainWindow);

        Scene scene = new Scene(mainWindow, WIDTH, HEIGHT);
        stage.setTitle("Terraforming");
        stage.setScene(scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();

        renderer.start();
    }

    private <T extends Event> void closeWindowEvent(T t) {
        try {
            this.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
