package com.map_generation.Model.Generators;

import com.map_generation.Model.Shapes.Point3D;
import com.map_generation.Model.Shapes.Polygon3D;
import com.map_generation.Model.Shapes.Polygon3DUtility;
import com.map_generation.Model.Shapes.Vector3D;
import com.map_generation.Model.Shapes.Tile;
import com.map_generation.Model.Shapes.TileUtility;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The TerrainMaker class generates a list of 3D polygons representing terrain
 * based on a 2D array of tiles. It uses the dimensions of the terrain and the
 * map to calculate the size of each square and the starting positions in the 3D
 * space. The class provides a method to generate the polygons from the given
 * tiles.
 * 
 * The generated polygons represent the terrain's surface, with each tile
 * corresponding to a polygon. The height values of the tiles are used to
 * determine the elevation of the terrain. The polygons are constructed by
 * connecting the vertices of adjacent tiles, forming triangles.
 * 
 * The class also includes functionality to flatten layers up to a specified
 * beach layer and assign colors to the polygons based on the height values of
 * the tiles. The generate method creates the polygons and applies rotations to
 * improve the view of the terrain.
 * 
 * Overall, the TerrainMaker class enables the creation of a 3D representation
 * of terrain from a 2D tile map.
 * 
 * @author chris
 */
public class TerrainMaker {
    private final double squareWidth;
    private final double squareHeight;
    private final double zStart;
    private final double yStart;

    private final int mapWidth;
    private final int mapHeight;

    /**
     * Constructs a new TerrainMaker object with the specified width, height, map
     * width, and map height.
     *
     * @param width     the width of the terrain
     * @param height    the height of the terrain
     * @param mapWidth  the width of the map
     * @param mapHeight the height of the map
     */
    public TerrainMaker(double width, double height, int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        squareWidth = width / mapWidth;
        squareHeight = height / mapHeight;
        zStart = -height / 2;
        yStart = -width / 2;
    }

    /**
     * Generates a list of 3D polygons representing the terrain using the specified
     * tiles.
     *
     * @param tiles the tiles representing the terrain
     * @return a list of 3D polygons representing the terrain
     */

    public List<Polygon3D> generate(Tile[][] tiles) {

        final List<Polygon3D> polygons = new ArrayList<>();
        final List<Point3D> points = new ArrayList<>();

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                double heightValue = tiles[i][j].value;
                int heightValueAsInt = (int) (heightValue * 10);

                // flatten all layers up to the beach layer
                double x = heightValueAsInt <= 0 // 0 - beach layer
                        ? 0
                        : heightValue * 0.2;

                points.add(new Point3D(x,
                        j * squareWidth + yStart,
                        i * squareHeight + zStart));

                if (!(i > 0 && j > 0)) {
                    continue;
                }

                /* Color color = heightColor(heightValueAsInt); */

                // convert javafx color to awt color

                javafx.scene.paint.Color color1 = TileUtility.getColor(tiles[i][j]);

                Color color = new Color((float) color1.getRed(), (float) color1.getGreen(), (float) color1.getBlue());

                polygons.add(new Polygon3D(color,
                        points.get((i - 1) * mapHeight + (j - 1)),
                        points.get(i * mapHeight + (j - 1)),
                        points.get(i * mapHeight + j)));

                polygons.add(new Polygon3D(color,
                        points.get((i - 1) * mapHeight + (j - 1)),
                        points.get(i * mapHeight + j),
                        points.get((i - 1) * mapHeight + j)));

            }
        }

        // rotate polygons for better view
        polygons.parallelStream()
                .forEach(p -> Polygon3DUtility.rotate(p, new Vector3D(-90, -45, 0)));

        return polygons;
    }
}
