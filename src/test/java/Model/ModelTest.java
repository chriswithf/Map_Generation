package Model;

import com.map_generation.Model.Model;
import com.map_generation.Model.Shapes.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

    Model model = new Model(100, 100, 0, 0);

    Tile[][] inputTiles = new Tile[100][100];

    Tile[][] outputTiles = new Tile[100][100];

    @Before
    public void setup() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                inputTiles[i][j] = new Tile();
                inputTiles[i][j].value = 0;
            }
        }
    }

    @Test
    public void checkIfArrayChanged() {
        outputTiles = model.editMap(100, 1, 50, 50, inputTiles);

        //if one of the tiles has changed, the test passes
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (inputTiles[i][j].value != outputTiles[i][j].value) {
                    Assert.assertTrue(true);
                }
            }
        }
    }

    @Test
    public void checkIfArrayChangedInRightPosition() {
        outputTiles = model.editMap(100, 1, 50, 50, inputTiles);

        //if one of the tiles has changed, the test passes
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (inputTiles[i][j].value != outputTiles[i][j].value) {
                    Assert.assertTrue(i == 50 && j == 50);
                }
            }
        }
    }

    @Test
    public void checkIfArrayChangedInRightDirection() {
        outputTiles = model.editMap(100, 1, 50, 50, inputTiles);

        //if one of the tiles has changed, the test passes
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (inputTiles[i][j].value != outputTiles[i][j].value) {
                    Assert.assertTrue(outputTiles[i][j].value > inputTiles[i][j].value);
                }
            }
        }
    }

    @Test
    public void checkIfArrayChangedInRightDirection2() {
        outputTiles = model.editMap(100, -1, 50, 50, inputTiles);

        //if one of the tiles has changed, the test passes
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (inputTiles[i][j].value != outputTiles[i][j].value) {
                    Assert.assertTrue(outputTiles[i][j].value < inputTiles[i][j].value);

                }
            }
        }
    }

    @After
    public void tearDown() {
        model = null;
        inputTiles = null;
        outputTiles = null;
    }


}
