package com.map_generation.Model.Shapes;


import java.awt.*;

public final class Polygon3DUtility {
    public static void convert(Triangle2D triangle, Polygon3D polygon, Point3D originPoint, int width, int height, double scale) {
        Point3DUtility.convert(triangle.p1, polygon.v1, originPoint, width, height, scale);
        Point3DUtility.convert(triangle.p2, polygon.v2, originPoint, width, height, scale);
        Point3DUtility.convert(triangle.p3, polygon.v3, originPoint, width, height, scale);
    }

    public static void rotate(Polygon3D polygon, Vector3D rotateVector) {
        Point3DUtility.rotate(polygon.v1, rotateVector);
        Point3DUtility.rotate(polygon.v2, rotateVector);
        Point3DUtility.rotate(polygon.v3, rotateVector);
    }

    public static Color lightingColor(Color color, Vector3D normalVector, Vector3D lightVector) {
        double dotProduct = Vector3DUtility.dotProduct(normalVector, lightVector);
        int lightRatio = (int) ((-1 - dotProduct) * 75);

        return new Color(correctRGBValue(color.getRed() + lightRatio), correctRGBValue(color.getGreen() + lightRatio), correctRGBValue(color.getBlue() + lightRatio));
    }

    public static int correctRGBValue(int value) {
        return Math.min(255, Math.max(0, value));
    }
}
