package com.map_generation;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import com.map_generation.View.MainWindow;
import com.map_generation.View.WelcomeWindow;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * JavaFX App
 * This is the main class of the application and is responsible for starting the application
 * It also creates the model, view and controller and links them together. It also creates the scene and sets the stage
 * As mentioned before this Project is based on the MVC pattern, which means that the model is responsible for the data
 * The view is responsible for the user interface and the controller is responsible for the logic of the application.
 */

public class Main extends Application {

    private final int WIDTH = 900;
    private final int HEIGHT = 900;
    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model(WIDTH,HEIGHT);
        MainWindow mainWindow = new MainWindow(WIDTH,HEIGHT);
        //WelcomeWindow mainWindow2 = new WelcomeWindow();
        Controller controller = new Controller(model, mainWindow);

        Scene scene = new Scene(mainWindow,WIDTH,HEIGHT);
        stage.setTitle("Map Generation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
