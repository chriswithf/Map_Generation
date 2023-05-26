package com.map_generation.Model;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class holds the methods to write to files.
 *
 * @author Eddie
 */
public class FileExport {

    /**
     * Safes the rendered image map as a PNG file
     * @param map the generated map
     */
    public static void saveAsPng( RenderedImage map){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as PNG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        Stage test = new Stage();
        File selectedFile = fileChooser.showSaveDialog(test);
        if (selectedFile != null) {
            try {
                ImageIO.write(map, "PNG", selectedFile);
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as JSON");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON files", "*.json");
        fileChooser.setSelectedExtensionFilter(jsonFilter);
        fileChooser.getExtensionFilters().add(jsonFilter);
        Stage test = new Stage();
        File selectedFile = fileChooser.showSaveDialog(test);
        if(selectedFile!=null){
            if(!selectedFile.getName().toLowerCase().endsWith(".json")){
                selectedFile = new File(selectedFile.getParent(), selectedFile.getName()+".json");
            }
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(selectedFile)) {
                gson.toJson(tiles,writer);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static Tile[][] loadTerrainData(){
        Tile[][] tiles = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a JSON file");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON files", "*.json");
        fileChooser.setSelectedExtensionFilter(jsonFilter);
        fileChooser.getExtensionFilters().add(jsonFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(selectedFile)) {
                tiles = gson.fromJson(reader, Tile[][].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tiles;
    }
}
