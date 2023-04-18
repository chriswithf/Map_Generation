package com.map_generation.Model;

import java.awt.image.BufferedImage;

public class Model {
    private GenerateSimplexTiles terrain;

    public Model() {
        terrain = new GenerateSimplexTiles(1000, 1000);
    }

    public void editMap(int mouseRadius, int mouseEditDirection, double x, double y) {

        for (int i = 0; i < terrain.getX(); i++) {
            for (int j = 0; j < terrain.getY(); j++) {
                double distance = Math.sqrt(Math.pow(x- i, 2) + Math.pow(y - j, 2));
                if (distance < mouseRadius) {
                    GenerateSimplexTiles.getTiles()[j][i].value += (0.1 - distance / (mouseRadius*10))* mouseEditDirection;
                }
                GenerateSimplexTiles.getTiles()[i][j].type = terrain.getType(GenerateSimplexTiles.getTiles()[i][j].value);
            }
        }

    }




}
