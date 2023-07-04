package Model;

import com.map_generation.Model.Generators.GenerateSimplexTiles;
import com.map_generation.Model.Shapes.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateSimplexTilesTest {

    @Test
    public void testGenerateSimplexTiles() {
        // Test that the tiles array is initialized with the correct dimensions
        GenerateSimplexTiles generator = new GenerateSimplexTiles(10, 10, 8, 0.5);
        Tile[][] tiles = generator.getTiles();
        assertEquals(10, tiles.length);
        assertEquals(10, tiles[0].length);

        // Test that the tiles have the correct type and value
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile tile = tiles[i][j];
                assertNotNull(tile);
                assertTrue(tile.value >= -1 && tile.value <= 1);
                assertNotNull(tile.type);
            }
        }

        // Test that the temperature values are set correctly
        generator = new GenerateSimplexTiles(5, 5, 8, 0.5);
        tiles = generator.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile tile = tiles[i][j];
                assertNotNull(tile.temperature);
                assertTrue(tile.temperature >= -1 && tile.temperature <= 1);
            }
        }
    }


    @Test
    public void testGetType() {
        // Test that getType returns the correct tile type for a given value
        GenerateSimplexTiles generator = new GenerateSimplexTiles(10, 10, 8, 0.5);
        assertEquals("DEEP_WATER", generator.getType(-1));
        assertEquals("DEEP_WATER", generator.getType(-0.2));
        assertEquals("WATER", generator.getType(0));
        assertEquals("FOREST", generator.getType(0.5));
        assertEquals("SNOW", generator.getType(1));
    }

}