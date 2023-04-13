package com.map_generation.Controller;

import com.map_generation.Model.GenerateSimplexTiles;
import com.map_generation.Model.Model;
import com.map_generation.View.MainWindow;

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

        // Event handlers
        //##################################################################################################
        mainWindow.getCanvas().onMouseClickedProperty().set(e -> {


            model.editMap(mainWindow.getMouseRadius(), mainWindow.getMouseEditDirection(), e.getX(), e.getY());;

            mainWindow.getCanvas().draw();

        });

        mainWindow.getCanvas().draw();


    }


}
