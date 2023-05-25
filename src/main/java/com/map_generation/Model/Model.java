package com.map_generation.Model;

import java.util.List;

import com.map_generation.Model.Generators.GenerateSimplexTiles;
import com.map_generation.Model.Generators.TerrainMaker;
import com.map_generation.Model.Shapes.Polygon3D;
import com.map_generation.Model.Shapes.Tile;
import com.map_generation.Model.Shapes.TileUtility;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

public class Model {

    private final int pixelWidth = 4;
    private final int pixelHeight = 4;

    private GenerateSimplexTiles terrain;
    private TerrainMaker terrainMaker;
    private Render render;

    Thread renderThread;

    private List<Polygon3D> polygons;



    Tile[][] tiles;

    /*
     * Constructor for the model
     * 
     * @param width the width of the map 
     * 
     * @param height the height of the map
     * 
     * @param oc the octaves of the map
     * 
     * @param per the persistence of the map
     * 
     * @return a new model
     * 
     * 
     */

    public Model(int width, int height, int oc, double per) {
        terrain = new GenerateSimplexTiles(width, height, oc, per);
        terrainMaker = new TerrainMaker(pixelWidth, pixelHeight, width, height);
        render = new Render(width, height);
        tiles = terrain.getTiles();
    }

    /*
     * Getter for the tiles
     * 
     * @return the tiles
     * 
     * 
     */

    public Tile[][] getTiles() {
        return tiles;
    }

    /*
     * method to edit the map
     * 
     * @param mouseRadius the radius of the mouse
     * 
     * @param mouseEditDirection the direction of the mouse
     * 
     * @param x the x coordinate of the mouse
     * 
     * @param y the y coordinate of the mouse
     * 
     * @param tiles the tiles of the map
     * 
     * @return the edited tiles
     * 
     *   
    */
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

    /*
     * method to draw the map
     * 
     * @param mapCanvas the canvas of the map
     * 
     * @param tiles the tiles of the map
     * 
     * 
     */

    public void drawMap(Canvas mapCanvas, Tile[][] tiles) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        for (int i = 0; i < tiles[0].length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                pw.setColor(j, i, TileUtility.getColor(tiles[j][i]));
            }
        }
    }

    /*
     * method to draw the 3D map
     * 
     * @param polygons the polygons of the map
     * 
     * 
     */

    public void draw3DMap() {
        polygons = terrainMaker.generate(tiles);
        render.setPolygons(polygons);
    }

    /*
     * method to draw the 3D map
     * 
     * @param polygons the polygons of the map
     * 
     * 
     */

    public void startRender() {
        //start render thread

        renderThread = new Thread(()->
        {
           render.start();
        });
        renderThread.setDaemon(true);
        renderThread.start();
    }

    /*
     * method to draw the 3D map
     * 
     * @param polygons the polygons of the map
     * 
     * 
     */

    public void stopRender() {
        //stop render thread

        render.stop();

        renderThread.interrupt();


    }

   


}
