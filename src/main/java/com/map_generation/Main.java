package com.map_generation;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import com.map_generation.View.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller();
        Model model = new Model();
        MainView mainView = new MainView(controller, model);
        Scene scene = new Scene(mainView, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
