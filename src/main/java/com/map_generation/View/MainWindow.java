package com.map_generation.View;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The MainWindow class represents the main window of the application and is
 * responsible for displaying the view.
 * It extends the HBox class and encapsulates the canvas, mouse radius, and
 * cursor functionality.
 *
 * @author danieldibella
 */
public class MainWindow extends HBox {

    private Canvas canvas;
    private int mouseRadius = 100;
    private Dimension bestCursorSize;

    /**
     * Initializes a Main Window, with a specific width and height of the frame
     *
     * @param width  width of the frame and canvas
     * @param height height of the frame and canvas
     */
    public MainWindow(int width, int height) {
        this.canvas = new Canvas(width, height);
        this.canvas.setFocusTraversable(true);
        this.bestCursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(mouseRadius, mouseRadius);

        setCursor();
        getChildren().add(canvas);
    }

    /**
     * return the canvas
     *
     * @return the current canvas
     */
    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * Sets the cursor of the frame to the custom circle cursor of the size of
     * mouseRadius
     */
    public void setCursor() {
        try {
            this.bestCursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(mouseRadius, mouseRadius);
            File file = new File("src/main/resources/pictures/cursor.png");

            if (file.exists()) {
                Image img = new Image(new FileInputStream(file), bestCursorSize.width, bestCursorSize.height, false, false);
                ImageCursor cursor = new ImageCursor(img, bestCursorSize.width * 0.5, bestCursorSize.width * 0.5);
                this.canvas.setCursor(cursor);
            }
        } catch (IOException e) {
            this.canvas.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * increases the mouse radius by a certain amount
     *
     * @param val amount
     * @return the new value
     */
    public int incMouseRadius(int val) {
        this.mouseRadius += val;
        return this.mouseRadius;
    }

    /**
     * decreases the mouse radius by a certain amount
     *
     * @param val amount
     * @return the new value
     */
    public int decMouseRadius(int val) {
        this.mouseRadius -= val;
        return this.mouseRadius;
    }

    /**
     * returns the mouse radius
     *
     * @return the current mouse radius
     */
    public int getMouseRadius() {
        return mouseRadius;
    }
}
