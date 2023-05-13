package com.map_generation.Model;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
/**
 * This class holds the methods to write to files.
 */
public class FileExport {

    /**
     * Safes the rendered image map as a PNG file
     * @param filename - the name of the file
     * @param map the generated map
     */
    public static void saveAsPng( RenderedImage map){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as PNG");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG files","png"));
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection==JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                ImageIO.write(map, "PNG", fileToSave);
            } catch (IOException e) {
                System.err.println("Failed to save image to file.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a copy of a generated map as a bufferedImage
     * @return Buffered image of the map
     */
    public static BufferedImage arrayToImage(Tile[][] tiles){

        int width = tiles.length;
        int height = tiles[0].length;
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
    public static void safeTerrainData(Tile[][] tiles){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as JSON");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files","json"));
        int userSelection = fileChooser.showSaveDialog(null);
        if(userSelection==JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            if(!fileToSave.getName().toLowerCase().endsWith(".json")){
                fileToSave = new File(fileToSave.getParent(), fileToSave.getName()+".json");
            }
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                gson.toJson(tiles,writer);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static Tile[][] loadTerrainData(){
        Tile[][] tiles = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a JSON file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        int userSelection = fileChooser.showOpenDialog(null);
        if(userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(fileToLoad)) {
                tiles = gson.fromJson(reader, Tile[][].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tiles;
    }
}
