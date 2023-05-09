package com.map_generation;

import com.map_generation.View.WelcomeWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

/**
 * JavaFX App
 * This is the main class of the application and is responsible for starting the application
 * It also creates the model, view and controller and links them together. It also creates the scene and sets the stage
 * As mentioned before this Project is based on the MVC pattern, which means that the model is responsible for the data
 * The view is responsible for the user interface and the controller is responsible for the logic of the application.
 * @author danieldibella
 */
public class Main extends Application {

    private final int WIDTH = 600;
    private final int HEIGHT = 500;

    private static volatile boolean javaFxLaunched = false;
    private static volatile boolean generationStarted = false;

    /**
     * This methode makes it possible to call launch() more than one time.
     * @param applicationClass the Application we want to launch
     */
    public static void myLaunch(Class<? extends Application> applicationClass, int width, int height) {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(()->Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } else { // Next times
            generationStarted = true;
            Platform.runLater(()->{
                try {
                    Application application = applicationClass.newInstance();
                    Stage primaryStage = new Stage();
                    primaryStage.setHeight(width);
                    primaryStage.setWidth(height);
                    application.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeWindow welcomeWindow = new WelcomeWindow();

        Scene scene = new Scene(welcomeWindow,WIDTH,HEIGHT);
        stage.setTitle("Map Generation");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            if (generationStarted) {
                e.consume(); // consume the event to prevent the window from closing
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Close");
                alert.setHeaderText("Are you sure you want to close all the windows?");
                alert.setContentText("Any unsaved changes will be lost.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    stage.close(); // close the window if the user confirms
                    Platform.exit();
                }
            } else {
                stage.close(); // close the window if the user confirms
                Platform.exit();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        myLaunch(Main.class,0,0);
    }
}
