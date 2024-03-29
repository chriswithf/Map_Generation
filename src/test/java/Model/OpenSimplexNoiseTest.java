package Model;


import com.map_generation.Model.Generators.OpenSimplexNoise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * First Simple Test for OpenSimplexNoise
 *
 * @author Chris
 */


public class OpenSimplexNoiseTest {

    private OpenSimplexNoise noise;

    @BeforeEach
    public void setup() {
        noise = new OpenSimplexNoise();
    }

    @Test
    public void fastFloorFunctionsCorrectly() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            double value = random.nextDouble();
            Assertions.assertEquals(Math.floor(value), OpenSimplexNoise.fastFloorForTest(value), 0.001);
        }
    }

    @Test
    public void OpenSimplexNoiseReturnsCorrectValues() {
        int x = 100;
        int y = 100;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double nx = i / 100.0 - 0.1;
                double ny = j / 100.0 - 0.1;
                double value = noise.eval(nx, ny);
                Assertions.assertTrue(value >= -1 && value <= 1);
            }
        }

    }


    @Test
    public void testNoise2D() {
        // Test that noise2D returns a value between -1 and 1
        OpenSimplexNoise noise = new OpenSimplexNoise();
        double value = noise.eval(0, 0);
        Assertions.assertTrue(value >= -1 && value <= 1);
    }

    @Test
    public void testNoise3D() {
        // Test that noise3D returns a value between -1 and 1
        OpenSimplexNoise noise = new OpenSimplexNoise();
        double value = noise.eval(0, 0, 0);
        Assertions.assertTrue(value >= -1 && value <= 1);
    }

    @Test
    public void testNoise4D() {
        // Test that noise4D returns a value between -1 and 1
        OpenSimplexNoise noise = new OpenSimplexNoise();
        double value = noise.eval(0, 0, 0, 0);
        Assertions.assertTrue(value >= -1 && value <= 1);
    }

}