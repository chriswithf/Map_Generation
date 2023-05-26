package com.map_generation.Model;

/**
 SimplexNoiseGenerator is a class that generates simplex noise using the OpenSimplexNoise algorithm.
 It allows for the generation of octaved simplex noise with customizable parameters such as octaves,
 roughness, and scale. The generated noise is represented as a 2D array of doubles.
 @see OpenSimplexNoise
 */
public class SimplexNoiseGenerator {

    private int octaves;
    private double roughness;
    private double scale;


    public SimplexNoiseGenerator(int octaves, double roughness, double scale) {
        this.octaves = octaves;
        this.roughness = roughness;
        this.scale = scale;
    }

    public double[][] generateOctavedSimplexNoise(int width, int height) {
        double[][] totalNoise = new double[width][height];
        double layerFrequency = scale;
        double layerWeight = 1;
        double weightSum = 0;
        OpenSimplexNoise generator = new OpenSimplexNoise();

        // Summing up all octaves, the whole expression makes up a weighted average
        // computation where the noise with the lowest frequencies have the least effect


        for (int octave = 0; octave < octaves; octave++) {
            // Calculate single layer/octave of simplex noise, then add it to total noise
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    totalNoise[x][y] += generator.eval(x * layerFrequency, y * layerFrequency)* layerWeight;
                //SimplexNoise.noise(x * layerFrequency, y * layerFrequency) * layerWeight;
                }
            }

            // Increase variables with each incrementing octave
            layerFrequency *= 2;
            weightSum += layerWeight;
            layerWeight *= roughness;

        }
        return totalNoise;
    }
    }

