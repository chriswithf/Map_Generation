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

public class TerrainMaker {
    private final double squareWidth;
    private final double squareHeight;
    private final double zStart;
    private final double yStart;

    private final int mapWidth;

    private final int mapHeight;



    public TerrainMaker(double width, double height, int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        squareWidth = width / mapWidth;
        squareHeight = height / mapHeight;
        zStart = -height / 2;
        yStart = -width / 2;
    }

    public List<Polygon3D> generate(Tile[][] tiles, Vector3D rotateVector) {
        final double[][] heightMap = createHeightMap(mapWidth,mapHeight,tiles);

        final List<Polygon3D> polygons = new ArrayList<>();
        final List<Point3D> points = new ArrayList<>();

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                double heightValue = heightMap[i][j];
                int heightValueAsInt = (int) (heightValue * 10);

                // flatten all layers up to the beach layer
                double x = heightValueAsInt <= 0 // 0 - beach layer
                        ? 0 : heightValue * 0.2;

                points.add(new Point3D(x,
                        j * squareWidth + yStart,
                        i * squareHeight + zStart));

                if (!(i > 0 && j > 0)) {
                    continue;
                }

               /* Color color = heightColor(heightValueAsInt);*/

                //convert javafx color to awt color

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
                .forEach(p -> Polygon3DUtility.rotate(p, new Vector3D(-90,-45,0)));

        return polygons;
    }



    private double[][] createHeightMap(int mapWidth, int mapHeight,Tile[][] tiles) {
        final double[][] heightMap = new double[mapWidth][mapHeight];

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                heightMap[i][j] = tiles[i][j].value;
            }
        }

        return heightMap;
    }



    private Color heightColor(int h) {
        return switch (h) {
            case -10 -> new Color(0, 33, 86);
            case -9 -> new Color(0, 35, 91);
            case -8 -> new Color(1, 39, 101);
            case -7 -> new Color(1, 44, 114);
            case -6 -> new Color(1, 48, 126);
            case -5 -> new Color(1, 53, 140);
            case -4 -> new Color(2, 60, 155);
            case -3 -> new Color(1, 66, 171);
            case -2 -> new Color(2, 73, 189);
            case -1 -> new Color(1, 80, 210);
            case 0 -> new Color(248, 234, 30);
            case 1 -> new Color(21, 178, 0);
            case 2 -> new Color(19, 161, 0);
            case 3 -> new Color(17, 148, 0);
            case 4 -> new Color(16, 140, 0);
            case 5 -> new Color(13, 121, 0);
            case 6 -> new Color(12, 101, 0);
            case 7 -> new Color(101, 101, 101);
            case 8 -> new Color(114, 112, 112);
            case 9 -> new Color(131, 131, 131);
            case 10 -> new Color(154, 154, 154);
            default -> (h > 0 ?
                    new Color(154, 154, 154) :
                    new Color(0, 33, 86));
        };
    }
}
