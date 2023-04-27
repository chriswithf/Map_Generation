package com.map_generation.View;

import com.map_generation.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;


/**
 * This is the Welcome Window class. Here the user is introduced to the Application
 * with all it's functions - how it works.
 * Here the user will also set up the parameters necessary for the Map Generation
 *
 * @author danieldibella
 */

//TODO
public class WelcomeWindow extends VBox {

    private Dimension size;
    private int tileSize;

    public WelcomeWindow(){
        Label title = new Label("Map Generation");
        Button start = new Button("Start Terraforming");


        start.setOnAction(actionEvent -> {
            Main.myLaunch(TerraformApplication.class, 700, 700);
        });

        getChildren().addAll(title, start);
    }
}
