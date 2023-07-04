package com.map_generation.Model.Shapes;

import javafx.scene.paint.Color;

/**
 * This class is responsible for the color of the tiles
 * It also contains a method to adjust the color based on the temperature of the
 * tile
 *
 * @author Chris
 */
public class TileUtility {

    //it is used to determine the partition between the warm and cold zones
    static float partition = 0.15f;

    /**
     * @param tile The tile to get the color of
     * @return The color of the tile
     */

    public static Color getColor(Tile tile) {

        Color c;
        switch (tile.type) {
            case "GRASS":
                c = getTemperatureAdjustedColor(Color.rgb(95, 224, 56), tile.temperature);
                break;
            case "FOREST":
                c = getTemperatureAdjustedColor(Color.rgb(77, 161, 51), tile.temperature);
                break;
            case "WATER":
                c = Color.rgb(0, 98, 227);
                break;
            case "DEEP_WATER":
                c = Color.rgb(0, 75, 173);
                break;
            case "SAND":
                c = Color.rgb(245, 232, 137);
                break;
            case "ROCK":
                c = Color.rgb(115, 115, 115);
                break;
            case "DARK_ROCK":
                c = Color.rgb(102, 102, 102);
                if (tile.temperature < -1 * partition) c = Color.rgb(200, 200, 200);
                break;
            case "SNOW":
                c = Color.rgb(255, 255, 255);
                // If snow is in a "warm zone", it becomes rock instead
                if (tile.temperature > partition) c = Color.rgb(102, 102, 102);
                break;
            case "DEBUG":
                c = Color.rgb(255, 0, 225);
                break;
            case "BORDER":
                c = Color.rgb(166, 160, 91);
                break;
            default:
                c = Color.rgb(0, 0, 0);
                break;

        }

        return c;
    }

    /**
     * @param c           The color to adjust
     * @param temperature The temperature of the tile
     * @return The adjusted color
     */

    public static Color getTemperatureAdjustedColor(Color c, double temperature) {
        if (temperature <= -1 * partition) {
            // Very cold
            c = blendColors(c, Color.rgb(150, 150, 227));
        } else {
            // HOT
            c = blendColors(c, Color.rgb(235, 170, 52));
        }

        return c;
    }

    /**
     * Blends two colors together
     *
     * @param colorA The color to blend with colorB
     * @param colorB The color to blend with colorA
     * @return The blended color
     */

    public static Color blendColors(Color colorA, Color colorB) {
        double totalAlpha = colorA.getOpacity() + colorB.getOpacity();
        double weightA = colorA.getOpacity() / totalAlpha;
        double weightB = colorB.getOpacity() / totalAlpha;

        double r = weightA * colorA.getRed() + weightB * colorB.getRed();
        double g = weightA * colorA.getGreen() + weightB * colorB.getGreen();
        double b = weightA * colorA.getBlue() + weightB * colorB.getBlue();
        double a = Math.max(colorA.getOpacity(), colorB.getOpacity());

        return Color.color(r, g, b, a);
    }
}
