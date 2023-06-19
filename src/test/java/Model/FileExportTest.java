package Model;

import com.map_generation.Model.FileExport;
import com.map_generation.Model.Generators.GenerateSimplexTiles;
import com.map_generation.Model.Shapes.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class FileExportTest {
    private Tile[][] testTiles;

    @BeforeEach
    public void setUp() {
        // Initialize test tiles
        GenerateSimplexTiles tiles = new GenerateSimplexTiles(50,50,5,5);
        testTiles=tiles.getTiles();
    }

    @Test
    @DisplayName("Test saveAsPng() with valid file")
    public void testSaveAsPngWithValidFile() throws IOException {

        BufferedImage map = FileExport.arrayToImage(testTiles);
        File outputFile = new File("test_output.png");

        FileExport.saveAsPng(map, outputFile);

        Assertions.assertTrue(outputFile.exists());
        outputFile.delete();
    }

    @Test
    @DisplayName("Test safeTerrainData() with valid file")
    public void testSafeTerrainDataWithValidFile() throws IOException {

        File outputFile = new File("test_output.json");

        FileExport.safeTerrainData(testTiles, outputFile);

        Assertions.assertTrue(outputFile.exists());
        outputFile.delete();
    }

    @Test
    @DisplayName("Test loadTerrainData() with valid file")
    public void testLoadTerrainDataWithValidFile() throws IOException {

        File inputFile = new File("test_input.json");
        FileExport.safeTerrainData(testTiles, inputFile);

        Tile[][] loadedTiles = FileExport.loadTerrainData(inputFile);

        Assertions.assertEquals(testTiles.length, loadedTiles.length);
        Assertions.assertEquals(testTiles[0].length, loadedTiles[0].length);
        inputFile.delete();
    }
    @Test
    @DisplayName("Test loadTerrainData() with valid file")
    public void testLoadTerrainDataWithValidFileisNotNull() throws IOException {

        File inputFile = new File("test_input.json");
        FileExport.safeTerrainData(testTiles, inputFile);

        Tile[][] loadedTiles = FileExport.loadTerrainData(inputFile);

        Assertions.assertNotNull(loadedTiles);
        inputFile.delete();
    }
}

