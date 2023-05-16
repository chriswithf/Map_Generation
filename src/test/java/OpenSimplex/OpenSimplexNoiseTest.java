package OpenSimplex;

import java.util.Random;

import com.map_generation.Model.Generators.OpenSimplexNoise;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * First Simple Test for OpenSimplexNoise
 * @author Chris
 */


public class OpenSimplexNoiseTest {

    private OpenSimplexNoise noise;

    @Before
    public void setup(){
        noise = new OpenSimplexNoise();
    }

    @Test
    public void fastFloorFunctionsCorrectly(){
        Random random = new Random();
        for (int i = 0; i<50;i++){
            double value = random.nextDouble();
            Assert.assertEquals(Math.floor(value),OpenSimplexNoise.fastFloorForTest(value),0.001);
        }
    }

    @Test
    public void OpenSimplexNoiseReturnsCorrectValues(){
        int x = 100;
        int y = 100;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double nx = i / 100.0 - 0.1;
                double ny = j / 100.0 - 0.1;
                double value = noise.eval(nx, ny);
                Assert.assertTrue(value>=-1 && value<=1);
            }
        }

    }

}