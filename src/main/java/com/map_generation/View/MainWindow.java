package com.map_generation.View;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
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
 * @author danieldibella
 */
public class MainWindow extends HBox {

        private Canvas canvas;
        private int mouseRadius = 100;
        private URL url;
        private Dimension bestCursorSize;

        public MainWindow(int width, int height) {
            this.canvas = new Canvas(width,height);
            this.canvas.setFocusTraversable(true);

            try {
                url = new URL("https://cdn-icons-png.flaticon.com/512/67/67687.png");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            bestCursorSize  = Toolkit.getDefaultToolkit().getBestCursorSize(mouseRadius, mouseRadius);
            setCursor();
            getChildren().add(canvas);
        }

        public Canvas getCanvas(){
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
