package com.map_generation.View;

import com.map_generation.Model.GenerateSimplexTiles;
import com.map_generation.Model.Tile;
import com.map_generation.Model.TileUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

/**
 * This is the canvas class, it is responsible for drawing the map to the screen.
 * It is responsible for handling events and updating the model and view accordingly.
 * @author Chris
 */

public class MapCanvas extends Canvas {

        public MapCanvas() {
            super();
        }

        public MapCanvas(double width, double height) {
            super(width, height);

        }

        public void draw() {
            GraphicsContext gc = getGraphicsContext2D();
            PixelWriter pw = gc.getPixelWriter();
            for (int i = 0; i < GenerateSimplexTiles.getTiles()[0].length; i++) {
                for (int j = 0; j < GenerateSimplexTiles.getTiles().length; j++) {
                    pw.setColor(j , i , TileUtility.getColor(GenerateSimplexTiles.getTiles()[i][j]));

                }
            }
        }


}
