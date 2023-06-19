package com.map_generation.Model.Generators;

import com.map_generation.Model.Shapes.Tile;

import java.util.Random;

/**
 * GenerateSimplexTiles is a class responsible for generating simplex noise maps
 * and using them to generate heightmaps
 * represented as tiles. It uses the OpenSimplexNoise algorithm to create
 * natural-looking terrain features.
 * The class provides methods to generate terrain based on different parameters
 * such as dimensions, octaves, and persistence.
 * It also allows for re-rendering the terrain with different random seeds for
 * variation.
 * The generated tiles are assigned types based on their heightmap values,
 * allowing for terrain classification
 * such as water, sand, grass, forest, rock, and snow.
 *
 * @see Tile
 * @see OpenSimplexNoise
 */

public class GenerateSimplexTiles {
    private int x;
    private int y;

    private int octaves;
    private double persistence;

    public Tile[][] getTiles() {
        return tiles;
    }

    private Tile[][] tiles;
    OpenSimplexNoise noise;
    OpenSimplexNoise continentNoise;
    OpenSimplexNoise temperatureNoise;

    /**
     * Method to generate a simplex noise map and use it to generate a heightmap
     * This function does not use a seed, so it uses a random seed.
     *
     * @param x the x dimension of the map
     * @param y the y dimension of the map
     */
    public GenerateSimplexTiles(int x, int y, int octaves, double persistence) {
        this.x = x;
        this.y = y;
        this.octaves = octaves;
        this.persistence = persistence;
        this.tiles = new Tile[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                tiles[i][j] = new Tile();
            }
        }

        Random rand = new Random();
        this.noise = new OpenSimplexNoise(rand.nextInt());
        this.continentNoise = new OpenSimplexNoise(rand.nextInt());
        this.temperatureNoise = new OpenSimplexNoise(rand.nextInt());
        generateContinents();

    }

    // Getter and Setter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Method to generate a second simplex noise filter and use positive values on
     * it to raise the tiles on the first filter
     */
    void generateContinents() {
        double value = 0.0;
        double scale = .02;

        double temperatureValue = 0.0;
        double temperatureScale = 0.002;

        double continentValue = 0.0;
        double continentScale = .005;
        // Generate simplex noise, and base tiles on the values. i represents x, and j
        // represents y
        // The range of values is ~ -.866 to .866, so tiles must be evaluated
        // accordingly
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                // The idea here is to generate an initial terrain, then with a larger scale,
                // raise or lower terrain to
                // exaggerate the features, oceans get bigger, continents get bigger.
                value = octaveSimplex(noise, i * scale, j * scale, 0.2, this.octaves, this.persistence);
                continentValue = continentNoise.eval(i * continentScale, j * continentScale);

                continentValue *= 1.1;
                value += continentValue;

                // Generate temperature noise
                temperatureValue = octaveSimplex(temperatureNoise, i * temperatureScale, j * temperatureScale, 0.2,
                        this.octaves, this.persistence);

                // Set the tiles type
                tiles[i][j].temperature = temperatureValue;
                tiles[i][j].type = getType(value);
                tiles[i][j].value = value;

            }
        }

    }

    /**
     * Method to apply a type based on the value of the tile, basically a janky
     * heightmap coloring
     *
     * @param value the Heightmap value of the tile
     * @return the type of the tile as a string
     */
    public String getType(double value) {
        String type = "";

        if (value <= -.2) {
            type = "DEEP_WATER";
        } else if (value > -.2 && value <= 0) {
            type = "WATER";
        } else if (value > 0 && value <= .1) {
            type = "SAND";
        } else if (value > .1 && value <= .3) {
            type = "GRASS";
        } else if (value > .3 && value <= .6) {
            type = "FOREST";
        } else if (value > 0.6 && value <= .73) {
            type = "ROCK";
        } else if (value > .73 && value <= .97) {
            type = "DARK_ROCK";
        } else {
            type = "SNOW";
        }

        return type;
    }

    /**
     * The octaveSimplex method is a noise function that generates a value of
     * simplex noise at a given set of coordinates. Simplex noise is a type of
     * gradient noise that is used to generate natural-looking textures and
     * patterns.
     * 
     * The method takes six parameters: an OpenSimplexNoise object that represents
     * the noise seed, and the x, y, and z coordinates of the point at which to
     * evaluate the noise, the number of octaves to use, and the persistence value.
     * 
     * The method uses a loop to generate multiple octaves of noise, each with a
     * higher frequency and lower amplitude than the previous octave. The noise
     * values from each octave are added together to create a final noise value.
     * 
     * The frequency and amplitude variables control the scaling of the noise
     * function. The frequency variable is multiplied by 2 for each octave, which
     * increases the frequency of the noise. The amplitude variable is multiplied by
     * the persistance value for each octave, which decreases the amplitude of the
     * noise.
     * 
     * The maxValue variable is used to normalize the noise value to a range between
     * 0 and 1. It is calculated as the sum of the amplitudes of all the octaves.
     * 
     * The final noise value is calculated by dividing the sum of the noise values
     * by the maxValue variable.
     *
     * @param noiseSeed   the OpenSimplexNoise object to use
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @param z           the z coordinate
     * @param octaves     the number of octaves to use
     * @param persistance the persistance of the octaves
     * @return the value of the noise at the given coordinates
     * 
     * @author chris
     */
    public double octaveSimplex(OpenSimplexNoise noiseSeed, double x, double y, double z, int octaves,
            double persistance) {
        double total = 0;
        double frequency = 1;
        double amplitude = .1;
        double maxValue = 0;

        for (int i = 0; i < octaves; i++) {
            total += noiseSeed.eval(x * frequency, y * frequency, .5) * amplitude;

            maxValue += amplitude;

            amplitude *= persistance;
            frequency *= 2;
        }

        return total / maxValue;

    }
}
