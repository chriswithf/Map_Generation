package com.map_generation.Model;

import com.map_generation.Model.Generators.GenerateSimplexTiles;
import com.map_generation.Model.Generators.TerrainMaker;
import com.map_generation.Model.Shapes.Polygon3D;
import com.map_generation.Model.Shapes.Tile;
import com.map_generation.Model.Shapes.TileUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

import java.util.List;

/**
 * The Model class represents the model component in the Model-View-Controller
 * (MVC) architectural pattern
 * for the map generation application. It encapsulates the terrain generation
 * logic and provides methods
 * for editing and drawing the map.
 *
 * @see GenerateSimplexTiles
 * @see Tile
 * @see TileUtility
 */
public class Model {

    private final int pixelWidth = 4;
    private final int pixelHeight = 4;
    Thread renderThread;
    Tile[][] tiles;
    private GenerateSimplexTiles terrain;
    private TerrainMaker terrainMaker;
    private Render render;
    private List<Polygon3D> polygons;

    /**
     * Constructor for the model
     *
     * @param width  the width of the map
     * @param height the height of the map
     * @param oc     the octaves of the map
     * @param per    the persistence of the map
     */

    public Model(int width, int height, int oc, double per) {
        terrain = new GenerateSimplexTiles(width, height, oc, per);
        terrainMaker = new TerrainMaker(pixelWidth, pixelHeight, width, height);
        render = new Render(width, height);
        tiles = terrain.getTiles();
    }

    /**
     * Getter for the tiles
     *
     * @return the tiles
     */

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * setter for the tiles
     *
     * @param input the tiles
     */
    public void setTiles(Tile[][] input) {
        tiles = input;
    }

    /**
     * method to edit the map
     *
     * @param mouseRadius        the radius of the mouse
     * @param mouseEditDirection the direction of the mouse
     * @param x                  the x coordinate of the mouse
     * @param y                  the y coordinate of the mouse
     * @param tiles              the tiles of the map
     * @return the edited tiles
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

    /**
     * method to draw the map
     *
     * @param mapCanvas the canvas of the map
     * @param tiles     the tiles of the map
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

    /**
     * method to draw the 3D map
     */
    public void draw3DMap() {
        polygons = terrainMaker.generate(tiles);
        render.setPolygons(polygons);
    }

    /**
     * method to start the render
     */

    public void startRender() {

        // start render thread
        // when the render is already running, do nothing
        if (renderThread == null) {
            renderThread = new Thread(() -> {
                render.start();
            });
            renderThread.start();
        }
    }

    /**
     * method to stop the render
     */
    public void stopRender() {
        // stop render thread
        if (render.isRunning()) {
            renderThread.interrupt();
            render.stop();
        }
    }

    /**
     * method to start the 3D map window
     */

    public void startWindow() {
        render.start3DMapWindow();
    }

}
