package com.map_generation.Model;

import com.google.gson.Gson;
import com.map_generation.Model.Shapes.Tile;
import com.map_generation.Model.Shapes.TileUtility;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class holds the methods to write to files.
 *
 * @author Eddie
 */
public class FileExport {

    /**
     * Safes the rendered image map as a PNG file
     *
     * @param map the generated map
     */
    public static void saveAsPng(RenderedImage map, File input) {
        File selectedFile = input;
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
     *
     * @param tiles the generated map
     * @return Buffered image of the map
     */
    public static BufferedImage arrayToImage(Tile[][] tiles) {

        int width = tiles.length;
        int height = tiles[0].length;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                color = TileUtility.getColor(tiles[x][y]);
                int red = (int) (color.getRed() * 255);
                int green = (int) (color.getGreen() * 255);
                int blue = (int) (color.getBlue() * 255);
                int d = ((255 << 24) | red << 16 | green << 8 | blue);
                bufferedImage.setRGB(x, y, d);
            }
        }

        return bufferedImage;
    }

    /**
     * Safes the terrain data as a JSON file
     *
     * @param tiles the generated map
     */
    public static void safeTerrainData(Tile[][] tiles, File input) {
        File selectedFile = input;
        if (selectedFile != null) {
            if (!selectedFile.getName().toLowerCase().endsWith(".json")) {
                selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".json");
            }
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(selectedFile)) {
                gson.toJson(tiles, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the terrain data from a JSON file
     *
     * @return the loaded map
     */
    public static Tile[][] loadTerrainData(File input) {
        Tile[][] tiles = null;
        File selectedFile = input;
        if (selectedFile != null) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(selectedFile)) {
                tiles = gson.fromJson(reader, Tile[][].class);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("File is corrupted");
                alert.showAndWait();
            }
        }
        return tiles;
    }

    /**
     * Opens filechooser to save Png.
     *
     * @return File with name provided by user
     */
    public static File showPngDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as PNG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        Stage stage = new Stage();
        return fileChooser.showSaveDialog(stage);
    }

    /**
     * Opens filechooser save dialog window
     *
     * @return filename where the file will be saved
     */
    public static File showSaveJsonDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as JSON");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON files", "*.json");
        fileChooser.setSelectedExtensionFilter(jsonFilter);
        fileChooser.getExtensionFilters().add(jsonFilter);
        Stage stage = new Stage();
        return fileChooser.showSaveDialog(stage);
    }

    /**
     * Opens filechooser open dialog for user to select a jsonfile to be loaded
     *
     * @return filename of file to be loaded
     */
    public static File showJsonLoadDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a JSON file");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON files", "*.json");
        fileChooser.setSelectedExtensionFilter(jsonFilter);
        fileChooser.getExtensionFilters().add(jsonFilter);
        Stage stage = new Stage();
        return fileChooser.showOpenDialog(stage);
    }
}
