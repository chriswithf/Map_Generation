package com.map_generation.Model;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class holds the methods to write to files.
 * @author Eddie
 */
public class FileExport {

    /**
     * Safes the rendered image map as a PNG file
     * @param Stringname - the name of the file
     * @param map the generated map
     */
    public static void saveAsPng(String Stringname, RenderedImage map){
        File file = new File(Stringname);
        try {
            ImageIO.write(map, "PNG", file);
        } catch (IOException e) {
            System.err.println("Failed to save image to file.");
            e.printStackTrace();
        }
    }

    /**
     * Creates a copy of a generated map as a bufferedImage
     * @param map - Generated map
     * @return Buffered image of the map
     */
    public static BufferedImage arrayToImage(GenerateSimplexTiles map, Tile[][] tiles){
        int width = map.getX();
        int height = map.getY();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color;

       for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                color = TileUtility.getColor(tiles[x][y]);
                int red = (int)(color.getRed()*255);
                int green = (int)(color.getGreen()*255);
                int blue = (int)(color.getBlue()*255);
                int d = ((255 << 24) | red << 16 | green << 8 | blue);
                bufferedImage.setRGB(x, y, d);
            }
        }

        return bufferedImage;
    }
}
