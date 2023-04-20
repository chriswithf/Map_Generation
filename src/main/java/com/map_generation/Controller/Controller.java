package com.map_generation.Controller;

import com.map_generation.Model.GenerateSimplexTiles;
import com.map_generation.Model.Model;
import com.map_generation.View.MainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This is the controller class, it is responsible for the logic of the application.
 * It is responsible for handling events and updating the model and view accordingly.
 * @author Chris
 *
 */

public class Controller {
    private Model model;
    private MainWindow mainWindow;

    public Controller(Model model, MainWindow mainWindow) {
        this.model = model;
        this.mainWindow = mainWindow;


        // Listener for editing the map
        mainWindow.getCanvas().onMouseClickedProperty().set( e -> {
            if(e.getButton().equals(MouseButton.SECONDARY)){
                model.editMap(mainWindow.getMouseRadius(), -1, e.getX(), e.getY());;
            } else if (e.getButton().equals(MouseButton.PRIMARY)) {
                model.editMap(mainWindow.getMouseRadius(), 1, e.getX(), e.getY());;
            }
            mainWindow.getCanvas().draw();

        });

        // Reset cursor when entering canvas
        mainWindow.getCanvas().onMouseEnteredProperty().set( e -> {
            mainWindow.setCursor();
        });

        // Change radius of mouse with '+' and '-'
        mainWindow.onKeyTypedProperty().set(e -> {
            int incDec = 10;
            if (e.getCharacter().equals("+")) {
                mainWindow.incMouseRadius(incDec);
                mainWindow.setCursor();
            } else if (e.getCharacter().equals("-")) {
                mainWindow.decMouseRadius((incDec));
                mainWindow.setCursor();
            }
            System.out.println(mainWindow.getMouseRadius());
        });

        mainWindow.getCanvas().draw();

    }


}
