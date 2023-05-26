package com.map_generation.Model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

/**

 The Model class represents the model component in the Model-View-Controller (MVC) architectural pattern
 for the map generation application. It encapsulates the terrain generation logic and provides methods
 for editing and drawing the map.
 @see GenerateSimplexTiles
 @see Tile
 @see TileUtility
 */
public class Model {
    private GenerateSimplexTiles terrain;

    Tile[][] tiles;

    public Model(int width, int height, int oc, double per) {
        terrain = new GenerateSimplexTiles(width, height, oc, per);
        tiles = terrain.getTiles();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] input){
        tiles = input;
    }

    public Tile[][] editMap(int mouseRadius, int mouseEditDirection, double x, double y, Tile[][] tiles) {

        for (int i = 0; i < terrain.getX(); i++) {
            for (int j = 0; j < terrain.getY(); j++) {
                double distance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                if (distance < mouseRadius) {
                    tiles[i][j].value += (0.1 - distance / (mouseRadius * 10)) * mouseEditDirection;
                    tiles[i][j].type = terrain.getType(tiles[i][j].value);
                }
            }
        }
        return tiles;
    }

    public void drawMap(Canvas mapCanvas, Tile[][] tiles) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        for (int i = 0; i < tiles[0].length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                pw.setColor(j, i, TileUtility.getColor(tiles[j][i]));
            }
        }
    }


}
