package com.map_generation;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import com.map_generation.View.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 * @author Chris
 * This is the main class of the application and is responsible for starting the application
 * It also creates the model, view and controller and links them together. It also creates the scene and sets the stage
 * As mentioned before this Project is based on the MVC pattern, which means that the model is responsible for the data
 * The view is responsible for the user interface and the controller is responsible for the logic of the application.
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        MainWindow mainWindow = new MainWindow();
        Controller controller = new Controller(model, mainWindow);

        //TestView testView = new TestView();
        Scene scene = new Scene(mainWindow,1000,1000);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
