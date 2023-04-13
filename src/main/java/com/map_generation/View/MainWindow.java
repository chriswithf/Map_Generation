package com.map_generation.View;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;

/**
 * This is the main window class, it is responsible for the view of the application.
 * It is responsible for displaying the model and handling user input.
 * @author Chris
 *
 */

//TODO: Add a menu bar to the top of the window with options to change the size of the map, the size of the tiles, the size of the mouse radius and the direction of the mouse edit
//for daniel

public class MainWindow extends HBox {

        private Controller controller;
        private Model model;

        private MapCanvas canvas;
        private int mouseRadius = 100;
        private int mouseEditDirection = 1;


        public MainWindow() {
            super();
            this.controller = controller;
            this.model = model;
            this.canvas = new MapCanvas(1000,1000);
            canvas.draw();
            getChildren().add(canvas);
        }

        public MapCanvas getCanvas() {
            return this.canvas;
        }

    public int getMouseRadius() {
        return mouseRadius;
    }

    public int getMouseEditDirection() {
        return mouseEditDirection;
    }
}
