package com.map_generation.View;

import com.map_generation.Controller.Controller;
import com.map_generation.Model.Model;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is the main window class, it is responsible for the view of the application.
 * It is responsible for displaying the model and handling user input.
 *
 */

// TODO: add a start menu to set up size of map and size of tiles
public class MainWindow extends HBox {

        private Controller controller;
        private Model model;
        private MapCanvas canvas;
        private int mouseRadius = 100;
        private URL url;
        private Dimension bestCursorSize;


        public MainWindow(int width, int height) throws MalformedURLException {
            super();
            //this.controller = controller;
            //this.model = model;

            this.canvas = new MapCanvas(width,height);
            this.canvas.setFocusTraversable(true);
            url = new URL("https://cdn-icons-png.flaticon.com/512/67/67687.png");
            bestCursorSize  = Toolkit.getDefaultToolkit().getBestCursorSize(mouseRadius, mouseRadius);
            setCursor();

            canvas.draw();
            getChildren().add(canvas);
        }

        public MapCanvas getCanvas() {
            return this.canvas;
        }

        public void setCursor() {
            try {
                bestCursorSize  = Toolkit.getDefaultToolkit().getBestCursorSize(mouseRadius, mouseRadius);
                Image img = new Image(url.openStream(),bestCursorSize.width, bestCursorSize.height, false,false);
                ImageCursor cursor = new ImageCursor(img, bestCursorSize.width * 0.5, bestCursorSize.width * 0.5);
                this.canvas.setCursor(cursor);
            } catch (IOException e) {
                this.canvas.setCursor(Cursor.DEFAULT);
            }
        }

        public int incMouseRadius(int val){
            this.mouseRadius += val;
            return this.mouseRadius;
        }

        public int decMouseRadius(int val){
            this.mouseRadius -= val;
            return this.mouseRadius;
        }
        public int getMouseRadius() {
            return mouseRadius;
        }
}
