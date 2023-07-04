package com.map_generation;

import com.map_generation.Model.Render;
import com.map_generation.View.WelcomeWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * JavaFX App
 * This is the main class of the application and is responsible for starting the
 * application
 * It also creates the model, view and controller and links them together. It
 * also creates the scene and sets the stage
 * As mentioned before this Project is based on the MVC pattern, which means
 * that the model is responsible for the data
 * The view is responsible for the user interface and the controller is
 * responsible for the logic of the application.
 *
 * @author danieldibella
 */
public class Main extends Application {

    private static volatile boolean javaFxLaunched = false;
    private static volatile boolean generationStarted = false;
    private final int WIDTH = 600;
    private final int HEIGHT = 350;

    /**
     * Launches a JavaFX application with specified parameters.
     * This method is used to launch a JavaFX application with the provided
     * application class, window dimensions,
     * and noise generation parameters. If this is the first time the method is
     * called, it initializes the JavaFX platform
     * and launches the application in a separate thread. Subsequent calls will
     * directly launch the application on the
     * JavaFX application thread.
     *
     * @param applicationClass The class extending the JavaFX Application class that
     *                         represents the application to launch.
     * @param width            The width of the application window.
     * @param height           The height of the application window.
     * @param octaves          The number of octaves to be used in noise generation.
     * @param persistence      The persistence value to be used in noise generation.
     * @throws IllegalArgumentException if the applicationClass is null.
     */
    public static void myLaunch(Class<? extends Application> applicationClass, int width, int height, int octaves, double persistence) {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } else { // Next times
            generationStarted = true;
            Platform.runLater(() -> {
                try {
                    Application application = applicationClass.newInstance();
                    Stage primaryStage = new Stage();
                    primaryStage.setHeight(width);
                    primaryStage.setWidth(height);

                    Map<String, Double> params = new HashMap<>();
                    params.put("octaves", (double) octaves);
                    params.put("persistence", persistence);
                    primaryStage.setUserData(params);

                    application.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * The main method is only used to launch the application
     *
     * @param args unused
     */

    public static void main(String[] args) {
        myLaunch(Main.class, 0, 0, 0, 0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeWindow welcomeWindow = new WelcomeWindow();

        Scene scene = new Scene(welcomeWindow, WIDTH, HEIGHT);
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
                    Render.stopAll();
                    Platform.exit();
                }
            } else {
                stage.close(); // close the window if the user confirms
                Platform.exit();
            }
        });
        stage.show();
    }
}
